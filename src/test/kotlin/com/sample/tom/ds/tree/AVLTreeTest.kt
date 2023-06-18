package com.sample.tom.ds.tree

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

class AVLTreeTest {
    @Test
    fun insert() {
        val tree = AVLTree<Int>()

        (0..14).forEach {
            tree.insert(it)
        }

        tree.toString() shouldContain """
              ┌──14
             ┌──13
             │ └──12
            ┌──11
            │ │ ┌──10
            │ └──9
            │  └──8
            7
            │  ┌──6
            │ ┌──5
            │ │ └──4
            └──3
             │ ┌──2
             └──1
              └──0
        """.trimIndent()
    }
}
