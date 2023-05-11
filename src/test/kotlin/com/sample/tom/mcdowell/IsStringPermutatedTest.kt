package com.sample.tom.mcdowell

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class IsStringPermutatedTest {
    @Test
    fun `the string is a permutation of another`() {
        "listen".isAPermutationOf("silent") shouldBe true
        "triangle".isAPermutationOf("integral") shouldBe true
        "cat".isAPermutationOf("act") shouldBe true
        "debit card".isAPermutationOf("bad credit") shouldBe true

        "abc".isAPermutationOf("cba") shouldBe true
        "aaa".isAPermutationOf("aaa") shouldBe true
        "abc".isAPermutationOf("abc") shouldBe true
        "abc".isAPermutationOf("bca") shouldBe true
        "cba".isAPermutationOf("abc") shouldBe true
        "aaa".isAPermutationOf("aaa") shouldBe true
        "abc".isAPermutationOf("abc") shouldBe true
        "bca".isAPermutationOf("abc") shouldBe true

        "Dog".isAPermutationOf("goD") shouldBe true
    }

    @Test
    fun `the string is not a permutation of another`() {
        "abc".isAPermutationOf("abca") shouldBe false
        "abc".isAPermutationOf("abcb") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "Dog".isAPermutationOf("god") shouldBe false
    }

    @Test
    fun `the string is a permutation of another using isAPermutationOfUsingBitVector`() {
        "listen".isAPermutationOfUsingBitVector("silent") shouldBe true
        "triangle".isAPermutationOfUsingBitVector("integral") shouldBe true
        "cat".isAPermutationOfUsingBitVector("act") shouldBe true
        "debit card".isAPermutationOfUsingBitVector("bad credit") shouldBe true

        "abc".isAPermutationOfUsingBitVector("cba") shouldBe true
        "aaa".isAPermutationOfUsingBitVector("aaa") shouldBe true
        "abc".isAPermutationOfUsingBitVector("abc") shouldBe true
        "abc".isAPermutationOfUsingBitVector("bca") shouldBe true
        "cba".isAPermutationOfUsingBitVector("abc") shouldBe true
        "aaa".isAPermutationOfUsingBitVector("aaa") shouldBe true
        "abc".isAPermutationOfUsingBitVector("abc") shouldBe true
        "bca".isAPermutationOfUsingBitVector("abc") shouldBe true

        "Dog".isAPermutationOfUsingBitVector("goD") shouldBe true
    }

    @Test
    fun `the string is not a permutation of another using isAPermutationOfUsingBitVector`() {
        "abc".isAPermutationOfUsingBitVector("abca") shouldBe false
        "abc".isAPermutationOfUsingBitVector("abcb") shouldBe false
        "abc".isAPermutationOfUsingBitVector("abcc") shouldBe false
        "abc".isAPermutationOfUsingBitVector("abcc") shouldBe false
        "abc".isAPermutationOfUsingBitVector("abcc") shouldBe false

        "Dog".isAPermutationOfUsingBitVector("god") shouldBe false
    }

    @Test
    fun `the string is a permutation of another using isAPermutationOfUsingFrequencyMap`() {
        "listen".isAPermutationOfUsingFrequencyMap("silent") shouldBe true
        "triangle".isAPermutationOfUsingFrequencyMap("integral") shouldBe true
        "cat".isAPermutationOfUsingFrequencyMap("act") shouldBe true
        "debit card".isAPermutationOfUsingFrequencyMap("bad credit") shouldBe true

        "abc".isAPermutationOfUsingFrequencyMap("cba") shouldBe true
        "aaa".isAPermutationOfUsingFrequencyMap("aaa") shouldBe true
        "abc".isAPermutationOfUsingFrequencyMap("abc") shouldBe true
        "abc".isAPermutationOfUsingFrequencyMap("bca") shouldBe true
        "cba".isAPermutationOfUsingFrequencyMap("abc") shouldBe true
        "aaa".isAPermutationOfUsingFrequencyMap("aaa") shouldBe true
        "abc".isAPermutationOfUsingFrequencyMap("abc") shouldBe true
        "bca".isAPermutationOfUsingFrequencyMap("abc") shouldBe true
        "Dog".isAPermutationOfUsingFrequencyMap("goD") shouldBe true
    }

    @Test
    fun `the string is not a permutation of another using isAPermutationOfUsingFrequencyMap`() {
        "abc".isAPermutationOfUsingFrequencyMap("abca") shouldBe false
        "abc".isAPermutationOfUsingFrequencyMap("abcb") shouldBe false
        "abc".isAPermutationOfUsingFrequencyMap("abcc") shouldBe false
        "abc".isAPermutationOfUsingFrequencyMap("abcc") shouldBe false
        "abc".isAPermutationOfUsingFrequencyMap("abcc") shouldBe false
        "Dog".isAPermutationOfUsingFrequencyMap("god") shouldBe false
        "aabb".isAPermutationOfUsingFrequencyMap("bxaa") shouldBe false
    }
}
