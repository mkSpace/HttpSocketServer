package exception

class BadRequestException(cause: Throwable? = null): RuntimeException("Bad Request Exception!", cause)