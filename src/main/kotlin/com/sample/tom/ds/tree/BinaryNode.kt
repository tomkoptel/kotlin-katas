package com.sample.tom.ds.tree

typealias Visitor<T> = (T) -> Unit

class BinaryNode<T>(val value: T) {
    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    fun preOrder(visitor: Visitor<T>) {
        visitor(value)
        leftChild?.preOrder(visitor)
        rightChild?.preOrder(visitor)
    }

    fun inOrder(visitor: Visitor<T>) {
        leftChild?.inOrder(visitor)
        visitor(value)
        rightChild?.inOrder(visitor)
    }

    fun postOrder(visitor: Visitor<T>) {
        leftChild?.postOrder(visitor)
        rightChild?.postOrder(visitor)
        visitor(value)
    }

    override fun toString() = diagram(this)

    //  ┌──null\n┌──9\n│ └──8\n7\n│ ┌──5\n└──1\n└──0
    // ┌──null
    // ┌──9
    // │ └──8
    // 7
    // │ ┌──5
    // └──1
    // └──0
    private fun diagram(
        node: BinaryNode<T>?,
        top: String = "",
        root: String = "",
        bottom: String = "",
    ): String {
        return node?.let {
            if (node.leftChild == null && node.rightChild == null) {
                "$root${node.value}\n"
            } else {
                val right = diagram(
                    node = node.rightChild,
                    top = "$top ",
                    root = "$top┌──",
                    bottom = "$top│ "
                )
                val left = diagram(
                    node = node.leftChild,
                    top = "$bottom│ ",
                    root = "$bottom└──",
                    bottom = "$bottom "
                )
                right + root + "${node.value}\n" + left
            }
        } ?: "${root}null\n"
    }
}
