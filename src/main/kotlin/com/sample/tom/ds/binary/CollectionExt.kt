package com.sample.tom.ds.binary

object CollectionExt {
    fun <T : Comparable<T>> List<T>.binarySearch(el: T): Int? {
        if (isEmpty()) return null
        var start = 0
        var end = size - 1
        while (start <= end) {
            val middle = (start + end) / 2
            val middleEl = this[middle]
            if (middleEl == el) {
                return middle
            } else if (middleEl < el) {
                start = middle + 1
            } else {
                end = middle - 1
            }
        }
        return null
    }
}
