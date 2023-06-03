package com.sample.tom.ds.tree

import io.kotest.matchers.booleans.shouldBeTrue
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

    @Test
    fun insertRecursive() {
        val tree = tree()
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

    @Test
    fun contains() {
        val tree = tree()
        tree.contains(4).shouldBeTrue()
    }

    @Test
    fun removeRecursive() {
        val tree = tree()
        tree.removeRecursive(3)
        "$tree" shouldContain """
            ┌──5
            4
            │ ┌──2
            └──1
             └──0
        """.trimIndent()
    }

    @Test
    fun remove() {
        val tree = tree()
        tree.remove(3)
        "$tree" shouldContain """
            ┌──5
            4
            │ ┌──2
            └──1
             └──0
        """.trimIndent()
    }

    @Test
    fun remove_leave_left() {
        val tree = biggerTree()
        "$tree" shouldContain """
             ┌──50
            ┌──40
            │ └──35
            30
            │  ┌──25
            │ ┌──20
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             │ ┌──null
             └──9
              └──8
        """.trimIndent()
        tree.remove(8)
        "$tree" shouldContain """
             ┌──50
            ┌──40
            │ └──35
            30
            │  ┌──25
            │ ┌──20
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             └──9
        """.trimIndent()
    }

    @Test
    fun remove_leave_right() {
        val tree = biggerTree()
        "$tree" shouldContain """
             ┌──50
            ┌──40
            │ └──35
            30
            │  ┌──25
            │ ┌──20
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             │ ┌──null
             └──9
              └──8
        """.trimIndent()
        tree.remove(50)
        "$tree" shouldContain """
             ┌──null
            ┌──40
            │ └──35
            30
            │  ┌──25
            │ ┌──20
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             │ ┌──null
             └──9
              └──8
        """.trimIndent()
    }

    @Test
    fun remove_node_with_2_branches() {
        val tree = biggerTree()
        "$tree" shouldContain """
             ┌──50
            ┌──40
            │ └──35
            30
            │  ┌──25
            │ ┌──20
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             │ ┌──null
             └──9
              └──8
        """.trimIndent()
        tree.remove(20)
        "$tree" shouldContain """
             ┌──50
            ┌──40
            │ └──35
            30
            │  ┌──null
            │ ┌──25
            │ │ │ ┌──12
            │ │ └──11
            │ │  └──null
            └──10
             │ ┌──null
             └──9
              └──8
        """.trimIndent()
    }

    private fun biggerTree() = BinarySearchTree<Int>().apply {
        /**
            ┌──50
        ┌──40
        │   └──null
        30
        │    ┌──25
        │   ┌──20
        │   │ └──11
        └──10
            │ ┌──null
            └──9
              └──8
         */
        insert(30)
        insert(10)
        insert(40)
        insert(9)
        insert(20)
        insert(50)
        insert(25)
        insert(8)
        insert(11)
        insert(35)
        insert(12)
    }

    private fun tree() = BinarySearchTree<Int>().apply {
        insertRecursive(3)
        insertRecursive(1)
        insertRecursive(4)
        insertRecursive(0)
        insertRecursive(2)
        insertRecursive(5)
    }
}

