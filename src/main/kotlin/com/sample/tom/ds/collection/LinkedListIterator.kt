package com.sample.tom.ds.collection

class LinkedListIterator<T : Any>(private val list: LinkedList<T>) : Iterator<T> {
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
}
