package com.sample.tom.ds.tree

class AVLNode<T>(var value: T) {
    var leftChild: AVLNode<T>? = null
    var rightChild: AVLNode<T>? = null

    var height: Int = 0
    private val leftHeight: Int get() = leftChild?.height ?: -1
    private val rightHeight: Int get() = rightChild?.height ?: -1
    val balanceFactor: Int get() = leftHeight - rightHeight

    val min: AVLNode<T> get() = leftChild?.min ?: this

    fun recomputeHeight() {
        height = Math.max(leftHeight, rightHeight) + 1
    }

    override fun toString() = diagram(this)

    private fun diagram(node: AVLNode<T>?, top: String = "", root: String = "", bottom: String = ""): String = node?.let {
        if (node.leftChild == null && node.rightChild == null) {
            "$root${node.value}\n"
        } else {
            val right =
                diagram(
                    node = node.rightChild,
                    top = "$top ",
                    root = "$top┌──",
                    bottom = "$top│ "
                )
            val left =
                diagram(
                    node = node.leftChild,
                    top = "$bottom│ ",
                    root = "$bottom└──",
                    bottom = "$bottom "
                )
            right + root + "${node.value}\n" + left
        }
    } ?: "${root}null\n"
}
