package com.sample.tom.ds.tree

import java.lang.Integer.max

class AVLTree<T : Comparable<T>> {
    private var root: AVLNode<T>? = null

    fun insert(value: T) {
        root = insertInternal(root, value)
    }

    private fun insertInternal(node: AVLNode<T>?, value: T): AVLNode<T>? {
        node ?: return AVLNode(value)
        if (value < node.value) {
            node.leftChild = insertInternal(node.leftChild, value)
        } else {
            node.rightChild = insertInternal(node.rightChild, value)
        }
        val balanceNode = balanced(node)
        balanceNode?.height = max(balanceNode?.leftHeight ?: 0, balanceNode?.rightHeight ?: 0) + 1
        return balanceNode
    }

    private fun balanced(node: AVLNode<T>): AVLNode<T>? {
        return when (node.balanceFactor) {
            2 -> {
                if (node.leftChild?.balanceFactor == -1) {
                    leftRightRotate(node)
                } else {
                    rightRotate(node)
                }
            }

            -2 -> {
                if (node.rightChild?.balanceFactor == 1) {
                    rightLeftRotate(node)
                } else {
                    leftRotate(node)
                }
            }

            else -> node
        }
    }

    private fun leftRightRotate(node: AVLNode<T>): AVLNode<T>? {
        val leftChild = node.leftChild ?: return node
        node.leftChild = leftRotate(leftChild)
        return rightRotate(node)
    }

    private fun rightLeftRotate(node: AVLNode<T>): AVLNode<T>? {
        val rightChild = node.rightChild ?: return node
        node.rightChild = rightRotate(rightChild)
        return leftRotate(node)
    }

    private fun leftRotate(node: AVLNode<T>): AVLNode<T>? {
        val pivot = node.rightChild ?: return null
        node.rightChild = pivot.leftChild
        pivot.leftChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    private fun rightRotate(node: AVLNode<T>): AVLNode<T>? {
        val pivot = node.leftChild ?: return null
        node.leftChild = pivot.rightChild
        pivot.rightChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    override fun toString(): String {
        return root?.let { "$it" } ?: "empty"
    }
}
