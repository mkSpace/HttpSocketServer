package handler

import server.RequestData

interface HttpHandler {
    fun canHandle(requestData: RequestData): Boolean

    fun handle(requestData: RequestData): Response
}