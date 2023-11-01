package exception

open class RuntimeException @JvmOverloads constructor(
    override val message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)