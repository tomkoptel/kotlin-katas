package com.sample.tom.ds.graph

import com.sample.tom.ds.graph.GraphSearch.breadthFirstSearch
import com.sample.tom.ds.graph.GraphSearch.breadthFirstSearchRecursive
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GraphSearchTest {
    private val graph = AdjacencyList<String>()
    private val singapore = graph.createVertex("Singapore")
    private val tokyo = graph.createVertex("Tokyo")
    private val hongKong = graph.createVertex("Hong Kong")
    private val detroit = graph.createVertex("Detroit")
    private val sanFrancisco = graph.createVertex("San Francisco")
    private val washingtonDC = graph.createVertex("Washington DC")
    private val austinTexas = graph.createVertex("Austin Texas")
    private val seattle = graph.createVertex("Seattle")

    init {
        graph.add(EdgeType.DIRECTED, singapore, hongKong, 300.0)
        graph.add(EdgeType.DIRECTED, singapore, tokyo, 500.0)
        graph.add(EdgeType.DIRECTED, hongKong, tokyo, 250.0)
        graph.add(EdgeType.DIRECTED, tokyo, detroit, 450.0)
        graph.add(EdgeType.DIRECTED, tokyo, washingtonDC, 300.0)
        graph.add(EdgeType.DIRECTED, hongKong, sanFrancisco, 600.0)
        graph.add(EdgeType.DIRECTED, detroit, austinTexas, 50.0)
        graph.add(EdgeType.DIRECTED, austinTexas, washingtonDC, 292.0)
        graph.add(EdgeType.DIRECTED, sanFrancisco, washingtonDC, 337.0)
        graph.add(EdgeType.DIRECTED, washingtonDC, seattle, 277.0)
        graph.add(EdgeType.DIRECTED, sanFrancisco, seattle, 218.0)
        graph.add(EdgeType.DIRECTED, austinTexas, sanFrancisco, 297.0)
    }

    @Test
    fun breadthFirstSearch() {
        graph.breadthFirstSearch(singapore).map { it.data } shouldBe listOf(
            "Singapore", "Hong Kong", "Tokyo", "San Francisco", "Detroit", "Washington DC", "Seattle", "Austin Texas"
        )
    }

    @Test
    fun breadthFirstSearchRecursive() {
        graph.breadthFirstSearchRecursive(singapore).map { it.data } shouldBe listOf(
            "Singapore", "Hong Kong", "Tokyo", "San Francisco", "Detroit", "Washington DC", "Seattle", "Austin Texas"
        )
    }
}
