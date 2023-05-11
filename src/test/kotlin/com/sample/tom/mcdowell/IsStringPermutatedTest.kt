package com.sample.tom.mcdowell

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class IsStringPermutatedTest {
    @Test
    fun `the string is a permutation of another`() {
        "abc".isAPermutationOf("cba") shouldBe true
        "aaa".isAPermutationOf("aaa") shouldBe true
        "abc".isAPermutationOf("abc") shouldBe true
        "abc".isAPermutationOf("bca") shouldBe true
        "cba".isAPermutationOf("abc") shouldBe true
        "aaa".isAPermutationOf("aaa") shouldBe true
        "abc".isAPermutationOf("abc") shouldBe true
        "bca".isAPermutationOf("abc") shouldBe true
    }

    @Test
    fun `the string is not a permutation of another`() {
        "abc".isAPermutationOf("abca") shouldBe false
        "abc".isAPermutationOf("abcb") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
    }
}
