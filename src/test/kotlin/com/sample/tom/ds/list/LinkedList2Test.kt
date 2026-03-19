package com.sample.tom.ds.list

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LinkedList2Test {

    @Test
    fun `add single element to empty list`() {
        val list = LinkedList<Int>()
        list.add(1)
        assertEquals("length: 1 [1] tail=Node(value=1, next=null)", list.toString())
    }

    @Test
    fun `append multiple elements`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals("length: 3 [1 -> 2 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `prepend to empty list with explicit index 0`() {
        val list = LinkedList<Int>()
        list.add(value = 1, index = 0)
        assertEquals("length: 1 [1] tail=Node(value=1, next=null)", list.toString())
    }

    @Test
    fun `prepend to non-empty list`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 0, index = 0)
        assertEquals("length: 4 [0 -> 1 -> 2 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `insert at middle index`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 99, index = 1)
        assertEquals("length: 4 [1 -> 99 -> 2 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `insert at second to last position`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 99, index = 2)
        assertEquals("length: 4 [1 -> 2 -> 99 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `out of bounds index appends to end`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(value = 99, index = 10)
        assertEquals("length: 2 [1 -> 99] tail=Node(value=99, next=null)", list.toString())
    }

    @Test
    fun `combined prepend append and insert`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 0, index = 0)
        list.add(value = 99, index = 1)
        assertEquals("length: 5 [0 -> 99 -> 1 -> 2 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `remove head from single element list`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.remove(0)
        assertEquals("length: 0 [] tail=null", list.toString())
    }

    @Test
    fun `remove head from multi element list`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(0)
        assertEquals("length: 2 [2 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `remove middle element`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(1)
        assertEquals("length: 2 [1 -> 3] tail=Node(value=3, next=null)", list.toString())
    }

    @Test
    fun `remove last element`() {
        val list = LinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(2)
        assertEquals("length: 2 [1 -> 2] tail=Node(value=2, next=null)", list.toString())
    }

    @Test
    fun `remove from empty list does nothing`() {
        val list = LinkedList<Int>()
        list.remove(0)
        assertEquals("length: 0 [] tail=null", list.toString())
    }

    private class LinkedList<T : Any?> {
        private var head: Node<T>? = null
        private var tail: Node<T>? = null
        private var length = 0

        fun add(value: T, index: Int = length) {
            val newNode = Node(value = value, next = null)
            val clampedIndex = index.coerceAtMost(length)

            when {
                clampedIndex == 0 -> {
                    newNode.next = head
                    head = newNode
                    if (tail == null) tail = head
                }

                clampedIndex == length -> {
                    tail?.next = newNode
                    tail = newNode
                }

                else -> {
                    var node = head
                    repeat(clampedIndex - 1) {
                        node = head?.next
                    }

                    node?.let {
                        newNode.next = node.next
                        node.next = newNode
                    }
                }
            }
            length++
        }

        fun remove(index: Int) {
            if (length == 0) return
            val lastIndex = length - 1
            val clampedIndex = index.coerceAtMost(lastIndex)
            when {
                clampedIndex == 0 -> {
                    head = head?.next
                    if (head == null) tail = null
                }

                else -> {
                    var node = head
                    repeat(clampedIndex - 1) {
                        node = node?.next
                    }
                    node?.next = node.next?.next
                    if (node?.next == null) tail = node
                }
            }
            length--
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LinkedList<*>

            return head == other.head
        }

        override fun hashCode(): Int {
            return head?.hashCode() ?: 0
        }

        override fun toString(): String {
            var previousNode: Node<T>? = null
            var nextNode = head
            val string = StringBuilder()
            while (nextNode != null) {
                if (previousNode == null) {
                    string.append(nextNode.value)
                } else {
                    string.append(" -> ").append(nextNode.value)
                }
                previousNode = nextNode
                nextNode = nextNode.next
            }

            return "length: $length [$string] tail=$tail"
        }

        private class Node<T : Any?>(
            val value: T? = null,
            var next: Node<T>? = null,
        ) {

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Node<*>

                if (value != other.value) return false
                if (next != other.next) return false

                return true
            }

            override fun hashCode(): Int {
                var result = value?.hashCode() ?: 0
                result = 31 * result + (next?.hashCode() ?: 0)
                return result
            }

            override fun toString(): String {
                return "Node(value=$value, next=$next)"
            }
        }
    }
}
