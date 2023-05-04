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
