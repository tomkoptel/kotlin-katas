package com.sample.tom.mcdowell

/**
 * Determine if a string is unique, i.e. no duplicate characters. You can not use additional data structures.
 */
fun String.isUnique(): Boolean {
    val sorted = this.toCharArray().apply { sort() }

    sorted.forEachIndexed { index, char ->
        if (index + 1 < sorted.size) {
            val nextChar = sorted[index + 1]
            if (nextChar == char) return false
        }
    }

    return true
}

/**
 * ASCII uses 7 bits to represent characters (2^7 = 128) unique characters.
 */
fun String.isUniqueASCIILowercase(): Boolean {
    // The function begins by checking the length of the string.
    // If the length is greater than 128, it immediately returns false.
    // This is because ASCII has a total of 128 characters, so any string
    // longer than 128 characters cannot have all unique ASCII characters.
    if (length > 128) return false

    // The variable checker is initialized to 0. This variable will be used
    // as a bit vector to keep track of which ASCII characters have been encountered.
    var checker = 0

    for (i in indices) {
        // To map each character to its corresponding bit in the checker variable,
        // we need an index. Since there are 26 lowercase characters in the English
        // alphabet ('a' to 'z'), we want to map each character to an index from 0 to 25.
        // The expression chars[i] - 'a' calculates the difference between the ASCII value
        // of the character chars[i] and the ASCII value of the character 'a'.
        // This subtraction operation effectively maps the character to an index in the range 0 to 25.
        // For example, if chars[i] is 'c', the ASCII value of 'c' is 99, and the ASCII value
        // of 'a' is 97. So, the expression chars[i] - 'a' gives us 99 - 97 = 2, which corresponds
        // to the index for character 'c'
        //
        // In summary, the expression chars[i] - 'a' is used to map each lowercase character to a unique index within the bit vector.
        val charValue: Int = this[i] - 'a'

        // The next step is to check if the bit corresponding to the
        // current character has already been set in the checker variable.
        // This is done by performing a bitwise AND operation between
        // checker and (1 shl value) (which shifts the bit 1 to the left
        // by value positions). If the result is greater than 0, it means
        // that the bit was already set, indicating a duplicate character.
        // If bit corresponding to current character is already set
        if (checker and (1 shl charValue) > 0) return false

        // set bit in checker
        // If the current character is unique, the corresponding
        // bit is set in the checker variable by performing a bitwise
        // OR operation between checker and (1 shl value).
        checker = checker or (1 shl charValue)
    }

    return true
}

fun String.isUniqueASCIIExtended(): Boolean {
    // The function begins by checking the length of the string.
    // If the length is greater than 128, it immediately returns false.
    // This is because ASCII has a total of 128 characters, so any string
    // longer than 128 characters cannot have all unique ASCII characters.
    //
    // Extended ASCII range
    // The length check is modified to if (length > 256) to account for the
    // extended ASCII range, which includes both lowercase and uppercase characters.
    if (length > 256) return false

    // The variable checker is initialized to 0. This variable will be used
    // as a bit vector to keep track of which ASCII characters have been encountered.
    // Using an array of Ints to represent 256 bits (8 * 32 bits)
    // This array will have 8 elements, with each element representing 32 bits.
    // This allows us to represent a total of 256 bits (8 * 32 bits).
    val checker = IntArray(8)

    for (i in indices) {
        val charValue: Int = this[i].code

        // Calculate the index and bit position for the character
        val index = charValue / 32 // Divide by 32 to get the index in the checker array

        // If bit corresponding to the current character is already set
        if ((checker[index] and (1 shl charValue)) != 0) return false

        // Set the bit in the checker
        checker[index] = checker[index] or (1 shl charValue)
    }

    return true
}
