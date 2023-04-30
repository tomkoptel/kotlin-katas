package com.sample.tom.ds.collection

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LinkedListTest {
    @Test
    fun `push adds a value at the front`() {
        val list1 = LinkedList<String>().push("1").push("2").push("3")
        "$list1" shouldBe "3 -> 2 -> 1"
        list1.getSize() shouldBe 3

        val list2 = LinkedList<String>().push("3").push("2").push("1")
        "$list2" shouldBe "1 -> 2 -> 3"
        list2.getSize() shouldBe 3
    }

    @Test
    fun `push adds a value at the end`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        "$list1" shouldBe "1 -> 2 -> 3"
        list1.getSize() shouldBe 3

        val list2 = LinkedList<String>().append("3").append("2").append("1")
        "$list2" shouldBe "3 -> 2 -> 1"
        list2.getSize() shouldBe 3
    }
}

