package com.sample.tom.ds.collection

fun <T: Any> LinkedList<T>.reverse(): LinkedList<T> {
    if (isEmpty()) return this
    if (size == 1) return this
    val newList = LinkedList<T>()
    for (el in this) {
        newList.push(el)
    }
    return newList
}
