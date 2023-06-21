package com.sample.tom.ds.tree

class AVLTree<T : Comparable<T>> {
    private var root: AVLNode<T>? = null

    fun insert(value: T) {
        root = internalInsert(root, value)
    }

    private fun internalInsert(node: AVLNode<T>?,value: T): AVLNode<T>? {
        node ?: return AVLNode(value)
        if (value < node.value) {
            node.leftChild = internalInsert(node.leftChild, value)
        } else {
            node.rightChild = internalInsert(node.rightChild, value)
        }
        // why we pass the node itself?
        val balancedNode = balance(node)
        // does it make sense
        balancedNode?.recomputeHeight()
        return balancedNode
    }

    // shouldn't it be recursive
    private fun balance(node: AVLNode<T>): AVLNode<T>? {
        return when (node.balanceFactor) {
            2 -> { // left heavy
                // is it a right or left we need check
                val balanceFactor = node.leftChild?.balanceFactor ?: 0
                if (balanceFactor == -1) {
                    // why do we need to reassign it?
                    // because here we have a reference to the node which height is at minimum 2 and we need fix the skew of left branch
                    // we are rotating the left child that has a height of 1 but skew to the left and change it to right
                    node.leftChild = leftRotate(node.leftChild)
                }
                rightRotate(node)
            }

            -2 -> {
                val balanceFactor = node.rightChild?.balanceFactor ?: 0
                if (balanceFactor == 1) {
                    node.rightChild = rightRotate(node.rightChild)
                }
                leftRotate(node)
            }

            else -> node
        }
    }

    private fun leftRotate(node: AVLNode<T>?): AVLNode<T>? {
        val pivot = node?.rightChild
        node?.rightChild = pivot?.leftChild
        pivot?.leftChild = node
        node?.recomputeHeight()
        pivot?.recomputeHeight()
        return pivot
    }

    private fun rightRotate(node: AVLNode<T>?): AVLNode<T>? {
        val pivot = node?.leftChild
        node?.leftChild = pivot?.rightChild
        pivot?.rightChild = node
        node?.recomputeHeight()
        pivot?.recomputeHeight()
        return pivot
    }

    fun remove(value: T) {
        root = removeRecursive(root, value)
    }

    private fun removeRecursive(node: AVLNode<T>?, value: T): AVLNode<T>? {
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

        val balancedNode = balance(node)
        balancedNode?.recomputeHeight()
        return balancedNode
    }

    override fun toString(): String {
        return root?.let { "$it" } ?: "empty"
    }
}
