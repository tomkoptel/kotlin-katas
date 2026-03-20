package com.sample.tom.ds.list

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DoublyLinkedListTest {

    // --- add tests ---

    @Test
    fun `add single element to empty list`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        assertEquals("length: 1 [1] reverse: [1] tail=1", list.toString())
    }

    @Test
    fun `append multiple elements`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals("length: 3 [1 -> 2 -> 3] reverse: [3 -> 2 -> 1] tail=3", list.toString())
    }

    @Test
    fun `prepend to empty list with explicit index 0`() {
        val list = DoublyLinkedList<Int>()
        list.add(value = 1, index = 0)
        assertEquals("length: 1 [1] reverse: [1] tail=1", list.toString())
    }

    @Test
    fun `prepend to non-empty list`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 0, index = 0)
        assertEquals("length: 4 [0 -> 1 -> 2 -> 3] reverse: [3 -> 2 -> 1 -> 0] tail=3", list.toString())
    }

    @Test
    fun `insert at middle index`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 99, index = 1)
        assertEquals("length: 4 [1 -> 99 -> 2 -> 3] reverse: [3 -> 2 -> 99 -> 1] tail=3", list.toString())
    }

    @Test
    fun `insert at second to last position`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 99, index = 2)
        assertEquals("length: 4 [1 -> 2 -> 99 -> 3] reverse: [3 -> 99 -> 2 -> 1] tail=3", list.toString())
    }

    @Test
    fun `out of bounds index appends to end`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(value = 99, index = 10)
        assertEquals("length: 2 [1 -> 99] reverse: [99 -> 1] tail=99", list.toString())
    }

    @Test
    fun `combined prepend append and insert`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(value = 0, index = 0)
        list.add(value = 99, index = 1)
        assertEquals("length: 5 [0 -> 99 -> 1 -> 2 -> 3] reverse: [3 -> 2 -> 1 -> 99 -> 0] tail=3", list.toString())
    }

    // --- remove tests ---

    @Test
    fun `remove head from single element list`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.remove(0)
        assertEquals("length: 0 [] reverse: [] tail=null", list.toString())
    }

    @Test
    fun `remove head from multi element list`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(0)
        assertEquals("length: 2 [2 -> 3] reverse: [3 -> 2] tail=3", list.toString())
    }

    @Test
    fun `remove middle element`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(1)
        assertEquals("length: 2 [1 -> 3] reverse: [3 -> 1] tail=3", list.toString())
    }

    @Test
    fun `remove last element`() {
        val list = DoublyLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.remove(2)
        assertEquals("length: 2 [1 -> 2] reverse: [2 -> 1] tail=2", list.toString())
    }

    @Test
    fun `remove from empty list does nothing`() {
        val list = DoublyLinkedList<Int>()
        list.remove(0)
        assertEquals("length: 0 [] reverse: [] tail=null", list.toString())
    }

    private class DoublyLinkedList<T : Any?> {
        private var head: Node<T>? = null
        private var tail: Node<T>? = null
        private var length = 0

        fun add(value: T, index: Int = length) {
            val clampedIndex = index.coerceAtMost(length)
            val newNode = Node(value = value)
            when {
                clampedIndex == 0 -> {
                    val previousHead = head
                    previousHead?.prev = newNode
                    head = newNode
                    newNode.next = previousHead
                    if (tail == null) tail = head
                }

                clampedIndex == length -> {
                    val previousTail = tail
                    tail = newNode
                    newNode.prev = previousTail
                    previousTail?.next = newNode
                }

                else -> {
                    var node = head
                    repeat(clampedIndex - 1) {
                        node = node?.next
                    }
                    val previousNext = node?.next
                    node?.next = newNode
                    newNode.prev = node
                    previousNext?.prev = newNode
                    newNode.next = previousNext
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
                    val nextAfterHead = head?.next
                    head = nextAfterHead
                    nextAfterHead?.prev = null
                    if (head == null) tail = null
                    length--
                }

                clampedIndex == lastIndex -> {
                    val newTail = tail?.prev
                    newTail?.next = null
                    tail = newTail
                    length--
                }

                else -> {
                    var node = head
                    repeat(clampedIndex) {
                        node = node?.next
                    }
                    // 1 -> 2 -> 3
                    val newNext = node?.next // 3
                    val previous = node?.prev // 1
                    previous?.next = newNext
                    newNext?.prev = previous
                    length--
                }
            }
        }

        override fun toString(): String {
            val forward = StringBuilder()
            var node = head
            while (node != null) {
                if (forward.isNotEmpty()) forward.append(" -> ")
                forward.append(node.value)
                node = node.next
            }

            val reverse = StringBuilder()
            node = tail
            while (node != null) {
                if (reverse.isNotEmpty()) reverse.append(" -> ")
                reverse.append(node.value)
                node = node.prev
            }

            return "length: $length [$forward] reverse: [$reverse] tail=${tail?.value}"
        }

        private class Node<T : Any?>(
            val value: T,
            var next: Node<T>? = null,
            var prev: Node<T>? = null,
        ) {
            override fun toString(): String {
                return "Node(value=$value)"
            }
        }
    }
}
