package com.sample.tom.ds.queue

import java.util.*

fun <T : Any> linkedListQueueQueueOf(collection: Collection<T>): Queue<T> {
    val queue = LinkedListQueue<T>()
    for (item in collection) {
        queue.enqueue(item)
    }
    return queue
}

/**
 * The main weakness with LinkedListQueue is not apparent from the table.
 * Despite O(1) performance, it suffers from high overhead. Each element
 * has to have extra storage for the forward and back reference. Moreover,
 * every time you create a new element, it requires a relatively expensive
 * dynamic allocation. By contrast, ArrayListQueue does bulk allocation, which is faster.
 */
class LinkedListQueue<T : Any> : Queue<T> {
    private val storage = LinkedList<T>()

    override fun enqueue(element: T): Boolean {
        return storage.add(element)
    }

    override fun dequeue(): T? {
        return try {
            storage.pop()
        } catch (ex: NoSuchElementException) {
            null
        }
    }

    override val count: Int
        get() = storage.size

    override fun peek(): T? {
        return storage.first
    }

    override fun toString(): String {
        return if (storage.isEmpty()) "[]" else storage.joinToString(separator = " > ")
    }
}
