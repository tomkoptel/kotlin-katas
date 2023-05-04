package com.sample.tom.ds.collection

fun <T : Any> LinkedList<T>.immutableReverse(): LinkedList<T> {
    if (isEmpty()) return this
    if (size == 1) return this
    val newList = LinkedList<T>()
    for (el in this) {
        newList.push(el)
    }
    return newList
}

fun <T : Any> LinkedList<T>.immutableRecursiveReverse(): LinkedList<T> {
    val newList = LinkedList<T>()
    val head = nodeAt(0) ?: return newList
    immutableRecursiveReverse(head, newList)
    return newList
}

private fun <T : Any> immutableRecursiveReverse(node: Node<T>, list: LinkedList<T>) {
    val next = node.next
    if (next != null) {
        immutableRecursiveReverse(next, list)
    }
    list.add(node.value)
}

fun <T : Any> LinkedList<T>.middle(): T? {
    if (isEmpty()) return null
    if (size == 1) return nodeAt(0)?.value
    return nodeAt(size / 2)?.value
}

/**
 * Once the faster reference reaches the end the slowe reference will be in the middle
 */
fun <T : Any> LinkedList<T>.middleWithRunner(): T? {
    var slow = nodeAt(0)
    var fast = nodeAt(0)
    while (fast != null) {
        fast = fast.next
        if (fast != null) {
            fast = fast.next
            slow = slow?.next
        }
    }
    return slow?.value
}
