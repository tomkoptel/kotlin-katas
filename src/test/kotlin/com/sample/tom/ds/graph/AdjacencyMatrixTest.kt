package com.sample.tom.ds.graph

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

class AdjacencyMatrixTest {
    @Test
    fun airports() {
        val graph = AdjacencyMatrix<String>()

        val singapore = graph.createVertex("Singapore")
        val tokyo = graph.createVertex("Tokyo")
        val hongKong = graph.createVertex("Hong Kong")
        val detroit = graph.createVertex("Detroit")
        val sanFrancisco = graph.createVertex("San Francisco")
        val washingtonDC = graph.createVertex("Washington DC")
        val austinTexas = graph.createVertex("Austin Texas")
        val seattle = graph.createVertex("Seattle")

        graph.add(EdgeType.UNDIRECTED, singapore, hongKong, 300.0)
        graph.add(EdgeType.UNDIRECTED, singapore, tokyo, 500.0)
        graph.add(EdgeType.UNDIRECTED, hongKong, tokyo, 250.0)
        graph.add(EdgeType.UNDIRECTED, tokyo, detroit, 450.0)
        graph.add(EdgeType.UNDIRECTED, tokyo, washingtonDC, 300.0)
        graph.add(EdgeType.UNDIRECTED, hongKong, sanFrancisco, 600.0)
        graph.add(EdgeType.UNDIRECTED, detroit, austinTexas, 50.0)
        graph.add(EdgeType.UNDIRECTED, austinTexas, washingtonDC, 292.0)
        graph.add(EdgeType.UNDIRECTED, sanFrancisco, washingtonDC, 337.0)
        graph.add(EdgeType.UNDIRECTED, washingtonDC, seattle, 277.0)
        graph.add(EdgeType.UNDIRECTED, sanFrancisco, seattle, 218.0)
        graph.add(EdgeType.UNDIRECTED, austinTexas, sanFrancisco, 297.0)

        val graphString = graph.toString()
        graphString shouldContain "0:Singapore"
        graphString shouldContain "1:Tokyo"
        graphString shouldContain "2:Hong Kong"
        graphString shouldContain "3:Detroit"
        graphString shouldContain "4:San Francisco"
        graphString shouldContain "5:Washington DC"
        graphString shouldContain "6:Austin Texas"
        graphString shouldContain "7:Seattle"
        graphString shouldContain "ø\t\t500.0\t300.0\tø\t\tø\t\tø\t\tø\t\tø"
        graphString shouldContain "500.0\tø\t\t250.0\t450.0\tø\t\t300.0\tø\t\tø"
        graphString shouldContain "300.0\t250.0\tø\t\tø\t\t600.0\tø\t\tø\t\tø"
        graphString shouldContain "ø\t\t450.0\tø\t\tø\t\tø\t\tø\t\t50.0\tø"
        graphString shouldContain "ø\t\tø\t\t600.0\tø\t\tø\t\t337.0\t297.0\t218.0"
        graphString shouldContain "ø\t\t300.0\tø\t\tø\t\t337.0\tø\t\t292.0\t277.0"
        graphString shouldContain "ø\t\tø\t\tø\t\t50.0\t297.0\t292.0\tø\t\tø"
        graphString shouldContain "ø\t\tø\t\tø\t\tø\t\t218.0\t277.0\tø\t\tø"
    }
}
