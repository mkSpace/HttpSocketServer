package storage

interface Storage {
    fun get(key: Int): String

    fun getAll(): String

    fun add(key: Int, value: String)

    fun delete(key: Int)

    fun update(key: Int, value: String)
}