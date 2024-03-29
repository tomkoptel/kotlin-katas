package com.sample.tom.ds.binary

import com.sample.tom.ds.binary.CollectionExt.binarySearch
import com.sample.tom.ds.binary.CollectionExt.findIndices
import com.sample.tom.ds.binary.CollectionExt.findIndicesRecursive
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BinarySearchTest {
    @Test
    fun testBinarySearchEmptyCollection() {
        listOf<Int>().binarySearch(5).shouldBeNull()
    }

    @Test
    fun testBinarySearchSingleElementCollection() {
        val single = listOf(10)
        single.binarySearch(10) shouldBe 0
        single.binarySearch(5).shouldBeNull()
        single.binarySearch(15).shouldBeNull()
    }

    @Test
    fun testBinarySearchMultipleElementCollection() {
        val multiple = listOf(1, 3, 5, 7, 9)
        multiple.binarySearch(1) shouldBe 0
        multiple.binarySearch(3) shouldBe 1
        multiple.binarySearch(5) shouldBe 2
        multiple.binarySearch(7) shouldBe 3
        multiple.binarySearch(9) shouldBe 4
        multiple.binarySearch(0).shouldBeNull()
        multiple.binarySearch(2).shouldBeNull()
        multiple.binarySearch(4).shouldBeNull()
        multiple.binarySearch(6).shouldBeNull()
        multiple.binarySearch(8).shouldBeNull()
        multiple.binarySearch(10).shouldBeNull()
    }

    @Test
    fun testBinarySearchStringCollection() {
        val strings = listOf("apple", "banana", "cherry", "date", "elderberry")
        strings.binarySearch("apple") shouldBe 0
        strings.binarySearch("banana") shouldBe 1
        strings.binarySearch("cherry") shouldBe 2
        strings.binarySearch("date") shouldBe 3
        strings.binarySearch("elderberry") shouldBe 4
        strings.binarySearch("aardvark").shouldBeNull()
        strings.binarySearch("berry").shouldBeNull()
        strings.binarySearch("coconut").shouldBeNull()
        strings.binarySearch("dragonfruit").shouldBeNull()
        strings.binarySearch("fig").shouldBeNull()
    }

    @Test
    fun findIndicesForMoreThanOneDuplicateSequential() {
        val array = arrayListOf(1, 2, 3, 3, 3, 4, 5, 5)
        val indices = array.findIndices(3)
        indices shouldBe (2..4)
    }

    @Test
    fun findIndicesForOneInstance() {
        val array = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
        val indices = array.findIndices(3)
        indices shouldBe (2..2)
    }

    @Test
    fun findIndicesForNoInstances() {
        val array = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
        val indices = array.findIndices(9)
        indices.shouldBeNull()
    }

    @Test
    fun findIndicesForEmptyList() {
        val array = emptyList<Int>()
        val indices = array.findIndices(9)
        indices.shouldBeNull()
    }

    @Test
    fun findIndicesRecursiveForMoreThanOneDuplicateSequential() {
        val array = arrayListOf(1, 2, 3, 3, 3, 4, 5, 5)
        val indices = array.findIndicesRecursive(3)
        indices shouldBe (2..4)
    }

    @Test
    fun findIndicesRecursiveForOneInstance() {
        val array = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
        val indices = array.findIndicesRecursive(3)
        indices shouldBe (2..2)
    }

    @Test
    fun findIndicesRecursiveForNoInstances() {
        val array = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
        val indices = array.findIndicesRecursive(9)
        indices.shouldBeNull()
    }

    @Test
    fun findIndicesRecursiveForEmptyList() {
        val array = emptyList<Int>()
        val indices = array.findIndicesRecursive(9)
        indices.shouldBeNull()
    }
}
