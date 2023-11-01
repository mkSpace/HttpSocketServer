object Constants {
    object HttpMethod {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
    }

    object HttpResponseHeader {
        const val SUCCESS = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n"
        const val CONFLICT = "HTTP/1.1 409 Conflict\r\nContent-Type: application/json\r\n\r\n"
        const val NOT_FOUND = "HTTP/1.1 404 Not Found\r\nContent-Type: application/json\r\n\r\n"
        const val BAD_REQUEST = "HTTP/1.1 400 Bad Request\r\nContent-Type: application/json\r\n\r\n"
    }

    const val HTTP_METHOD_DIVIDER = " "
    const val HTTP_HEADER_DIVIDER = ":"

    const val HEADER_CONTENT_LENGTH = "Content-Length"
}