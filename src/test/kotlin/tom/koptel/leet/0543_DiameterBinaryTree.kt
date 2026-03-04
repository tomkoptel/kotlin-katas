package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

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
            val stack = ArrayDeque<Pair<TreeNode, Int>>()
            root.left?.let { stack.add(it to 1) }
            root.right?.let { stack.add(it to 1) }
            while (stack.isNotEmpty()) {
                val leftPath = stack.removeLastOrNull()
                val rightPath = stack.removeLastOrNull()
                val left = leftPath?.first
                val leftDepth = leftPath?.second ?: 0
                val right = rightPath?.first
                val rightDepth = rightPath?.second ?: 0
                val diameter = leftDepth + rightDepth
                maxDiameter = maxOf(diameter, maxDiameter)
                left?.left?.let { stack.addLast(it to leftDepth + 1) }
                left?.right?.let { stack.addLast(it to rightDepth + 1) }
                right?.left?.let { stack.addLast(it to leftDepth + 1) }
                right?.right?.let { stack.addLast(it to rightDepth + 1) }
            }
            return maxDiameter
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}
