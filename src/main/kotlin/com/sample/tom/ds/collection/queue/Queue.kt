package com.sample.tom.ds.collection.queue

interface Queue<T : Any> {
    /**
     * Inserts an element at the back of the queue and returns true if the operation is successful.
     */
    fun enqueue(element: T): Boolean

    /**
     * Removes an element from the front of the queue and returns that element.
     */
    fun dequeue(): T?

    val count: Int

    val isEmpty: Boolean
        get() = count == 0

    /**
     * Returns the element at the front of the queue without removing it from the queue.
     */
    fun peek(): T?
}
