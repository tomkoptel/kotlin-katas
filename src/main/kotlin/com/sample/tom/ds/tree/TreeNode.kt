package com.sample.tom.ds.tree

typealias TreeNodeVisitor<T> = (TreeNode<T>) -> Unit

class TreeNode<T>(
    val value: T,
) {
    private val children = mutableListOf<TreeNode<T>>()

    fun add(child: TreeNode<T>) = children.add(child)

    /**
     * Beverage
     * /       \
     * Hot      Cold
     * /    \      \
     * Tea   Choco   Shake
     *
     * We create stack with a root node 'Beverage'.
     * Then we take children of the root node 'Hot' and 'Cold'.
     * Then we reverse the children 'Cold' > 'Hot' and add them to the stack. So that on top of the stack is 'Hot'.
     * Then we pop the 'Hot' node from the stack and visit it.
     * Then we take children of the 'Hot' node 'Tea' and 'Choco'.
     * Then we reverse the children 'Choco' > 'Tea' and add them to the stack.
     * So that on top of the stack is 'Tea'.
     * Then we pop the 'Tea' node from the stack and visit it.
     * Then we take children of the 'Tea' node. There are no children continue.
     * Then we pop the 'Choco' node from the stack and visit it. There are no children continue.
     * Then we pop the 'Cold' node from the stack and visit it.
     * Then we take children of the 'Cold' which is 'Shake' and add to stack.
     * Then we pop 'Shake' and visit.
     *
     * So the order of visit is 'Beverage' > 'Hot' > 'Tea' > 'Choco' > 'Cold' > 'Shake'.
     */
    fun depthFirst(visitor: TreeNodeVisitor<T>) {
        val stack = mutableListOf(this)
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            visitor(node)
            node.children.reversed().forEach {
                stack.add(it)
            }
        }
    }

    /**
     * Visit 'Beverages'.
     * Add children of 'Beverages'. The queue is 'Hot' > 'Cold'.
     * Dequeue 'Hot' and visit it. Then add its children to queue. The queue 'cold' > 'tea' > 'coffee' > 'cocoa'.
     * Dequeue 'Cold' and visit it. Then add its children to queue. The queue 'tea' > 'coffee' > 'cocoa' > 'soda' > 'milk'.
     * Dequeue 'Tea' and visit it. Then add its children to queue. The queue 'coffee' > 'cocoa' > 'soda' > 'milk' > 'black' > 'green' > 'chai'.
     * Dequeue 'Coffee' and visit it. Then add its children to queue. No children. The queue 'cocoa' > 'soda' > 'milk' > 'black' > 'green' > 'chai'.
     * Dequeue 'Cocoa' and visit it. Then add its children to queue. No children. The queue 'soda' > 'milk' > 'black' > 'green' > 'chai'.
     * Dequeue 'Soda' and visit it. Then add its children to queue. The queue 'milk' > 'black' > 'green' > 'chai' > 'ginger ale' > 'bitter lemon'.
     * Dequeue 'Milk' and visit it. Then add its children to queue. No children. The queue 'black' > 'green' > 'chai' > 'ginger ale' > 'bitter lemon'.
     * Dequeue 'Black' and visit it. Then add its children to queue. No children. The queue 'green' > 'chai' > 'ginger ale' > 'bitter lemon'.
     * Dequeue 'Green' and visit it. Then add its children to queue. No children. The queue 'chai' > 'ginger ale' > 'bitter lemon'.
     * Dequeue 'Chai' and visit it. Then add its children to queue. No children. The queue 'ginger ale' > 'bitter lemon'.
     * Dequeue 'Ginger Ale' and visit it. Then add its children to queue. No children. The queue 'bitter lemon'.
     * Dequeue 'Bitter Lemon' and visit it. Then add its children to queue. No children. The queue is empty.
     */
    fun levelOrder(visitor: TreeNodeVisitor<T>) {
        val queue = mutableListOf(this)
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            visitor(node)
            node.children.forEach {
                queue.add(it)
            }
        }
    }

    /**
     * If there are multiple matches, the last match wins.
     */
    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null

        levelOrder {
            if (it.value == value) {
                result = it
            }
        }

        return result
    }

    fun printEachLevel() {
        val queue = arrayListOf(this)
        var levelCount: Int

        while (queue.isNotEmpty()) {
            levelCount = queue.size

            while (levelCount > 0) {
                val node = queue.removeAt(0)
                print("${node.value} ")
                node.children.forEach {
                    queue.add(it)
                }
                levelCount--
            }
            println()
        }
    }

    override fun toString(): String = "TreeNode(value=$value, children=$children)"
}
