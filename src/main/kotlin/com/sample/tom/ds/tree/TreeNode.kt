package com.sample.tom.ds.tree

typealias Visitor<T> = (TreeNode<T>) -> Unit

class TreeNode<T>(val value: T) {
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
    fun depthFirst(visitor: Visitor<T>) {
        val stack = mutableListOf(this)
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            visitor(node)
            node.children.reversed().forEach {
                stack.add(it)
            }
        }
    }

    override fun toString(): String {
        return "TreeNode(value=$value, children=$children)"
    }
}
