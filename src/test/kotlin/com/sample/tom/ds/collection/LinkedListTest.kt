package com.sample.tom.ds.collection

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
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

    @Test
    fun `allow us to track node at position`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        list1.nodeAt(index = 0).shouldNotBeNull().value shouldBe "1"
        list1.nodeAt(index = 1).shouldNotBeNull().value shouldBe "2"
        list1.nodeAt(index = 2).shouldNotBeNull().value shouldBe "3"
    }

    @Test
    fun `adds a value after particular node`() {
        val list1 = LinkedList<String>().append("1").append("2").append("5")
        val node1 = list1.nodeAt(1).shouldNotBeNull()

        "${list1.insert("4", node1)}" shouldBe "4 -> 5"
        "$list1" shouldBe "1 -> 2 -> 4 -> 5"

        "${list1.insert("3", node1)}" shouldBe "3 -> 4 -> 5"
        "$list1" shouldBe "1 -> 2 -> 3 -> 4 -> 5"

        val list2 = LinkedList<String>().append("1")
        val tail = list2.nodeAt(0).shouldNotBeNull()
        "${list2.insert("2", tail)}" shouldBe "2"
        "$list2" shouldBe "1 -> 2"
    }

    @Test
    fun `pop removes the value at the front of the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        "${list1.pop()}" shouldBe "1"
        list1.getSize() shouldBe 2
        "$list1" shouldBe "2 -> 3"

        "${list1.pop()}" shouldBe "2"
        list1.getSize() shouldBe 1
        "$list1" shouldBe "3"

        "${list1.pop()}" shouldBe "3"
        list1.getSize() shouldBe 0
        "$list1" shouldBe "[]"

        list1.pop().shouldBeNull()
        list1.getSize() shouldBe 0
        "$list1" shouldBe "[]"
    }

    @Test
    fun `removeLast removes the value at the end of the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        "${list1.removeLast()}" shouldBe "3"
        list1.getSize() shouldBe 2
        "$list1" shouldBe "1 -> 2"

        "${list1.removeLast()}" shouldBe "2"
        list1.getSize() shouldBe 1
        "$list1" shouldBe "1"

        "${list1.removeLast()}" shouldBe "1"
        list1.getSize() shouldBe 0
        "$list1" shouldBe "[]"

        list1.removeLast().shouldBeNull()
        list1.getSize() shouldBe 0
        "$list1" shouldBe "[]"
    }

    @Test
    fun `removeAfter removes a value after a particular node of the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")

        val node2 = list1.nodeAt(1).shouldNotBeNull()
        list1.removeAfter(node2) shouldBe "3"
        list1.getSize() shouldBe 3
        "$list1" shouldBe "1 -> 2 -> 4"

        val node1 = list1.nodeAt(0).shouldNotBeNull()
        list1.removeAfter(node1) shouldBe "2"
        list1.getSize() shouldBe 2
        "$list1" shouldBe "1 -> 4"

        val node4 = list1.nodeAt(1).shouldNotBeNull()
        list1.removeAfter(node4).shouldBeNull()
        list1.getSize() shouldBe 2
        "$list1" shouldBe "1 -> 4"

        list1.removeAfter(node1) shouldBe "4"
        list1.getSize() shouldBe 1
        "$list1" shouldBe "1"

        val list2 = LinkedList<String>().append("1").append("2").append("3").append("4")
        list2.removeAfter(list2.nodeAt(2).shouldNotBeNull()) shouldBe "4"
        "$list2" shouldBe "1 -> 2 -> 3"
    }
}
