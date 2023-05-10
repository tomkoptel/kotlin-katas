package com.sample.tom.mcdowell

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class IsUniqueStringTest {
    @Test
    fun `is string unique`() {
        "abc".isUnique() shouldBe true
        "aaa".isUnique() shouldBe false
        "".isUnique() shouldBe true
        "abca".isUnique() shouldBe false
        "aaabbccdaa".isUnique() shouldBe false
    }

    @Test
    fun `is ascii string unique`() {
        "abc".isUniqueASCIILowercase() shouldBe true
        "aaa".isUniqueASCIILowercase() shouldBe false
        "".isUniqueASCIILowercase() shouldBe true
        "abca".isUniqueASCIILowercase() shouldBe false
        "aaabbccdaa".isUniqueASCIILowercase() shouldBe false
    }

    @Test
    fun `is extended ascii string unique`() {
        "abc".isUniqueASCIIExtended() shouldBe true
        "aaa".isUniqueASCIIExtended() shouldBe false
        "".isUniqueASCIIExtended() shouldBe true
        "abca".isUniqueASCIIExtended() shouldBe false
        "aaabbccdaa".isUniqueASCIIExtended() shouldBe false
    }
}
