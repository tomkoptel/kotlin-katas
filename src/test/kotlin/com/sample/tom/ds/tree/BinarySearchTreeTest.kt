package com.sample.tom.ds.tree

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

class BinarySearchTreeTest {
    @Test
    fun insert() {
        val tree = BinarySearchTree<Int>().apply {
            insertRecursive(3)
            insertRecursive(1)
            insertRecursive(4)
            insertRecursive(0)
            insertRecursive(2)
            insertRecursive(5)
        }
        tree.toString() shouldContain """
             ┌──5
            ┌──4
            │ └──null
            3
            │ ┌──2
            └──1
             └──0
        """.trimIndent()
    }
}

