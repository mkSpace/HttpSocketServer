package storage

import exception.ConflictException
import exception.NotFoundException
import java.util.concurrent.ConcurrentHashMap

class InMemoryStorage : Storage {

    private val concurrentHashMap: ConcurrentHashMap<Int, String> = ConcurrentHashMap()

    override fun get(key: Int): String {
        return concurrentHashMap[key] ?: throw NotFoundException()
    }

    override fun getAll(): String {
        return concurrentHashMap.toString()
    }

    override fun add(key: Int, value: String) {
        if (concurrentHashMap.containsKey(key)) throw ConflictException()
        concurrentHashMap[key] = value
    }

    override fun delete(key: Int) {
        if (!concurrentHashMap.containsKey(key)) throw NotFoundException()
        concurrentHashMap.remove(key)
    }

    override fun update(key: Int, value: String) {
        if (!concurrentHashMap.containsKey(key)) throw NotFoundException()
        concurrentHashMap[key] = value
    }
}