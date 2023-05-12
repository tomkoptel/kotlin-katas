package com.sample.tom.ds.stack

import com.sample.tom.ds.list.LinkedList

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

/**
 * Check for balanced parentheses. Given a string, check if there are ( and ) characters, and return true if the parentheses in the string are balanced.
 * The time complexity `O(n)` and space complexity `O(n)`.
 */
fun String.isBalanced(): Boolean {
    val stack = Stack<Char>()
    for (char in this) {
        when (char) {
            '(' -> stack.push(char)
            ')' -> if (stack.isEmpty) {
                return false
            } else {
                stack.pop()
            }
        }
    }
    return stack.isEmpty
}
