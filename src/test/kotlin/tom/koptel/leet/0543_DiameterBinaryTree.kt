package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

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
            var maxDiameter = 0
            val dfs = DeepRecursiveFunction<TreeNode?, Int> { tree ->
                if (tree == null) {
                    0
                } else {
                    val right = tree.right
                    val left = tree.left
                    val rightHeight = callRecursive(right)
                    val leftHeight = callRecursive(left)
                    val diameter = rightHeight + leftHeight
                    maxDiameter = maxOf(maxDiameter, diameter)
                    maxOf(rightHeight, leftHeight) + 1
                }
            }
            dfs(root)
            return maxDiameter
        }

        fun diameterOfBinaryTreeImperative(root: TreeNode?): Int {
            if (root == null) return 0
            var maxDiameter = 0
            val heightMap = mutableMapOf<TreeNode, Int>()
            val stack = ArrayDeque<TreeNode>().also { it.addLast(root) }

            var lastProcessed: TreeNode? = null
            while (stack.isNotEmpty()) {
                val node = stack.last()
                val leftNode = node.left
                val rightNode = node.right
                val lastChild = rightNode ?: leftNode

                if ((lastChild == null) || (lastProcessed == lastChild)) {
                    val leftHeight = leftNode?.let { heightMap[it] ?: 0 } ?: 0
                    val rightHeight = rightNode?.let { heightMap[it] ?: 0 } ?: 0
                    heightMap[node] = maxOf(leftHeight, rightHeight) + 1

                    val diameter = leftHeight + rightHeight
                    maxDiameter = maxOf(maxDiameter, diameter)
                    lastProcessed = stack.removeLast()
                } else {
                    rightNode?.let { stack.addLast(it) }
                    leftNode?.let { stack.addLast(it) }
                }
            }

            return maxDiameter
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        override fun toString(): String = "TreeNode(`val`=$`val`)"
    }
}
