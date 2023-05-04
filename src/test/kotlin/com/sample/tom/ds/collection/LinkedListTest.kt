package com.sample.tom.ds.collection

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LinkedListTest {
    @Test
    fun `push adds a value at the front`() {
        val list1 = LinkedList<String>().push("1").push("2").push("3")
        "$list1" shouldBe "3 -> 2 -> 1"
        list1.size shouldBe 3

        val list2 = LinkedList<String>().push("3").push("2").push("1")
        "$list2" shouldBe "1 -> 2 -> 3"
        list2.size shouldBe 3
    }

    @Test
    fun `push adds a value at the end`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        "$list1" shouldBe "1 -> 2 -> 3"
        list1.size shouldBe 3

        val list2 = LinkedList<String>().append("3").append("2").append("1")
        "$list2" shouldBe "3 -> 2 -> 1"
        list2.size shouldBe 3
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
        list1.size shouldBe 2
        "$list1" shouldBe "2 -> 3"

        "${list1.pop()}" shouldBe "2"
        list1.size shouldBe 1
        "$list1" shouldBe "3"

        "${list1.pop()}" shouldBe "3"
        list1.size shouldBe 0
        "$list1" shouldBe "[]"

        list1.pop().shouldBeNull()
        list1.size shouldBe 0
        "$list1" shouldBe "[]"
    }

    @Test
    fun `removeLast removes the value at the end of the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        "${list1.removeLast()}" shouldBe "3"
        list1.size shouldBe 2
        "$list1" shouldBe "1 -> 2"

        "${list1.removeLast()}" shouldBe "2"
        list1.size shouldBe 1
        "$list1" shouldBe "1"

        "${list1.removeLast()}" shouldBe "1"
        list1.size shouldBe 0
        "$list1" shouldBe "[]"

        list1.removeLast().shouldBeNull()
        list1.size shouldBe 0
        "$list1" shouldBe "[]"
    }

    @Test
    fun `removeAfter removes a value after a particular node of the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")

        val node2 = list1.nodeAt(1).shouldNotBeNull()
        list1.removeAfter(node2) shouldBe "3"
        list1.size shouldBe 3
        "$list1" shouldBe "1 -> 2 -> 4"

        val node1 = list1.nodeAt(0).shouldNotBeNull()
        list1.removeAfter(node1) shouldBe "2"
        list1.size shouldBe 2
        "$list1" shouldBe "1 -> 4"

        val node4 = list1.nodeAt(1).shouldNotBeNull()
        list1.removeAfter(node4).shouldBeNull()
        list1.size shouldBe 2
        "$list1" shouldBe "1 -> 4"

        list1.removeAfter(node1) shouldBe "4"
        list1.size shouldBe 1
        "$list1" shouldBe "1"

        val list2 = LinkedList<String>().append("1").append("2").append("3").append("4")
        list2.removeAfter(list2.nodeAt(2).shouldNotBeNull()) shouldBe "4"
        "$list2" shouldBe "1 -> 2 -> 3"
    }

    @Test
    fun `list should support the iterator`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        val iterator = list1.iterator()
        iterator.hasNext() shouldBe true
        iterator.next() shouldBe "1"
        iterator.hasNext() shouldBe true
        iterator.next() shouldBe "2"
        iterator.hasNext() shouldBe true
        iterator.next() shouldBe "3"
        iterator.hasNext() shouldBe true
        iterator.next() shouldBe "4"
        iterator.hasNext() shouldBe false
    }

    @Test
    fun `list should support the mutable iterator`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3")
        val iterator1 = list1.iterator()
        iterator1.hasNext() shouldBe true
        iterator1.next() shouldBe "1"
        iterator1.remove()
        iterator1.hasNext() shouldBe true
        iterator1.next() shouldBe "2"
        iterator1.remove()
        iterator1.hasNext() shouldBe true
        iterator1.next() shouldBe "3"
        iterator1.remove()
        iterator1.hasNext() shouldBe false
        list1.isEmpty().shouldBeTrue()

        val list2 = LinkedList<String>().append("1").append("2").append("3")
        val iterator2 = list2.iterator()
        iterator2.hasNext() shouldBe true
        iterator2.next() shouldBe "1"
        iterator2.hasNext() shouldBe true
        iterator2.next() shouldBe "2"
        iterator2.remove()
        iterator2.hasNext() shouldBe true
        iterator2.next() shouldBe "3"
        iterator2.hasNext() shouldBe false
        "$list2" shouldBe "1 -> 3"
    }

    @Test
    fun `should support contains`() {
        val list1 = LinkedList<String>().append("1").append("2")
        list1.contains("1") shouldBe true
        list1.contains("2") shouldBe true
        list1.contains("3") shouldBe false
    }

    @Test
    fun `should support contains all`() {
        val list1 = LinkedList<String>().append("1").append("2")
        val list2 = LinkedList<String>().append("1").append("2").append("3").append("4")
        list2.containsAll(list1).shouldBeTrue()
        list1.containsAll(list2).shouldBeFalse()
    }

    @Test
    fun `should support isEmpty`() {
        val list1 = LinkedList<String>().append("1").append("2")
        val list2 = LinkedList<String>()
        list1.isEmpty().shouldBeFalse()
        list2.isEmpty().shouldBeTrue()
    }

    @Test
    fun `should support clear`() {
        val list1 = LinkedList<String>().append("1").append("2")
        list1.clear()
        list1.isEmpty().shouldBeTrue()
    }

    @Test
    fun `should support add`() {
        val list1 = LinkedList<String>()
        list1.add("1")
        list1.add("2")
        "$list1" shouldBe "1 -> 2"
    }

    @Test
    fun `should support addAll`() {
        val list1 = LinkedList<String>()
        list1.addAll(listOf("1", "2"))
        "$list1" shouldBe "1 -> 2"
    }

    @Test
    fun `should support retainAll`() {
        val list1 = LinkedList<String>().append("1").append("2")
        val list2 = LinkedList<String>().append("2").append("3")
        list1.retainAll(list2).shouldBeTrue()
        "$list1" shouldBe "2"

        val list3 = LinkedList<String>().append("2").append("3").append("4")
        val list4 = LinkedList<String>().append("2").append("3")
        list3.retainAll(list4).shouldBeTrue()
        "$list3" shouldBe "2 -> 3"

        val list5 = LinkedList<String>().append("2").append("3").append("4")
        val list6 = LinkedList<String>().append("2").append("3")
        list6.retainAll(list5).shouldBeFalse()
        "$list6" shouldBe "2 -> 3"
    }

    @Test
    fun `should support removeAll`() {
        val list1 = LinkedList<String>().append("1").append("2")
        val list2 = LinkedList<String>().append("2").append("3")
        list1.removeAll(list2).shouldBeTrue()
        "$list1" shouldBe "1"

        val list3 = LinkedList<String>().append("2").append("3").append("4")
        val list4 = LinkedList<String>().append("2").append("3")
        list3.removeAll(list4).shouldBeTrue()
        "$list3" shouldBe "4"

        val list5 = LinkedList<String>().append("2").append("3").append("4")
        val list6 = LinkedList<String>().append("2").append("3")
        list6.removeAll(list5)
        list6.isEmpty().shouldBeTrue()
    }

    @Test
    fun `immutableReverse should allow us to reverse the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        "${list1.immutableReverse()}" shouldBe "4 -> 3 -> 2 -> 1"

        val list2 = LinkedList<String>().append("1").append("2")
        "${list2.immutableReverse()}" shouldBe "2 -> 1"

        val list3 = LinkedList<String>().append("1")
        "${list3.immutableReverse()}" shouldBe "1"

        val list4 = LinkedList<String>()
        "${list4.immutableReverse()}" shouldBe "[]"

        val list5 = LinkedList<String>().append("3").append("2").append("1").append("4").append("5")
        "${list5.immutableReverse()}" shouldBe "5 -> 4 -> 1 -> 2 -> 3"
    }

    @Test
    fun `middle ext should give us middle node`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        "${list1.middle()}" shouldBe "3"

        val list2 = LinkedList<String>().append("1").append("2").append("3")
        "${list2.middle()}" shouldBe "2"

        val list3 = LinkedList<String>().append("1")
        "${list3.middle()}" shouldBe "1"

        val list4 = LinkedList<String>()
        list4.middle().shouldBeNull()
    }

    @Test
    fun `middleWithRunner ext should give us middle node`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        "${list1.middleWithRunner()}" shouldBe "3"

        val list2 = LinkedList<String>().append("1").append("2").append("3").append("4").append("5")
        "${list2.middleWithRunner()}" shouldBe "3"

        val list3 = LinkedList<String>().append("1")
        "${list3.middleWithRunner()}" shouldBe "1"

        val list4 = LinkedList<String>()
        list4.middleWithRunner().shouldBeNull()
    }

    @Test
    fun `mutableReverse should allow us to reverse the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        "${list1.mutableReverse()}" shouldBe "4 -> 3 -> 2 -> 1"

        val list2 = LinkedList<String>().append("1").append("2")
        "${list2.mutableReverse()}" shouldBe "2 -> 1"

        val list3 = LinkedList<String>().append("1")
        "${list3.mutableReverse()}" shouldBe "1"

        val list4 = LinkedList<String>()
        "${list4.mutableReverse()}" shouldBe "[]"

        val list5 = LinkedList<String>().append("3").append("2").append("1").append("4").append("5")
        "${list5.mutableReverse()}" shouldBe "5 -> 4 -> 1 -> 2 -> 3"
    }

    @Test
    fun `immutableRecursiveReverse should allow us to reverse the list`() {
        val list1 = LinkedList<String>().append("1").append("2").append("3").append("4")
        "${list1.immutableRecursiveReverse()}" shouldBe "4 -> 3 -> 2 -> 1"

        val list2 = LinkedList<String>().append("1").append("2")
        "${list2.immutableRecursiveReverse()}" shouldBe "2 -> 1"

        val list3 = LinkedList<String>().append("1")
        "${list3.immutableRecursiveReverse()}" shouldBe "1"

        val list4 = LinkedList<String>()
        "${list4.immutableRecursiveReverse()}" shouldBe "[]"

        val list5 = LinkedList<String>().append("3").append("2").append("1").append("4").append("5")
        "${list5.immutableRecursiveReverse()}" shouldBe "5 -> 4 -> 1 -> 2 -> 3"
    }
}
