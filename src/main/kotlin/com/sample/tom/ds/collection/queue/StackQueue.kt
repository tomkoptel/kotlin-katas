package com.sample.tom.ds.collection.queue

import com.sample.tom.ds.collection.Stack

fun <T : Any> stackQueueOf(collection: Collection<T>): Queue<T> {
    val queue = StackQueue<T>()
    for (item in collection) {
        queue.enqueue(item)
    }
    return queue
}

/**
 * Compared to the list-based implementation, by leveraging two stacks, you were able to transform dequeue() into an amortized O(1) operation.
 * Moreover, your two-stack implementation is fully dynamic and doesn’t have the fixed size restriction that your ring-buffer-based queue implementation has.
 * Finally, it beats the linked list in terms of spatial locality. This is because list elements are next to each other in memory blocks. So a large number of elements will be loaded in a cache on first access.”
 */
class StackQueue<T : Any> : Queue<T> {
    private val left = Stack<T>()
    private val right = Stack<T>()

    override fun enqueue(element: T): Boolean {
        right.push(element)
        return true
    }

    override fun dequeue(): T? {
        if (left.isEmpty) {
            transferElements()
        }
        return left.pop()
    }

    override fun peek(): T? {
        if (left.isEmpty) {
            transferElements()
        }
        return left.peek()
    }

    private fun transferElements() {
        for (i in (0 until right.size)) {
            right.pop()?.let { left.push(it) }
        }
    }

    override val count: Int
        get() = left.size + right.size

    override val isEmpty: Boolean
        get() = left.isEmpty && right.isEmpty

    override fun toString(): String {
        return "StackQueue(left=$left, right=$right)"
    }
}
