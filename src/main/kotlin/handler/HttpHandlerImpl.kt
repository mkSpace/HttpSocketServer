package handler

import exception.RuntimeException
import server.RequestData
import storage.Storage

class HttpHandlerImpl(storage: Storage) : HttpHandler {

    private val handlerList: List<HttpHandler> = listOf(
        HttpGetHandler(storage),
        HttpPostHandler(storage),
        HttpPutHandler(storage),
        HttpDeleteHandler(storage)
    )

    override fun canHandle(requestData: RequestData): Boolean {
        return requestData.method.isNotBlank() && requestData.url.isNotBlank()
    }

    override fun handle(requestData: RequestData): Response {
        handlerList.forEach {handler ->
            if(!handler.canHandle(requestData)) return@forEach
            return handler.handle(requestData)
        }
        throw RuntimeException("Cannot handle your request")
    }
}