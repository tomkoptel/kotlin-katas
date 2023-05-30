package com.sample.tom.ds.tree

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

class BinarySearchTreeTest {
    @Test
    fun insert() {
        val tree = BinarySearchTree<Int>().apply {
            insert(3)
            insert(1)
            insert(4)
            insert(0)
            insert(2)
            insert(5)
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

