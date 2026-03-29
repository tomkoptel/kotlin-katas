package com.sample.tom.ds.list

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QueueTest {
    @Test
    fun enqueue() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.toString() shouldBe "1->2->3 length=3"
    }

    @Test
    fun dequeue() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)


        queue.dequeue() shouldBe 1
        queue.dequeue() shouldBe 2
        queue.toString() shouldBe "3 length=1"
        queue.dequeue() shouldBe 3
        queue.toString() shouldBe "length=0"
    }

    private class Queue<T> {
        class Node<T>(
            val value: T,
            var next: Node<T>? = null,
        ) {
            override fun toString(): String {
                return "Node(next=$next)"
            }
        }

        private var head: Node<T>? = null
        private var tail: Node<T>? = null
        private var length = 0

        fun enqueue(value: T) {
            val newNode = Node(value)
            if (head == null) {
                head = newNode
                tail = newNode
            } else {
                tail?.next = newNode
                tail = newNode
            }
            length++
        }

        fun dequeue(): T? {
            val currentHead = head ?: return null
            if (head == tail) {
                tail = null
            }
            head = currentHead.next
            length--
            return currentHead.value
        }

        override fun toString(): String {
            return if (head == null) {
                "length=$length"
            } else {
                var node = head
                val builder = StringBuilder()
                while (node != null) {
                    builder.append(node.value)
                    node = node.next?.also {
                        builder.append("->")
                    }
                }
                "$builder length=$length"
            }
        }
    }
}
