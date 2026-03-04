package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.TreeMap

class `0543_DiameterBinaryTree` {
    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     * class TreeNode(var `val`: Int) {
     *     var left: TreeNode? = null
     *     var right: TreeNode? = null
     * }
     */
    @Test
    fun `example 1 - diameter passes through root`() {
        // Tree:       1
        //            / \
        //           2   3
        //          / \
        //         4   5
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(4)
                right = TreeNode(5)
            }
            right = TreeNode(3)
        }
        Solution().diameterOfBinaryTree(root) shouldBe 3
    }

    @Test
    fun `example 2 - two nodes`() {
        // Tree: 1
        //        \
        //         2
        val root = TreeNode(1).apply {
            right = TreeNode(2)
        }
        Solution().diameterOfBinaryTree(root) shouldBe 1
    }

    @Test
    fun `single node`() {
        Solution().diameterOfBinaryTree(TreeNode(1)) shouldBe 0
    }

    @Test
    fun `null root`() {
        Solution().diameterOfBinaryTree(null) shouldBe 0
    }

    @Test
    fun `diameter does not pass through root`() {
        // Tree:       1
        //            /
        //           2
        //          / \
        //         3   4
        //        /     \
        //       5       6
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(5)
                }
                right = TreeNode(4).apply {
                    right = TreeNode(6)
                }
            }
        }
        Solution().diameterOfBinaryTree(root) shouldBe 4
    }

    @Test
    fun `left-skewed tree`() {
        // Tree: 1 - 2 - 3 - 4 (all left children)
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(4)
                }
            }
        }
        Solution().diameterOfBinaryTree(root) shouldBe 3
    }

    @Test
    fun `balanced tree`() {
        // Tree:       1
        //            / \
        //           2   3
        //          / \ / \
        //         4  5 6  7
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(4)
                right = TreeNode(5)
            }
            right = TreeNode(3).apply {
                left = TreeNode(6)
                right = TreeNode(7)
            }
        }
        Solution().diameterOfBinaryTree(root) shouldBe 4
    }

    class Solution {
        fun diameterOfBinaryTree(root: TreeNode?): Int {
            if (root == null) return 0
            var maxDiameter = 0
            val nodes = postOrderTraversal(root)
            val heightMap = mutableMapOf<TreeNode, Int>()
            for (node in nodes) {
                val leftHeight = node.left?.let { heightMap[it] ?: 0 } ?: 0
                val rightHeight = node.right?.let { heightMap[it] ?: 0 } ?: 0
                heightMap[node] = maxOf(leftHeight, rightHeight) + 1
                val diameter = leftHeight + rightHeight
                maxDiameter = maxOf(maxDiameter, diameter)
            }
            return maxDiameter
        }

        private fun postOrderTraversal(root: TreeNode): List<TreeNode> {
            val stack1 = ArrayDeque<TreeNode>().also { it.addLast(root) }
            val stack2 = ArrayDeque<TreeNode>()
            val result = mutableListOf<TreeNode>()

            while (stack1.isNotEmpty()) {
                val node = stack1.removeLast()
                stack2.addLast(node)

                node.left?.let { stack1.addLast(it) }
                node.right?.let { stack1.addLast(it) }
            }

            while (stack2.isNotEmpty()) {
                stack2.removeLast().let { result.add(it) }
            }

            return result
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        override fun toString(): String {
            return "TreeNode(`val`=$`val`)"
        }
    }
}
