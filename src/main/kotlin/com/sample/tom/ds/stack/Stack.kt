package com.sample.tom.ds.stack

@Suppress("FunctionName")
fun <T : Any> Stack(): Stack<T> = Stack.Impl()

fun <T : Any> stackOf(items: Iterable<T>): Stack<T> {
    val stack = Stack<T>()
    for (item in items) {
        stack.push(item)
    }
    return stack
}

interface Stack<T : Any> {
    fun push(value: T): T

    fun pop(): T?

    fun peek(): T?

    val size: Int
    val isEmpty: Boolean
        get() = size == 0

    class Impl<T : Any> : Stack<T> {
        private var storage = arrayListOf<T>()

        override fun push(value: T): T {
            storage.add(value)
            return value
        }

        override fun pop(): T? {
            if (isEmpty) return null
            return storage.removeAt(storage.lastIndex)
        }

        override fun peek(): T? = storage.lastOrNull()

        override val size: Int
            get() = storage.size

        override fun toString(): String = buildString {
            appendLine("----top----")
            storage.asReversed().forEach {
                appendLine("$it")
            }
            appendLine("-----------")
        }
    }
}
