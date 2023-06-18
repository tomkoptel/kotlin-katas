package com.sample.tom.ds.tree

class AVLNode<T>(var value: T) {
    var leftChild: AVLNode<T>? = null
    var rightChild: AVLNode<T>? = null

    val min: AVLNode<T> get() = leftChild?.min ?: this

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

    val height: Int
        get() {
            var left = leftChild
            var leftLevel = 0
            while (left != null) {
                left = left.leftChild
                leftLevel++
            }

            var right = rightChild
            var rightLevel = 0
            while (right != null) {
                right = right.rightChild
                rightLevel++
            }

            return rightLevel.coerceAtLeast(leftLevel) + 1
        }

    fun height(node: AVLNode<T>? = this): Int {
        return node?.let {
            1 + height(node.leftChild).coerceAtLeast(height(node.rightChild))
        } ?: 0
    }

    fun serialize(): MutableList<T?> {
        val result = mutableListOf<T?>()
        serializeHelper(this) { value -> result.add(value) }
        return result
    }

    private fun <T> serializeHelper(node: AVLNode<T>?, visitor: Visitor<T?>) {
        if (node == null) {
            visitor(null)
            return
        }

        visitor(node.value)
        serializeHelper(node.leftChild, visitor)
        serializeHelper(node.rightChild, visitor)
    }

    companion object {
        fun <T> deserialize(data: List<T?>): AVLNode<T?>? {
            val iterator = data.iterator()
            return deserializeHelper(iterator)
        }

        private fun <T> deserializeHelper(iterator: Iterator<T?>): AVLNode<T?>? {
            if (!iterator.hasNext()) {
                return null
            }

            val value = iterator.next() ?: return null

            val node = AVLNode<T?>(value)
            node.leftChild = deserializeHelper(iterator)
            node.rightChild = deserializeHelper(iterator)

            return node
        }
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
        node: AVLNode<T>?,
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

    override fun equals(other: Any?): Boolean {
        // 2
        return if (other != null && other is AVLNode<*>) {
            this.value == other.value &&
                this.leftChild == other.leftChild &&
                this.rightChild == other.rightChild
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        return result
    }
}
