package com.sample.tom.ds.collection


/**
 * $head -> $node0 -> $node1 -> $tail -> null
 */
class LinkedList<T : Any> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    fun getSize(): Int = size

    fun push(item: T) {
        head = Node(value = item, next = head)
        if (tail == null) {
            tail = head
        }
        size++
    }

    fun append() {}

    fun insert() {}

    override fun toString(): String {
        return when {
            getSize() == 0 -> "Empty list"
            else -> return "$head"
        }
    }
}
