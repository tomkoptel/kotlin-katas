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
        var current = root
        while (current != null) {
            if (current.value == value) {
                return true
            }

            current = if (value < current.value) {
                current.leftChild
            } else {
                current.rightChild
            }
        }
        return false
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

    fun removeRecursive(value: T) {
        root = removeRecursive(root, value)
    }

    private fun removeRecursive(node: BinaryNode<T>?, value: T): BinaryNode<T>? {
        node ?: return null

        when {
            value == node.value -> {
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = removeRecursive(node.rightChild, node.value)
            }

            value < node.value -> {
                node.leftChild = removeRecursive(node.leftChild, value)
            }

            value > node.value -> {
                node.rightChild = removeRecursive(node.rightChild, value)
            }
        }

        return node
    }

    override fun toString(): String {
        return root?.let { "$it" } ?: "empty"
    }
}
