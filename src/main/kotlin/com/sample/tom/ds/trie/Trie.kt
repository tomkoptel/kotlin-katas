package com.sample.tom.ds.trie

import java.util.Stack

class Trie<Key : Any> {
    private val root = TrieNode<Key>()
    private val storedLists: MutableSet<List<Key>> = mutableSetOf()

    companion object {
        fun Trie<Char>.insert(word: String): Trie<Char> = apply { insert(word.toList()) }

        fun Trie<Char>.contains(word: String): Boolean = contains(word.toList())

        fun Trie<Char>.remove(word: String): Trie<Char> = apply { remove(word.toList()) }

        fun Trie<Char>.allPrefixesRecursive(prefix: String): List<String> = allPrefixesRecursive(prefix.toList()).map { it.joinToString(separator = "") }

        fun Trie<Char>.allPrefixesNonRecursive(prefix: String): List<String> = allPrefixesNonRecursive(prefix.toList()).map { it.joinToString(separator = "") }

        fun Trie<Char>.allLists(): List<String> = lists().map { it.joinToString(separator = "") }
    }

    fun insert(keys: List<Key>) {
        var current = root

        keys.forEach { element ->
            val child = current.children[element] ?: TrieNode(key = element, parent = current)
            current.children[element] = child
            current = child
        }

        current.isTerminating = true
        storedLists.add(keys)
    }

    fun contains(keys: List<Key>): Boolean {
        var current = root
        keys.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        storedLists.remove(keys)
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
        var parent = current.parent
        while (parent != null && current.children.isEmpty() && !current.isTerminating) {
            parent.children.remove(current.key)
            current = parent
            parent = current.parent
        }
    }

    fun allPrefixesRecursive(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }
        return allPrefixesRecursive(prefix, current)
    }

    private fun allPrefixesRecursive(prefix: List<Key>, node: TrieNode<Key>): List<List<Key>> {
        val results = mutableListOf<List<Key>>()

        if (node.isTerminating) {
            results.add(prefix)
        }

        node.children.forEach { (key, node) ->
            val subprefixes = allPrefixesRecursive(prefix + key, node)
            results.addAll(subprefixes)
        }

        return results
    }

    fun allPrefixesNonRecursive(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }

        val results = mutableListOf<List<Key>>()
        val stack =
            Stack<Pair<List<Key>, TrieNode<Key>>>().also {
                it.push(prefix to current)
            }

        while (stack.size > 0) {
            val (currentPrefix, currentNode) = stack.pop()

            if (currentNode.isTerminating) {
                results.add(currentPrefix)
            }

            currentNode.children.forEach { (key, node) ->
                val subPrefix = currentPrefix + key
                stack.push(subPrefix to node)
            }
        }

        return results
    }

    val listsCount: Int get() = storedLists.size

    val isEmpty: Boolean get() = root.children.isEmpty()

    fun lists(): List<List<Key>> = storedLists.toList()
}
