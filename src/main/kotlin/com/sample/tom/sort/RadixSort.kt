package com.sample.tom.sort

import java.lang.Math.pow
import java.text.FieldPosition

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

    fun MutableList<Int>.lexicographicalSort(): MutableList<Int> {
        return msdRadixSorted(this, 0)
    }

    private fun msdRadixSorted(list: MutableList<Int>, position: Int): MutableList<Int> {
        if (position >= list.maxDigits()) return list
        val buckets = MutableList<MutableList<Int>>(10) { mutableListOf() }
        val priorityBucket = arrayListOf<Int>()

        list.forEach { number ->
            val digit = number.digit(position)
            if (digit == null) {
                priorityBucket.add(number)
            } else {
                buckets[digit].add(number)
            }
        }

        val newValues = buckets.reduce { result, bucket ->
            if (bucket.isEmpty()) return@reduce result
            result.addAll(msdRadixSorted(bucket, position + 1))
            result
        }
        priorityBucket.addAll(newValues)
        return priorityBucket
    }

    private fun Int.digits(): Int {
        var count = 0
        var num = this
        while (num != 0) {
            count += 1
            num /= 10
        }
        return count
    }

    private fun Int.digit(atPosition: Int): Int? {
        val correctPosition = (atPosition + 1).toDouble()
        if (correctPosition > digits()) return null

        var num = this
        while (num / (pow(10.0, correctPosition).toInt()) != 0) {
            num /= 10
        }
        return num % 10
    }

    private fun List<Int>.maxDigits(): Int {
        return maxOrNull()?.digits() ?: 0
    }
}
