package server

import Constants
import Constants.HttpMethod
import exception.BadRequestException
import exception.RuntimeException
import handler.HttpHandler
import handler.HttpHandlerImpl
import handler.Response
import storage.InMemoryStorage
import storage.Storage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class Server : Runnable {

    companion object {
        private const val PORT = 8080
    }

    private val serverSocket: ServerSocket = ServerSocket(PORT)
    private val storage: Storage = InMemoryStorage()
    private val httpHandler: HttpHandler = HttpHandlerImpl(storage)

    override fun run() {
        while (true) {
            runCatching {
                listen(serverSocket.accept())
            }.onFailure { exception ->
                exception.printStackTrace()
                return
            }
        }
    }

    private fun listen(clientSocket: Socket) {
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        clientSocket.getOutputStream().use { outputStream ->
            val requestInfo = reader.readLine()
            if (requestInfo == null) {
                clientSocket.close()
                return
            }

            val (method, url) = requestInfo.split(Constants.HTTP_METHOD_DIVIDER)
            val headers: MutableMap<String, String> = mutableMapOf()

            var line = reader.readLine()
            while (!line.isNullOrBlank()) {
                val (key, value) = line.split(Constants.HTTP_HEADER_DIVIDER)
                headers[key.trim()] = value.trim()
                line = reader.readLine()
            }

            val buffer = StringBuffer()
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                val length = headers[Constants.HEADER_CONTENT_LENGTH]?.toIntOrNull() ?: 0
                repeat(length) {
                    buffer.append(reader.read().toChar())
                }
            }

            val requestData = RequestData(method, url, buffer.toString())
            runCatching {
                if (!httpHandler.canHandle(requestData)) {
                    throw BadRequestException()
                }
                outputStream.write(httpHandler.handle(requestData).convertToByteArray())
            }.onFailure { exception ->
                if (exception is RuntimeException) {
                    outputStream.write(Response(exception = exception).convertToByteArray())
                }
            }
        }
    }
}