package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0104_MaxDepthBinaryTree` {
    @Test
    fun `example 1`() {
        // Tree:       3
        //            / \
        //           9   20
        //               / \
        //              15  7
        val root = TreeNode(3).apply {
            left = TreeNode(9)
            right = TreeNode(20).apply {
                left = TreeNode(15)
                right = TreeNode(7)
            }
        }
        Solution().maxDepth(root) shouldBe 3
    }

    @Test
    fun `example 2 - two nodes`() {
        // Tree: 1
        //        \
        //         2
        val root = TreeNode(1).apply {
            right = TreeNode(2)
        }
        Solution().maxDepth(root) shouldBe 2
    }

    @Test
    fun `null root`() {
        Solution().maxDepth(null) shouldBe 0
    }

    @Test
    fun `single node`() {
        Solution().maxDepth(TreeNode(1)) shouldBe 1
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
        Solution().maxDepth(root) shouldBe 4
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
        Solution().maxDepth(root) shouldBe 3
    }

    @Test
    fun `unbalanced - right side deeper`() {
        // Tree:   1
        //          \
        //           2
        //            \
        //             3
        //            /
        //           4
        val root = TreeNode(1).apply {
            right = TreeNode(2).apply {
                right = TreeNode(3).apply {
                    left = TreeNode(4)
                }
            }
        }
        Solution().maxDepth(root) shouldBe 4
    }

    private class Solution {
        fun maxDepth(root: TreeNode?): Int {
            val dfs = DeepRecursiveFunction<TreeNode?, Int> { tree ->
                if (tree == null) {
                    0
                } else {
                    val rightHeight = callRecursive(tree.right)
                    val leftHeight = callRecursive(tree.left)
                    maxOf(rightHeight, leftHeight) + 1
                }
            }
            return dfs(root)
        }

        fun maxDepthNonRecursive(root: TreeNode?): Int {
            if (root == null) return 0
            val heights = mutableMapOf<TreeNode, Int>()
            val stack = ArrayDeque<TreeNode>().also { it.addLast(root) }
            var visited: TreeNode? = null

            while (stack.isNotEmpty()) {
                val parent = stack.last()
                val left = parent.left
                val right = parent.right
                val child = right ?: left
                if ((child == null) || (visited == child)) {
                    val rightHeight = heights heightOf right
                    val leftHeight = heights heightOf left
                    val height = maxOf(rightHeight, leftHeight) + 1
                    heights[parent] = height
                    visited = stack.removeLast()
                } else {
                    right?.let { stack.addLast(it) }
                    left?.let { stack.addLast(it) }
                }
            }

            return heights[root] ?: 0
        }

        private infix fun MutableMap<TreeNode, Int>.heightOf(node: TreeNode?): Int = node?.let { this[it] } ?: 0
    }

    private class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}
