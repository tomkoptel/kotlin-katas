package com.sample.tom.mcdowell

fun String.isAPermutationOf(other: String): Boolean {
    val left = this.toCharArray().sorted()
    val right = other.toCharArray().sorted()
    return left == right
}
