package com.sample.tom.ds.trie

class Trie<Key : Any> {
    private val root = TrieNode<Key>()

    companion object {
        fun Trie<Char>.insert(word: String): Trie<Char> {
            return apply { insert(word.toList()) }
        }

        fun Trie<Char>.contains(word: String): Boolean {
            return contains(word.toList())
        }

        fun Trie<Char>.remove(word: String): Trie<Char> {
            return apply { remove(word.toList()) }
        }

        fun Trie<Char>.collections(prefix: String): List<String> {
            return collections(prefix.toList()).map { it.joinToString(separator = "") }
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

    fun remove(keys: List<Key>) {
        var current = root

        keys.forEach { element ->
            val child = current.children[element] ?: return
            current = child
        }

        if (!current.isTerminating) return

        current.isTerminating = false
        val parent = current.parent
        while (parent != null && current.children.isEmpty() && !current.isTerminating) {
            parent.children.remove(current.key)
            current = parent
        }
    }

    fun collections(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }
        return collections(prefix, current)
    }

    private fun collections(prefix: List<Key>, node: TrieNode<Key>): List<List<Key>> {
        val results = mutableListOf<List<Key>>()

        if (node.isTerminating) {
            results.add(prefix)
        }

        node.children.forEach { (key, node) ->
            val subprefixes = collections(prefix + key, node)
            results.addAll(subprefixes)
        }

        return results
    }
}
