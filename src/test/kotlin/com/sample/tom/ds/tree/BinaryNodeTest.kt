package com.sample.tom.ds.tree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BinaryNodeTest {
    @Test
    fun smokeTest() {
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


        println(seven.toString())
    }
}
