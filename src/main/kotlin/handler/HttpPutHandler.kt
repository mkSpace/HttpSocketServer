package handler

import Constants.HttpMethod
import exception.BadRequestException
import server.RequestData
import storage.Storage

class HttpPutHandler(private val storage: Storage) : HttpHandler {
    override fun canHandle(requestData: RequestData): Boolean {
        return requestData.method == HttpMethod.PUT
    }

    override fun handle(requestData: RequestData): Response {
        val (_, path) = requestData.url.split("/")
        val target = path.toIntOrNull() ?: throw BadRequestException()
        storage.update(target, requestData.body)
        return Response.createSuccessResponse()
    }
}