package com.sample.tom.ds.tree

class BinarySearchTree<T : Comparable<T>> {
    private var root: BinaryNode<T>? = null

    fun insert(value: T) {
        var current = root
        if (current == null) {
            root = BinaryNode(value)
            return
        }
        while (current != null) {
            if (value < current.value) {
                if (current.leftChild != null) {
                    current = current.leftChild
                } else {
                    current.leftChild = BinaryNode(value)
                    return
                }
            } else {
                if (current.rightChild != null) {
                    current = current.rightChild
                } else {
                    current.rightChild = BinaryNode(value)
                    return
                }
            }
        }
    }

    fun insertRecursive(value: T) {
        root = insertInternal(root, value)
    }

    fun contains(value: T): Boolean {
        val root = root ?: return false
        var found = false
        root.preOrder { node ->
            if (node == value) {
                found = true
            }
        }
        return found
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
