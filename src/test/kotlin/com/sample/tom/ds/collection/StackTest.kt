package com.sample.tom.ds.collection

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StackTest {
    @Test
    fun `should support pushing and poping`() {
        val stack = Stack<Int>()
        stack.push(1) shouldBe 1
        stack.push(2) shouldBe 2

        stack.pop() shouldBe 2
        stack.pop() shouldBe 1
        stack.pop().shouldBeNull()
    }

    @Test
    fun `stackOf creates new stack`() {
        val stack = stackOf(listOf(1, 2, 3, 4))
        stack.pop() shouldBe 4
        stack.pop() shouldBe 3
        stack.pop() shouldBe 2
        stack.pop() shouldBe 1
        stack.pop().shouldBeNull()
    }

    @Test
    fun inReverseWithStack() {
        val list = LinkedList<Int>().append(1).append(2).append(3).append(4)
        "${list.inReverseWithStack()}" shouldBe "4 -> 3 -> 2 -> 1"
    }
}
