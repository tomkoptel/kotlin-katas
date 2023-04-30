package com.sample.tom.ds.collection

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NodeTest {
    @Test
    fun `setup node`() {
        Node(value = "1", next = Node(value = "2")).toString() shouldBe "1 -> 2"
    }
}
