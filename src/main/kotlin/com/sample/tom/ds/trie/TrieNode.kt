package com.sample.tom.ds.trie

/**
 * key is optional, because the root of the trie has no key
 * a reference to the parent helps to remove() the node
 */
class TrieNode<Key : Any>(val key: Key? = null, val parent: TrieNode<Key>? = null) {
    /**
     *
     */
    val children = HashMap<Key, TrieNode<Key>>()

    /**
     * The indicator of the end of the collection
     */
    var isTerminating = false
}
