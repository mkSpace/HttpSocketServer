package exception

class ConflictException(cause: Throwable? = null) : RuntimeException("Conflict Exception!!", cause)