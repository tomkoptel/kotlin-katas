package com.sample.tom.ds.queue

import com.sample.tom.ds.heap.Heap

abstract class AbstractPriorityQueue<T : Any> : Queue<T> {
    abstract val heap: Heap<T>
        get

    override fun enqueue(element: T): Boolean {
        heap.insert(element)
        return true
    }

    override fun dequeue(): T? = heap.remove()

    override val count: Int
        get() = heap.count

    override fun peek(): T? = heap.peek()
}
