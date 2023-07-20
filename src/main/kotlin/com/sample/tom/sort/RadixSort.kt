package com.sample.tom.sort

object RadixSort {
    fun MutableList<Int>.radixSort(): MutableList<Int> {
        if (isEmpty()) return this

        val base = 10
        var digits = 1
        var done = false

        while (!done) {
            done = true

            val buckets = MutableList<MutableList<Int>>(10) { mutableListOf() }
            forEach { number ->
                val remainingPart = number / digits
                val digit = remainingPart % base
                buckets[digit] += number
                if (remainingPart > 0) {
                    done = false
                }
            }
            digits *= base

            clear()
            addAll(buckets.flatten())
        }

        return this
    }
}
