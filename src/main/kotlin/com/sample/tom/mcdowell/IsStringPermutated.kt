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

fun String.isAPermutationOfUsingFrequencyMap(other: String): Boolean {
    if (length != other.length) return false
    val frequencyMap = mutableMapOf<Char, Int>()
    for (i in indices) {
        val char = this[i]
        frequencyMap[char] = frequencyMap.getOrDefault(char, 0) + 1
    }

    for (i in other.indices) {
        val rightChar = other[i]
        frequencyMap[rightChar] = frequencyMap.getOrDefault(rightChar, 0) - 1
        // we increment the frequency count for each character in the first string,
        // and then decrement the count for each character in the second string.
        // If the count becomes negative during the iteration, it means the second
        // string contains a character that is not present in the first string, or
        // it contains more occurrences of a character than the first string.
        if (frequencyMap.contains(rightChar) && frequencyMap[rightChar]!! < 0) {
            return false
        }
    }

    return true
}
