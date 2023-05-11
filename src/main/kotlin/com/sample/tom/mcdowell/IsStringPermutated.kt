package com.sample.tom.mcdowell

fun String.isAPermutationOf(other: String): Boolean {
    val left = this.toCharArray().map { it.code }.sorted()
    val right = other.toCharArray().map { it.code }.sorted()
    return left == right
}
