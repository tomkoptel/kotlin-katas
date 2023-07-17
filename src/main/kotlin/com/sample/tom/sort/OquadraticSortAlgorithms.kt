package com.sample.tom.sort

import java.util.*

object OquadraticSortAlgorithms {
    fun <T : Comparable<T>> MutableList<T>.bubbleSort() {
        if (size < 2) return

        for (end in lastIndex downTo 1) {
            var swapped = false

            println(this)

            for (current in 0 until end) {
                val next = current + 1
                val currentEl = this[current]
                val nextEl = this[next]
                if (currentEl > nextEl) {
                    Collections.swap(this, current, next)
                    swapped = true
                }
                println("end=$end current=$current next=$next currentEl=$currentEl nextEl=$nextEl")
            }

            if (!swapped) return
        }
    }

    fun <T : Comparable<T>> MutableList<T>.selectionSort() {
        if (size < 2) return

        for (current in 0 until lastIndex) {
            var lowest = current

            for (other in (current + 1) until size) {
                println("current=$current other=$other $this")
                if (this[other] < this[lowest]) {
                    lowest = other
                }
            }

            if (lowest != current) {
                Collections.swap(this, lowest, current)
            }

            println(this)
        }
    }

    fun <T : Comparable<T>> MutableList<T>.insertionSort() {
        for (current in 1 until size) {
            val range = (1..current).reversed()
            println("range=$range")
            for (shifting in range) {
                println("current=$current remaining=$shifting")
                if (this[shifting] < this[shifting - 1]) {
                    Collections.swap(this, shifting, shifting - 1)
                } else {
                    break
                }
            }
            println(this)
        }
    }

    /**
     * Given a list of Comparable elements, bring all instances of a given value in the list to the right side of the list.
     */
    fun <T : Comparable<T>> MutableList<T>.rightAlign(element: T) {
        if (this.size < 2) return
        val first = indexOfFirst { it == element }
        val last = indexOfLast { it == element }
        if (first < 0 || last < 0) return

        val shiftIndex = last - first
        val firstAtEnd = lastIndex - shiftIndex
        var index = indexOf(element)

        while (index != firstAtEnd) {
            for (current in index until size - 1) {
                Collections.swap(this, current, current + 1)
            }
            index = indexOf(element)
        }
    }

    fun <T : Comparable<T>> MutableList<T>.rightAlignBook(element: T) {
        if (this.size < 2) return
        var searchIndex = this.size - 2
        while (searchIndex >= 0) {
            if (this[searchIndex] == element) {
                var moveIndex = searchIndex
                while (moveIndex < this.size - 1 && this[moveIndex + 1] != element) {
                    Collections.swap(this, moveIndex, moveIndex + 1)
                    moveIndex++
                }
            }
            searchIndex--
        }
    }

    fun <T : Comparable<T>> MutableList<T>.biggestDuplicate(): T? {
        if (this.size == 0) return null
        for (current in (1 until size).reversed()) {
            val el = this[current]
            if (el.compareTo(this[current - 1]) == 0) {
                return this[current]
            }
        }
        return null
    }

    fun <T : Comparable<T>> MutableList<T>.rev() {
        var left = 0
        var right = lastIndex
        while (left < right) {
            Collections.swap(this, right, left)
            right--
            left++
        }
    }
}
