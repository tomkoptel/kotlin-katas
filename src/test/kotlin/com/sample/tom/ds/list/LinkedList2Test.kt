package com.sample.tom.ds.list

import org.junit.jupiter.api.Test

class LinkedList2Test {

    @Test
    fun name() {
        val list = LinkedList<Int>()
        list.add(value = 1)
        list.add(value =2)
        list.add(value =3)
        list.add(value =0, index = 0)
        list.add(value =99, index = 1)
        println(list)
    }

    private class LinkedList<T : Any?> {
        private var head: Node<T>? = null
        private var tail: Node<T>? = null
        private var length = 0

        fun add(value: T, index: Int = length) {
            val newNode = Node(value = value, next = null)
            val clampedIndex = index.coerceAtMost(length)

            when (clampedIndex) {
                0 -> {
                    newNode.next = head
                    head = newNode
                    if (tail == null) tail = head
                }
                length -> {
                    tail?.next = newNode
                    tail = newNode
                }
                else -> {
                    var node = head
                    repeat(clampedIndex - 1) {node = node?.next}
                    node?.let {
                        newNode.next = node.next
                        node.next = newNode
                    }
                }
            }
            length++
        }

        fun remove(index: Int) {}

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
