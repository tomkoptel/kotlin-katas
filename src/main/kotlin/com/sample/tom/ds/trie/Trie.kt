package com.sample.tom.ds.trie

class Trie<Key : Any> {
    private val root = TrieNode<Key>()

    companion object {
        fun Trie<Char>.insert(word: String): Trie<Char> {
            return apply {
                insert(word.toList())
            }
        }

        fun Trie<Char>.contains(word: String): Boolean {
            return contains(word.toList())
        }
    }

    fun insert(keys: List<Key>) {
        var current = root

        keys.forEach { element ->
            val child = current.children[element] ?: TrieNode(key = element, parent = current)
            current.children[element] = child
            current = child
        }

        current.isTerminating = true
    }

    fun contains(keys: List<Key>): Boolean {
        var current = root
        keys.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }
}
