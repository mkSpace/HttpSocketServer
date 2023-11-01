package exception

class NotFoundException(cause: Throwable? = null) : RuntimeException("Not Found Exception!!", cause)