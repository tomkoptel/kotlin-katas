package com.sample.tom.ds.graph

import com.sample.tom.ds.graph.GraphSearch.breadthFirstSearch
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GraphSearchTest {
    @Test
    fun breadthFirstSearch() {
        val graph = AdjacencyList<String>()

        val singapore = graph.createVertex("Singapore")
        val tokyo = graph.createVertex("Tokyo")
        val hongKong = graph.createVertex("Hong Kong")
        val detroit = graph.createVertex("Detroit")
        val sanFrancisco = graph.createVertex("San Francisco")
        val washingtonDC = graph.createVertex("Washington DC")
        val austinTexas = graph.createVertex("Austin Texas")
        val seattle = graph.createVertex("Seattle")

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

        graph.breadthFirstSearch(singapore).map { it.data } shouldBe listOf(
            "Singapore", "Hong Kong", "Tokyo", "San Francisco", "Detroit", "Washington DC", "Seattle", "Austin Texas"
        )
    }
}
