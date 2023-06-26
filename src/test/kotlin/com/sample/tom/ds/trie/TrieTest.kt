package com.sample.tom.ds.trie

import org.junit.jupiter.api.Test
import com.sample.tom.ds.trie.Trie.Companion.insert
import com.sample.tom.ds.trie.Trie.Companion.contains
import com.sample.tom.ds.trie.Trie.Companion.remove
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

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
}
