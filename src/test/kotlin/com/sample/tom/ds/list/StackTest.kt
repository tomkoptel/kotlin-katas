package com.sample.tom.ds.list

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StackTest {
    @Test
    fun push() {
        val stack = Stack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.toString() shouldBe "3<--2<--1 length=3"
    }

    @Test
    fun pop() {
        val stack = Stack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)

        stack.pop()?.value shouldBe 3
        stack.pop()?.value shouldBe 2
        stack.toString() shouldBe "1 length=1"
        stack.pop()?.value shouldBe 1

        stack.pop().shouldBeNull()
    }

    @Test
    fun peek() {
        val stack = Stack<Int>()
        stack.push(1)

        stack.toString() shouldBe "1 length=1"
        stack.peek()?.value shouldBe 1
    }

    private class Stack<T> {
        class Node<T>(val value: T, var next: Node<T>? = null) {
            override fun toString(): String = "Node(value=$value)"
        }

        private var top: Node<T>? = null
        private var length = 0

        fun push(value: T) {
            val newNode = Node<T>(value)
            if (top == null) {
                top = newNode
            } else {
                val previousTop = top
                top = newNode
                newNode.next = previousTop
            }
            length++
        }

        fun peek(): Node<T>? = top

        fun pop(): Node<T>? {
            val currentTop = top ?: return null
            top = currentTop.next
            length--
            return currentTop
        }

        override fun toString(): String {
            val builder = StringBuilder()
            var node = top
            if (node != null) {
                builder.append("${node.value}")
                node = node.next
            }
            while (node != null) {
                builder.append("<--")
                builder.append("${node.value}")
                node = node.next
            }
            return if (top == null) {
                "length=$length"
            } else {
                "$builder length=$length"
            }
        }
    }
}
