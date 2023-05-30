package com.sample.tom.ds.tree

class BinarySearchTree<T : Comparable<T>> {
    private var root: BinaryNode<T>? = null

    fun insert(value: T) {
        root = insertInternal(root, value)
    }

    private fun insertInternal(node: BinaryNode<T>?, value: T): BinaryNode<T> {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insertInternal(node.leftChild, value)
        } else {
            node.rightChild = insertInternal(node.rightChild, value)
        }
        return node
    }


    override fun toString(): String {
        return root?.let { "$it" } ?: "empty"
    }
}
