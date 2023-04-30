package com.sample.tom.ds.collection

data class Node<T : Any>(var value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        val next = next
        return if (next == null) {
            "$value"
        } else {
            "$value -> ${next.toString()}"
        }
    }
}
