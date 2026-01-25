package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0226_InvertBinaryTree` {
    @Test
    fun `empty tree returns empty tree`() {
        // Input: root = []
        // Output: []
        // Explanation: Empty tree remains empty after inversion
        val root: TreeNode? = null
        val result = Solution().invertTree(root)
        result shouldBe null
    }

    @Test
    fun `single node tree remains unchanged`() {
        // Input: root = [1]
        // Output: [1]
        // Explanation: A tree with only root has no children to swap
        val root = TreeNode(1)
        val result = Solution().invertTree(root)

        result?.`val` shouldBe 1
        result?.left shouldBe null
        result?.right shouldBe null
    }

    @Test
    fun `example 1 - complete binary tree with seven nodes`() {
        // Input: root = [4,2,7,1,3,6,9]
        // Output: [4,7,2,9,6,3,1]
        // Explanation: The tree is inverted at every level
        val root =
            buildTree(4) {
                left =
                    buildTree(2) {
                        left = buildTree(1)
                        right = buildTree(3)
                    }
                right =
                    buildTree(7) {
                        left = buildTree(6)
                        right = buildTree(9)
                    }
            }

        val expected =
            buildTree(4) {
                left =
                    buildTree(7) {
                        left = buildTree(9)
                        right = buildTree(6)
                    }
                right =
                    buildTree(2) {
                        left = buildTree(3)
                        right = buildTree(1)
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `example 2 - small tree with three nodes`() {
        // Input: root = [2,1,3]
        // Output: [2,3,1]
        // Explanation: Left and right children are swapped
        val root =
            buildTree(2) {
                left = buildTree(1)
                right = buildTree(3)
            }

        val expected =
            buildTree(2) {
                left = buildTree(3)
                right = buildTree(1)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with only left children becomes tree with only right children`() {
        // Input: root = [1,2,3,4]
        // Output: [1,null,2,null,3,null,4]
        // Explanation: Left-skewed tree becomes right-skewed
        val root =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left =
                            buildTree(3) {
                                left = buildTree(4)
                            }
                    }
            }

        val expected =
            buildTree(1) {
                right =
                    buildTree(2) {
                        right =
                            buildTree(3) {
                                right = buildTree(4)
                            }
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with only right children becomes tree with only left children`() {
        // Input: root = [1,null,2,null,3,null,4]
        // Output: [1,2,null,3,null,4]
        // Explanation: Right-skewed tree becomes left-skewed
        val root =
            buildTree(1) {
                right =
                    buildTree(2) {
                        right =
                            buildTree(3) {
                                right = buildTree(4)
                            }
                    }
            }

        val expected =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left =
                            buildTree(3) {
                                left = buildTree(4)
                            }
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with two levels only left child`() {
        // Input: root = [1,2]
        // Output: [1,null,2]
        // Explanation: Left child moves to right
        val root =
            buildTree(1) {
                left = buildTree(2)
            }

        val expected =
            buildTree(1) {
                right = buildTree(2)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with two levels only right child`() {
        // Input: root = [1,null,2]
        // Output: [1,2]
        // Explanation: Right child moves to left
        val root =
            buildTree(1) {
                right = buildTree(2)
            }

        val expected =
            buildTree(1) {
                left = buildTree(2)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with negative values`() {
        // Input: root = [-1,-2,-3]
        // Output: [-1,-3,-2]
        // Explanation: Negative values are handled the same way
        val root =
            buildTree(-1) {
                left = buildTree(-2)
                right = buildTree(-3)
            }

        val expected =
            buildTree(-1) {
                left = buildTree(-3)
                right = buildTree(-2)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with mixed positive and negative values`() {
        // Input: root = [0,-5,5,-10,-3,3,10]
        // Output: [0,5,-5,10,3,-3,-10]
        val root =
            buildTree(0) {
                left =
                    buildTree(-5) {
                        left = buildTree(-10)
                        right = buildTree(-3)
                    }
                right =
                    buildTree(5) {
                        left = buildTree(3)
                        right = buildTree(10)
                    }
            }

        val expected =
            buildTree(0) {
                left =
                    buildTree(5) {
                        left = buildTree(10)
                        right = buildTree(3)
                    }
                right =
                    buildTree(-5) {
                        left = buildTree(-3)
                        right = buildTree(-10)
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with all same values`() {
        // Input: root = [5,5,5,5,5]
        // Output: [5,5,5,5,5]
        // Explanation: Tree structure changes but values are identical
        val root =
            buildTree(5) {
                left =
                    buildTree(5) {
                        left = buildTree(5)
                    }
                right =
                    buildTree(5) {
                        right = buildTree(5)
                    }
            }

        val expected =
            buildTree(5) {
                left =
                    buildTree(5) {
                        left = buildTree(5)
                    }
                right =
                    buildTree(5) {
                        right = buildTree(5)
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with asymmetric structure left heavy`() {
        // Input: root = [1,2,3,4,5]
        // Output: [1,3,2,null,null,5,4]
        // Explanation: Asymmetric tree gets mirrored
        val root =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left = buildTree(4)
                        right = buildTree(5)
                    }
                right = buildTree(3)
            }

        val expected =
            buildTree(1) {
                left = buildTree(3)
                right =
                    buildTree(2) {
                        left = buildTree(5)
                        right = buildTree(4)
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with asymmetric structure right heavy`() {
        // Input: root = [1,2,3,null,null,4,5]
        // Output: [1,3,2,5,4]
        // Explanation: Right-heavy tree gets mirrored
        val root =
            buildTree(1) {
                left = buildTree(2)
                right =
                    buildTree(3) {
                        left = buildTree(4)
                        right = buildTree(5)
                    }
            }

        val expected =
            buildTree(1) {
                left =
                    buildTree(3) {
                        left = buildTree(5)
                        right = buildTree(4)
                    }
                right = buildTree(2)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `large complete binary tree with 15 nodes`() {
        // Input: root = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
        // Explanation: Large tree to test performance and correctness
        val root =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left =
                            buildTree(4) {
                                left = buildTree(8)
                                right = buildTree(9)
                            }
                        right =
                            buildTree(5) {
                                left = buildTree(10)
                                right = buildTree(11)
                            }
                    }
                right =
                    buildTree(3) {
                        left =
                            buildTree(6) {
                                left = buildTree(12)
                                right = buildTree(13)
                            }
                        right =
                            buildTree(7) {
                                left = buildTree(14)
                                right = buildTree(15)
                            }
                    }
            }

        val expected =
            buildTree(1) {
                left =
                    buildTree(3) {
                        left =
                            buildTree(7) {
                                left = buildTree(15)
                                right = buildTree(14)
                            }
                        right =
                            buildTree(6) {
                                left = buildTree(13)
                                right = buildTree(12)
                            }
                    }
                right =
                    buildTree(2) {
                        left =
                            buildTree(5) {
                                left = buildTree(11)
                                right = buildTree(10)
                            }
                        right =
                            buildTree(4) {
                                left = buildTree(9)
                                right = buildTree(8)
                            }
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `tree with boundary values`() {
        // Input: root = [0,100,-100]
        // Output: [0,-100,100]
        // Explanation: Testing boundary values from constraints
        val root =
            buildTree(0) {
                left = buildTree(100)
                right = buildTree(-100)
            }

        val expected =
            buildTree(0) {
                left = buildTree(-100)
                right = buildTree(100)
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    @Test
    fun `double inversion returns original tree`() {
        // Input: root = [1,2,3,4,5,6,7]
        // Explanation: Inverting twice should return the original structure
        val root =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left = buildTree(4)
                        right = buildTree(5)
                    }
                right =
                    buildTree(3) {
                        left = buildTree(6)
                        right = buildTree(7)
                    }
            }

        val expected =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left = buildTree(4)
                        right = buildTree(5)
                    }
                right =
                    buildTree(3) {
                        left = buildTree(6)
                        right = buildTree(7)
                    }
            }

        val solution = Solution()
        val firstInversion = solution.invertTree(root)
        val result = solution.invertTree(firstInversion)

        result shouldBe expected
    }

    @Test
    fun `tree with gap in middle level`() {
        // Input: root = [1,2,3,4,null,null,5]
        // Output: [1,3,2,5,null,null,4]
        // Explanation: Missing nodes should be handled correctly
        val root =
            buildTree(1) {
                left =
                    buildTree(2) {
                        left = buildTree(4)
                    }
                right =
                    buildTree(3) {
                        right = buildTree(5)
                    }
            }

        val expected =
            buildTree(1) {
                left =
                    buildTree(3) {
                        left = buildTree(5)
                    }
                right =
                    buildTree(2) {
                        right = buildTree(4)
                    }
            }

        val result = Solution().invertTree(root)
        result shouldBe expected
    }

    // Helper function to build trees with DSL-like syntax
    private fun buildTree(value: Int, builder: TreeNode.() -> Unit = {}): TreeNode = TreeNode(value).apply(builder)

    class Solution {
        fun invertTree(root: TreeNode?): TreeNode? {
            if (root == null) return null
            val stack = ArrayDeque<TreeNode>()
            stack.addLast(root)

            while (stack.isNotEmpty()) {
                val node = stack.removeLast()
                val left = node.left
                val right = node.right
                node.left = right
                node.right = left
                right?.let { stack.addLast(right) }
                left?.let { stack.addLast(left) }
            }

            return root
        }

        // Recursive solution - typically faster due to less overhead
        fun invertTreeRecursive(root: TreeNode?): TreeNode? {
            if (root == null) return null

            // Swap children
            val temp = root.left
            root.left = root.right
            root.right = temp

            // Recursively invert subtrees
            invertTreeRecursive(root.left)
            invertTreeRecursive(root.right)

            return root
        }
    }

    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     */
    class TreeNode(var `val`: Int) {
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
}
