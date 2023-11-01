package handler

import Constants.HttpResponseHeader
import exception.ConflictException
import exception.NotFoundException
import exception.RuntimeException

class Response(private val data: String? = null, private val exception: RuntimeException? = null) {

    companion object {
        fun createSuccessResponse(): Response {
            return Response("Success!!")
        }
    }

    fun convertToByteArray(): ByteArray {
        return if (exception == null) {
            (HttpResponseHeader.SUCCESS + data).toByteArray()
        } else {
            when (exception) {
                is ConflictException -> (HttpResponseHeader.CONFLICT + exception.message).toByteArray()
                is NotFoundException -> (HttpResponseHeader.NOT_FOUND + exception.message).toByteArray()
                else -> (HttpResponseHeader.BAD_REQUEST + exception.message).toByteArray()
            }
        }
    }
}