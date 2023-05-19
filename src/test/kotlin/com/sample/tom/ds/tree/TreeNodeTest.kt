package com.sample.tom.ds.tree

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class TreeNodeTest {
    @Test
    fun depthFirst() {
        val beverage = TreeNode(value = "Beverage")
        val hot = TreeNode(value = "Hot")
        val cold = TreeNode(value = "Cold")
        val tea = TreeNode(value = "Tea")
        val choco = TreeNode(value = "Choco")
        val shake = TreeNode(value = "Shake")

        beverage.add(hot)
        beverage.add(cold)
        hot.add(tea)
        hot.add(choco)
        cold.add(shake)

        beverage.depthFirst { println(it.value) }
    }

    @Test
    fun depthFirst2() {
        val tree = makeBeverageTree()

        tree.depthFirst { println(it.value) }
    }

    @Test
    fun levelOrder() {
        val tree = makeBeverageTree()

        tree.levelOrder { println(it.value) }
    }

    @Test
    fun search() {
        val tree = makeBeverageTree()
        tree.search("ginger ale").shouldNotBeNull().value shouldBe "ginger ale"
        tree.search("WKD Blue").shouldBeNull()
        tree.printEachLevel()
    }

    private fun makeBeverageTree(): TreeNode<String> {
        val tree = TreeNode("Beverages")

        val hot = TreeNode("hot")
        val cold = TreeNode("cold")

        val tea = TreeNode("tea")
        val coffee = TreeNode("coffee")
        val chocolate = TreeNode("cocoa")

        val blackTea = TreeNode("black")
        val greenTea = TreeNode("green")
        val chaiTea = TreeNode("chai")

        val soda = TreeNode("soda")
        val milk = TreeNode("milk")

        val gingerAle = TreeNode("ginger ale")
        val bitterLemon = TreeNode("bitter lemon")

        tree.add(hot)
        tree.add(cold)

        hot.add(tea)
        hot.add(coffee)
        hot.add(chocolate)

        cold.add(soda)
        cold.add(milk)

        tea.add(blackTea)
        tea.add(greenTea)
        tea.add(chaiTea)

        soda.add(gingerAle)
        soda.add(bitterLemon)
        return tree
    }
}
