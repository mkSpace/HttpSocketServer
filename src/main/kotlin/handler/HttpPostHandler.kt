package handler

import Constants.HttpMethod
import exception.BadRequestException
import server.RequestData
import storage.Storage

class HttpPostHandler(private val storage: Storage) : HttpHandler {
    override fun canHandle(requestData: RequestData): Boolean {
        return requestData.method == HttpMethod.POST
    }

    override fun handle(requestData: RequestData): Response {
        val (_, path) = requestData.url.split("/")
        val target = path.toIntOrNull() ?: throw BadRequestException()
        storage.add(target, requestData.body)
        return Response.createSuccessResponse()
    }
}