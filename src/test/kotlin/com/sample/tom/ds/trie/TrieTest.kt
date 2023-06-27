package com.sample.tom.ds.trie

import org.junit.jupiter.api.Test
import com.sample.tom.ds.trie.Trie.Companion.insert
import com.sample.tom.ds.trie.Trie.Companion.contains
import com.sample.tom.ds.trie.Trie.Companion.remove
import com.sample.tom.ds.trie.Trie.Companion.allPrefixesRecursive
import com.sample.tom.ds.trie.Trie.Companion.allLists
import com.sample.tom.ds.trie.Trie.Companion.allPrefixesNonRecursive
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class TrieTest {
    @Test
    fun `should support insertion of word in trie`() {
        val trie = Trie<Char>()
        trie.insert("dog")
        trie.insert("cute")
        trie.insert("cat")
        trie.contains("dog").shouldBeTrue()
    }

    @Test
    fun `should support removal of the word in trie`() {
        val trie = Trie<Char>()
        trie.insert("cat")
        trie.insert("car")
        trie.insert("cart")
        trie.remove("cart")
        trie.contains("cart").shouldBeFalse()
    }

    @Test
    fun `supports listing of the prefixes in recursive way`() {
        val trie = Trie<Char>().apply {
            insert("car")
            insert("card")
            insert("care")
            insert("cared")
            insert("cars")
            insert("carbs")
            insert("carapace")
            insert("cargo")
        }

        val prefixedWithCar = trie.allPrefixesRecursive("car")
        prefixedWithCar shouldContainAll listOf("car", "carapace", "carbs", "cars", "card", "care", "cared", "cargo")

        val prefixedWithCare = trie.allPrefixesRecursive("care")
        prefixedWithCare shouldContainAll listOf("care", "cared")
    }

    @Test
    fun `supports listing of the prefixes in non recursive way`() {
        val trie = Trie<Char>().apply {
            insert("car")
            insert("card")
            insert("care")
            insert("cared")
            insert("cars")
            insert("carbs")
            insert("carapace")
            insert("cargo")
        }

        val prefixedWithCar = trie.allPrefixesNonRecursive("car")
        prefixedWithCar shouldContainAll listOf("car", "carapace", "carbs", "cars", "card", "care", "cared", "cargo")

        val prefixedWithCare = trie.allPrefixesNonRecursive("care")
        prefixedWithCare shouldContainAll listOf("care", "cared")
    }

    @Test
    fun `lists return collection of all words`() {
        val trie = Trie<Char>().apply {
            insert("car")
            insert("card")
            insert("care")
            insert("cared")
            insert("cars")
            insert("carbs")
            insert("carapace")
            insert("cargo")
        }

        trie.allLists() shouldContainAll listOf("car", "card", "care", "cared", "cars", "carbs", "carapace", "cargo")
        trie.listsCount shouldBe 8
    }

    @Test
    fun `is trie empty`() {
        val trie = Trie<Char>()
        trie.isEmpty.shouldBeTrue()
        trie.insert("card")
        trie.isEmpty.shouldBeFalse()
        trie.remove("card")
        trie.isEmpty.shouldBeTrue()
    }
}
