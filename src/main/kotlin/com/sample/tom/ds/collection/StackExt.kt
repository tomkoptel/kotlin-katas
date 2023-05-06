package com.sample.tom.ds.collection

/**
 * Given a linked list, print the nodes in reverse order. You should not use recursion to solve this problem.
 * The time complexity `O(2n)` or `O(n)` and space complexity `O(n)`.
 */
fun <T : Any> LinkedList<T>.inReverseWithStack(): LinkedList<T> {
    val stack = stackOf(this)
    val list = LinkedList<T>()
    while (!stack.isEmpty) {
        stack.pop()?.let(list::add)
    }
    return list
}
