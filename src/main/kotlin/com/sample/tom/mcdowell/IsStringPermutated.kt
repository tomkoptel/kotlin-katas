package com.sample.tom.mcdowell

fun String.isAPermutationOf(other: String): Boolean {
    val left = this.toCharArray().sorted()
    val right = other.toCharArray().sorted()
    return left == right
}

fun String.isAPermutationOfUsingBitVector(other: String): Boolean {
    if (length != other.length) return false

    val leftVector = generateBitVector(this)
    val rightVector = generateBitVector(other)

    return leftVector == rightVector
}

private fun generateBitVector(str: String): Long {
    var vector = 0L

    for (char in str) {
        val charCode = char.code
        if (charCode in 'a'.code..'z'.code) {
            // For uppercase characters, we set the bit position from 0 to 25 (corresponding to 'A' to 'Z').
            vector = vector or (1L shl (charCode - 65))
        } else if (charCode in 'A'.code..'Z'.code) {
            // For lowercase characters, we set the bit position from 26 to 51 (corresponding to 'a' to 'z').
            vector = vector or (1L shl (charCode - 97 + 26))
        }
    }

    return vector
}
