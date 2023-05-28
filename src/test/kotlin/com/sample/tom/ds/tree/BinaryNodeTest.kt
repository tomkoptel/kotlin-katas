package com.sample.tom.ds.tree

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BinaryNodeTest {
    private val collector = mutableListOf<String>()

    private fun collect(any: Any) {
        println(any)
        collector += "$any"
    }

    private fun orderedResult(): String = collector.joinToString(separator = "->")

    @Test
    fun preOrder() {
        val binaryNode = binaryNode()
        println(binaryNode)
        binaryNode.preOrder(::collect)
        orderedResult() shouldBe "7->1->0->5->9->8"
    }

    @Test
    fun inOrder() {
        val binaryNode = binaryNode()
        println(binaryNode)
        binaryNode.inOrder(::collect)
        orderedResult() shouldBe "0->1->5->7->8->9"
    }

    @Test
    fun postOrder() {
        val binaryNode = binaryNode()
        println(binaryNode)
        binaryNode.postOrder(::collect)
        orderedResult() shouldBe "0->5->1->8->9->7"
    }

    @Test
    fun height() {
        binaryNode().height shouldBe 3
        BinaryNode(0).height shouldBe 1
    }

    @Test
    fun heightRecursive() {
        binaryNode().height() shouldBe 3
        BinaryNode(0).height() shouldBe 1
    }

    private fun binaryNode(): BinaryNode<Int> {
        val zero = BinaryNode(0)
        val one = BinaryNode(1)
        val five = BinaryNode(5)
        val seven = BinaryNode(7)
        val eight = BinaryNode(8)
        val nine = BinaryNode(9)

        seven.leftChild = one
        one.leftChild = zero
        one.rightChild = five
        seven.rightChild = nine
        nine.leftChild = eight
        return seven
    }
}
