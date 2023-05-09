package com.sample.tom.ds.collection.queue

import com.sample.tom.ds.collection.Stack

fun <T : Any> Queue<T>.reverse(): Queue<T> {
    val queue = linkedListQueueQueueOf(listOf<T>())
    val stack = Stack<T>()
    var next = dequeue()
    while (next != null) {
        stack.push(next)
        next = dequeue()
    }
    next = stack.pop()
    while (next != null) {
        queue.enqueue(next)
        next = stack.pop()
    }
    return queue
}

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
