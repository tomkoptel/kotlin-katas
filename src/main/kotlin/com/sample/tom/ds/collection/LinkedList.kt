package com.sample.tom.ds.collection

/**
 * $head -> $node0 -> $node1 -> $tail -> null
 */
class LinkedList<T : Any> : Iterable<T>, Collection<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size = 0
    override val size: Int
        get() = _size

    override fun isEmpty(): Boolean {
        return _size == 0
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        elements.forEach {
            if (!contains(it)) return false
        }
        return true
    }

    override fun contains(element: T): Boolean {
        for (el in this) {
            if (el == element) return true
        }
        return false
    }

    fun push(item: T): LinkedList<T> = apply {
        head = Node(value = item, next = head)
        if (tail == null) {
            tail = head
        }
        _size++
    }

    fun append(item: T): LinkedList<T> = apply {
        val tail = tail
        if (tail == null) {
            push(item)
        } else {
            val newNode = Node(item)
            tail.next = newNode
            this.tail = newNode
            _size++
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
        _size++
        return newNode
    }

    fun pop(): Node<T>? {
        val head = head ?: return null
        val nextAfter = head.next
        this.head = nextAfter
        _size--
        if (size == 0) {
            tail = null
        }
        return head.also { it.next = null }
    }

    fun removeLast(): Node<T>? {
        val tail = tail ?: return null
        nodeAt(size - 2)?.let {
            it.next = null
            this.tail = it
            _size--
        }
        if (isEmpty()) {
            this.tail = null
            head = null
        }
        return tail
    }

    fun removeAfter(node: Node<T>): T? {
        val nodeAfterThis = node.next ?: return null
        if (nodeAfterThis == tail) {
            tail = node
        }
        node.next = nodeAfterThis.next
        _size--
        return nodeAfterThis.value
    }

    override fun iterator(): Iterator<T> = LinkedListIterator(this)

    override fun toString(): String {
        return when {
            isEmpty() -> "[]"
            else -> return "$head"
        }
    }
}
