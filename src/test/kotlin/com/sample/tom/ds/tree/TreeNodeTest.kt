package com.sample.tom.ds.tree

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

        tree.depthFirst { println(it.value) }
    }
}
