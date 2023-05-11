package com.sample.tom.mcdowell

fun String.isAPermutationOf(other: String): Boolean {
    val left = this.toCharArray().sorted()
    val right = other.toCharArray().sorted()
    return left == right
}

fun String.isAPermutationOfVector(other: String): Boolean {
    if (length != other.length) return false

    var leftVector = 0
    var rightVector = 0
    for (i in indices) {
        val leftChar = this[i].code
        val rightChar = this[i].code
        leftVector = leftVector or (1 shl leftChar)
        rightVector = rightVector or (1 shl rightChar)
    }

    return leftVector == rightVector
}
