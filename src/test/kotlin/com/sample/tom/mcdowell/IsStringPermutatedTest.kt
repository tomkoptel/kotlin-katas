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
    }

    @Test
    fun `the string is not a permutation of another`() {
        "abc".isAPermutationOf("abca") shouldBe false
        "abc".isAPermutationOf("abcb") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
        "abc".isAPermutationOf("abcc") shouldBe false
    }

    @Test
    fun `the string is a permutation of another using isAPermutationOfVector`() {
        "listen".isAPermutationOfVector("silent") shouldBe true
        "triangle".isAPermutationOfVector("integral") shouldBe true
        "cat".isAPermutationOfVector("act") shouldBe true
        "debit card".isAPermutationOfVector("bad credit") shouldBe true

        "abc".isAPermutationOfVector("cba") shouldBe true
        "aaa".isAPermutationOfVector("aaa") shouldBe true
        "abc".isAPermutationOfVector("abc") shouldBe true
        "abc".isAPermutationOfVector("bca") shouldBe true
        "cba".isAPermutationOfVector("abc") shouldBe true
        "aaa".isAPermutationOfVector("aaa") shouldBe true
        "abc".isAPermutationOfVector("abc") shouldBe true
        "bca".isAPermutationOfVector("abc") shouldBe true
    }

    @Test
    fun `the string is not a permutation of another using isAPermutationOfVector`() {
        "abc".isAPermutationOfVector("abca") shouldBe false
        "abc".isAPermutationOfVector("abcb") shouldBe false
        "abc".isAPermutationOfVector("abcc") shouldBe false
        "abc".isAPermutationOfVector("abcc") shouldBe false
        "abc".isAPermutationOfVector("abcc") shouldBe false
    }
}
