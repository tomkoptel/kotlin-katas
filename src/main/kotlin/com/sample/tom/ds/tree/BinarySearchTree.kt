package com.sample.tom.ds.tree

import kotlin.collections.ArrayDeque

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

            current =
                if (value < current.value) {
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

    fun remove(value: T) {
        if (root == null) return

        var current = root
        var parent: BinaryNode<T>? = null
        var isLeftChild = false

        while (current != null) {
            val comparison = value.compareTo(current.value)
            if (comparison == 0) {
                removeNode(current, parent, isLeftChild)
                return
            }

            parent = current
            if (comparison < 0) {
                current = current.leftChild
                isLeftChild = true
            } else {
                current = current.rightChild
                isLeftChild = false
            }
        }
    }

    private fun removeNode(node: BinaryNode<T>, parent: BinaryNode<T>?, isLeftChild: Boolean) {
        if (node.leftChild == null && node.rightChild == null) {
            // Case 1: Node is a leaf node
            if (parent == null) {
                // Node is the root
                root = null
            } else if (isLeftChild) {
                parent.leftChild = null
            } else {
                parent.rightChild = null
            }
        } else if (node.leftChild == null) {
            // Case 2: Node has only right child
            if (parent == null) {
                // Node is the root
                root = node.rightChild
            } else if (isLeftChild) {
                parent.leftChild = node.rightChild
            } else {
                parent.rightChild = node.rightChild
            }
        } else if (node.rightChild == null) {
            // Case 3: Node has only left child
            if (parent == null) {
                // Node is the root
                root = node.leftChild
            } else if (isLeftChild) {
                parent.leftChild = node.leftChild
            } else {
                parent.rightChild = node.leftChild
            }
        } else {
            // Case 4: Node has both left and right children
            val successor = getSuccessor(node)
            if (parent == null) {
                // Node is the root
                root = successor
            } else if (isLeftChild) {
                parent.leftChild = successor
            } else {
                parent.rightChild = successor
            }

            successor.leftChild = node.leftChild
        }
    }

    /**
     * In the removeNode function, we first obtain the successor of the node to be removed using the getSuccessor function.
     * The successor is the smallest value in the right subtree of the node to be removed.
     * We then update the parent of the node being removed to point to the successor.
     * Additionally, if the successor is not the immediate right child of the node being removed, we adjust the connections accordingly.
     * The getSuccessor function finds the successor by starting at the right child of the node and traversing left until there are no more left children.
     * It keeps track of the parent of the successor to handle the case where the successor has a right child.
     */
    private fun getSuccessor(node: BinaryNode<T>): BinaryNode<T> {
        var parentOfSuccessor = node
        var successor = node
        var current = node.rightChild

        while (current != null) {
            parentOfSuccessor = successor
            successor = current
            current = current.leftChild
        }

        if (successor != node.rightChild) {
            parentOfSuccessor.leftChild = successor.rightChild
            successor.rightChild = node.rightChild
        }

        return successor
    }

    val isBSTRecursive: Boolean
        get() {
            return isBST(root, min = null, max = null)
        }

    private fun isBST(node: BinaryNode<T>?, min: T?, max: T?): Boolean {
        node ?: return true

        if (min != null && node.value <= min) {
            return false
        } else if (max != null && node.value > max) {
            return false
        }
        return isBST(node.leftChild, min, node.value) && isBST(node.rightChild, node.value, max)
    }

    /**
     * 1. We use three stacks: stack to store the nodes, minStack to store the lower bounds, and maxStack to store the upper bounds.
     * 2. We initialize the stacks by pushing the root node onto the stack and null onto both minStack and maxStack.
     * 3. We iterate as long as the stack is not empty.
     * 4. Within the loop, we pop a node from the stack and retrieve the corresponding min and max values from the respective stacks.
     * 5. If the current node is not null, we perform the validation by checking if its value violates the min and max bounds. If it does, we return false.
     * 6. If the validation is successful, we push the left child, along with the same min value and the current node's value as the new max value, onto the respective stacks.
     * 7. We also push the right child, along with the current node's value as the new min value and the same max value, onto the respective stacks.
     * 8. Repeat steps 4-7 until we have processed all nodes in the tree.
     * 9. If the validation completes without any violations, we return true.
     */
    val isBSTNonRecursive: Boolean
        get() {
            val stack = ArrayDeque<BinaryNode<T>?>()
            val minStack = ArrayDeque<T?>()
            val maxStack = ArrayDeque<T?>()

            stack.addFirst(root)
            minStack.addFirst(null)
            maxStack.addFirst(null)

            while (!stack.isEmpty()) {
                val current = stack.removeFirst()
                val min = minStack.removeFirst()
                val max = maxStack.removeFirst()

                if (current != null) {
                    if ((min != null && current.value <= min) || (max != null && current.value > max)) {
                        return false
                    }

                    stack.addFirst(current.leftChild)
                    minStack.addFirst(min)
                    maxStack.addFirst(current.value)

                    stack.addFirst(current.rightChild)
                    minStack.addFirst(current.value)
                    maxStack.addFirst(max)
                }
            }

            return true
        }

    override fun toString(): String = root?.let { "$it" } ?: "empty"

    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val otherTree = other as BinarySearchTree<T>
        val currentTree = this

        val currentNodes =
            ArrayDeque<BinaryNode<T>?>().also {
                it.addFirst(currentTree.root)
            }
        val otherNodes =
            ArrayDeque<BinaryNode<T>?>().also {
                it.addFirst(otherTree.root)
            }

        while (currentNodes.isNotEmpty()) {
            val currentNode = currentNodes.removeFirst()
            val otherNode = otherNodes.removeFirst()

            if (currentNode != null && otherNode != null) {
                if (currentNode != otherNode) {
                    return false
                }
                currentNodes.addFirst(currentNode.rightChild)
                currentNodes.addFirst(currentNode.leftChild)

                otherNodes.addFirst(otherNode.rightChild)
                otherNodes.addFirst(otherNode.leftChild)
            } else if (currentNode == null && otherNode != null) {
                return false
            } else if (currentNode != null && otherNode == null) {
                return false
            }
        }

        return true
    }

    fun contains(tree: BinarySearchTree<T>): Boolean {
        val values = mutableSetOf<T>()
        root?.inOrder {
            values.add(it)
        }

        var isEqual = true
        tree.root?.inOrder {
            isEqual = isEqual && values.contains(it)
        }

        return isEqual
    }

    override fun hashCode(): Int = root?.hashCode() ?: 0
}
