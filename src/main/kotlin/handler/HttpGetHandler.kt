package handler

import Constants.HttpMethod
import exception.NotFoundException
import server.RequestData
import storage.Storage

class HttpGetHandler(private val storage: Storage) : HttpHandler {
    override fun canHandle(requestData: RequestData): Boolean {
        return requestData.method == HttpMethod.GET
    }

    override fun handle(requestData: RequestData): Response {
        val (_, path) = requestData.url.split("/")
        path.toIntOrNull()?.let { target ->
            return Response(storage.get(target))
        }
        if (path == "list") {
            return Response(storage.getAll())
        }
        throw NotFoundException()
    }

}