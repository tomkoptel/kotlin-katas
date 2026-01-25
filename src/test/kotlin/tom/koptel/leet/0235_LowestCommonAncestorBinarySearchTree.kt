package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.Queue

class `0235_LowestCommonAncestorBinarySearchTree` {
    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     */
    class TreeNode(var `val`: Int = 0) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        /**
         * Pretty print the tree structure for debugging
         * Example output:
         *     4
         *    / \
         *   2   7
         *  / \ / \
         * 1  3 6  9
         */
        override fun toString(): String {
            val lines = mutableListOf<String>()
            buildTreeString(this, "", "", lines)
            return lines.joinToString("\n")
        }

        private fun buildTreeString(node: TreeNode?, prefix: String, childrenPrefix: String, lines: MutableList<String>) {
            if (node == null) return

            lines.add(prefix + node.`val`)

            val children =
                listOfNotNull(
                    node.left?.let { "L" to it },
                    node.right?.let { "R" to it }
                )

            children.forEachIndexed { index, (side, child) ->
                val isLast = index == children.lastIndex
                val connector = if (side == "L") "├─L: " else "└─R: "
                val newPrefix = childrenPrefix + connector
                val newChildrenPrefix = childrenPrefix + if (isLast) "    " else "│   "
                buildTreeString(child, newPrefix, newChildrenPrefix, lines)
            }
        }

