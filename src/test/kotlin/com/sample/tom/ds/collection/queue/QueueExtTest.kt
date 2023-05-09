package com.sample.tom.ds.collection.queue

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QueueExtTest {
    @Test
    fun reverse() {
        val queue = linkedListQueueQueueOf(listOf("1", "2", "3", "4"))
        "$queue" shouldBe "1 > 2 > 3 > 4"
        "${queue.reverse()}" shouldBe "4 > 3 > 2 > 1"
    }
}
