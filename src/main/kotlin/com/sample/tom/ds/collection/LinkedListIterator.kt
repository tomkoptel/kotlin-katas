package com.sample.tom.ds.collection

class LinkedListIterator<T : Any>(private val list: LinkedList<T>) : MutableIterator<T> {
    private var currentIndex: Int = 0
    private var lastNode: Node<T>? = null

    override fun hasNext(): Boolean {
        return currentIndex < list.size
    }

    override fun next(): T {
        if (currentIndex >= list.size) throw IndexOutOfBoundsException()

        lastNode = if (currentIndex == 0) {
            list.nodeAt(0)
        } else {
            lastNode?.next
        }
        currentIndex++
        return lastNode!!.value
    }

    override fun remove() {
        if (currentIndex == 1) {
            list.pop()
        } else {
            val prevNode = list.nodeAt(currentIndex - 2) ?: throw IllegalStateException()
            list.removeAfter(prevNode)
            lastNode = prevNode
        }
        currentIndex--
    }
}