        /**
         * Compare two trees for structural and value equality
         */
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TreeNode) return false
            if (this.`val` != other.`val`) return false
            if (this.left != other.left) return false
            if (this.right != other.right) return false
            return true
        }

        /**
         * Generate hash code based on tree structure and values
         */
        override fun hashCode(): Int {
            var result = `val`
            result = 31 * result + (left?.hashCode() ?: 0)
            result = 31 * result + (right?.hashCode() ?: 0)
            return result
        }
    }

    // ==================== Test Cases ====================

    /**
     * Example 1: LCA of nodes in different subtrees
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \ / \
     *    0  4 7  9
     *      / \
     *     3   5
     *
     * p = 2, q = 8
     * Expected: 6 (root is the LCA since p is in left subtree and q is in right subtree)
     */
    @Test
    fun `test example 1 - LCA of nodes in different subtrees`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right =
                            TreeNode(4).apply {
                                left = TreeNode(3)
                                right = TreeNode(5)
                            }
                    }
                right =
                    TreeNode(8).apply {
                        left = TreeNode(7)
                        right = TreeNode(9)
                    }
            }

        val p = findNode(root, 2)
        val q = findNode(root, 8)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 6
    }

    /**
     * Example 2: LCA where one node is ancestor of the other
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \ / \
     *    0  4 7  9
     *      / \
     *     3   5
     *
     * p = 2, q = 4
     * Expected: 2 (a node can be a descendant of itself)
     */
    @Test
    fun `test example 2 - node is ancestor of itself`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right =
                            TreeNode(4).apply {
                                left = TreeNode(3)
                                right = TreeNode(5)
                            }
                    }
                right =
                    TreeNode(8).apply {
                        left = TreeNode(7)
                        right = TreeNode(9)
                    }
            }

        val p = findNode(root, 2)
        val q = findNode(root, 4)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 2
    }

    /**
     * Example 3: Minimal tree with two nodes
     * Tree structure:
     *      2
     *     /
     *    1
     *
     * p = 2, q = 1
     * Expected: 2
     */
    @Test
    fun `test example 3 - minimal tree`() {
        val root =
            TreeNode(2).apply {
                left = TreeNode(1)
            }

        val p = findNode(root, 2)
        val q = findNode(root, 1)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 2
    }

    /**
     * Test: Both nodes in left subtree
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \
     *    0  4
     *      / \
     *     3   5
     *
     * p = 0, q = 4
     * Expected: 2
     */
    @Test
    fun `test both nodes in left subtree`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right =
                            TreeNode(4).apply {
                                left = TreeNode(3)
                                right = TreeNode(5)
                            }
                    }
                right = TreeNode(8)
            }

        val p = findNode(root, 0)
        val q = findNode(root, 4)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 2
    }

    /**
     * Test: Both nodes in right subtree
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *         / \
     *        7   9
     *
     * p = 7, q = 9
     * Expected: 8
     */
    @Test
    fun `test both nodes in right subtree`() {
        val root =
            TreeNode(6).apply {
                left = TreeNode(2)
                right =
                    TreeNode(8).apply {
                        left = TreeNode(7)
                        right = TreeNode(9)
                    }
            }

        val p = findNode(root, 7)
        val q = findNode(root, 9)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 8
    }

    /**
     * Test: Nodes at different depths
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \
     *    0  4
     *      / \
     *     3   5
     *
     * p = 3, q = 2
     * Expected: 2 (parent is ancestor of deeper child)
     */
    @Test
    fun `test nodes at different depths`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right =
                            TreeNode(4).apply {
                                left = TreeNode(3)
                                right = TreeNode(5)
                            }
                    }
                right = TreeNode(8)
            }

        val p = findNode(root, 3)
        val q = findNode(root, 2)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 2
    }

    /**
     * Test: Deep nested nodes in same branch
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \
     *    0  4
     *      / \
     *     3   5
     *
     * p = 3, q = 5
     * Expected: 4
     */
    @Test
    fun `test deep nested nodes in same branch`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right =
                            TreeNode(4).apply {
                                left = TreeNode(3)
                                right = TreeNode(5)
                            }
                    }
                right = TreeNode(8)
            }

        val p = findNode(root, 3)
        val q = findNode(root, 5)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 4
    }

    /**
     * Test: Root is one of the target nodes
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *
     * p = 6, q = 8
     * Expected: 6
     */
    @Test
    fun `test root is one of the target nodes`() {
        val root =
            TreeNode(6).apply {
                left = TreeNode(2)
                right = TreeNode(8)
            }

        val p = findNode(root, 6)
        val q = findNode(root, 8)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 6
    }

    /**
     * Test: Leaf nodes
     * Tree structure:
     *        6
     *       / \
     *      2   8
     *     / \
     *    0  4
     *
     * p = 0, q = 4
     * Expected: 2
     */
    @Test
    fun `test leaf nodes`() {
        val root =
            TreeNode(6).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(0)
                        right = TreeNode(4)
                    }
                right = TreeNode(8)
            }

        val p = findNode(root, 0)
        val q = findNode(root, 4)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 2
    }

    /**
     * Test: Large BST with negative numbers
     * Tree structure:
     *          0
     *        /   \
     *      -5     5
     *     /  \   / \
     *   -8  -3  3  8
     *
     * p = -8, q = 3
     * Expected: 0
     */
    @Test
    fun `test BST with negative numbers`() {
        val root =
            TreeNode(0).apply {
                left =
                    TreeNode(-5).apply {
                        left = TreeNode(-8)
                        right = TreeNode(-3)
                    }
                right =
                    TreeNode(5).apply {
                        left = TreeNode(3)
                        right = TreeNode(8)
                    }
            }

        val p = findNode(root, -8)
        val q = findNode(root, 3)
        val result = Solution().lowestCommonAncestor(root, p, q)

        result?.`val` shouldBe 0
    }

    /**
     * Helper function to find a node by value in the tree
     */
    private fun findNode(root: TreeNode?, value: Int): TreeNode? {
        if (root == null) return null
        if (root.`val` == value) return root
        return findNode(root.left, value) ?: findNode(root.right, value)
    }

    class Solution {
        /**
         * Iterative BST-Optimized Solution (even more space efficient!)
         *
         * Time Complexity: O(h)
         * Space Complexity: O(1) - no recursion stack!
         */
        fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            if (root == null || p == null || q == null) return null

            var current = root
            val pVal = p.`val`
            val qVal = q.`val`

            while (current != null) {
                val currentVal = current.`val`

                when {
                    // Both nodes are smaller - go left
                    pVal < currentVal && qVal < currentVal -> current = current.left

                    // Both nodes are larger - go right
                    pVal > currentVal && qVal > currentVal -> current = current.right

                    // Found the split point
                    else -> return current
                }
            }

            return null
        }

        /**
         * Find the lowest common ancestor (LCA) of two nodes in a BST.
         *
         * @param root The root of the binary search tree
         * @param p First target node
         * @param q Second target node
         * @return The lowest common ancestor node
         */
        fun lowestCommonAncestor3(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            if (root == null || root == p || root == q) {
                return root
            }

            val leftAncestor = lowestCommonAncestor3(root.left, p, q)
            val rightAncestor = lowestCommonAncestor3(root.right, p, q)

            return when {
                leftAncestor != null && rightAncestor != null -> root
                leftAncestor != null -> leftAncestor
                else -> rightAncestor
            }
        }

        fun lowestCommonAncestor2(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            if (root == null) return null
            if (p == null) return null
            if (q == null) return null
            val stack: Queue<TreeNode> = LinkedList()
            stack.add(root)

            val parents = mutableMapOf<TreeNode, TreeNode>()
            while (stack.isNotEmpty()) {
                val node = stack.poll()
                node.left?.let { left ->
                    if (!parents.containsKey(left)) {
                        parents[left] = node
                        stack.add(left)
                    }
                }
                node.right?.let { right ->
                    if (!parents.containsKey(right)) {
                        parents[right] = node
                        stack.add(right)
                    }
                }
            }

            val ancestors = mutableSetOf<TreeNode>()
            var temp = p
            while (temp != null) {
                ancestors.add(temp)
                temp = parents[temp]
            }

            temp = q
            while (temp != null) {
                if (temp in ancestors) return temp
                temp = parents[temp]
            }

            return null
        }
    }
}
