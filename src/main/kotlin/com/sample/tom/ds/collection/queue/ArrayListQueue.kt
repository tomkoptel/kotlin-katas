package com.sample.tom.ds.collection.queue

fun <T : Any> arrayListQueueOf(collection: Collection<T>): Queue<T> {
    val queue = ArrayListQueue<T>()
    for (item in collection) {
        queue.enqueue(item)
    }
    return queue
}

/**
 * Most of the operations are constant time
 * except for dequeue(), which takes linear time.
 * Storage space is also linear.
 */
class ArrayListQueue<T : Any> : Queue<T> {
    private var storage = arrayListOf<T>()

    override fun enqueue(element: T): Boolean {
        return storage.add(element)
    }

    override fun dequeue(): T? {
        if (storage.isEmpty()) return null
        return storage.removeAt(0)
    }

    override val count: Int
        get() = storage.size

    override fun peek(): T? {
        return storage.firstOrNull()
    }

    override fun toString(): String {
        return if (storage.isEmpty()) "[]" else storage.joinToString(separator = " > ")
    }
}
