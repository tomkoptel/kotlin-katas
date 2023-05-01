package com.sample.tom.ds.collection

/**
 * $head -> $node0 -> $node1 -> $tail -> null
 */
class LinkedList<T : Any> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    fun getSize(): Int = size

    fun push(item: T): LinkedList<T> = apply {
        head = Node(value = item, next = head)
        if (tail == null) {
            tail = head
        }
        size++
    }

    fun append(item: T): LinkedList<T> = apply {
        val tail = tail
        if (tail == null) {
            push(item)
        } else {
            val newNode = Node(item)
            tail.next = newNode
            this.tail = newNode
            size++
        }
    }

    fun nodeAt(index: Int): Node<T>? {
        val head = head ?: return null

        var currentIndex = 0
        var currentNode: Node<T>? = head

        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }

        return currentNode
    }

    fun insert(item: T, atNode: Node<T>): Node<T> {
        val atNodeNext = atNode.next
        val newNode = Node(item, next = atNodeNext)
        atNode.next = newNode
        size++
        return newNode
    }

    fun pop(): Node<T>? {
        val head = head ?: return null
        val nextAfter = head.next
        this.head = nextAfter
        size--
        if (getSize() == 0) {
            tail = null
        }
        return head.also { it.next = null }
    }

    override fun toString(): String {
        return when {
            getSize() == 0 -> "[]"
            else -> return "$head"
        }
    }
}
