package com.sample.tom.ds.collection

fun <T : Any> LinkedList<T>.reverse(): LinkedList<T> {
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
    var traverses = 1
    while (fast != null) {
        fast = fast.next
        if (fast != null) {
            fast = fast.next
            slow = slow?.next
        }
        traverses++
    }
    println("size = $size traverses => $traverses")

    return slow?.value
}
