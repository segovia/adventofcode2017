package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import java.lang.RuntimeException
import java.util.ArrayDeque
import java.util.Deque
import java.util.Queue

class D06_UniversalOrbitMap {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    @Test
    fun test() {
        assertThat(part1(fileInputs[0]), `is`(42))
        assertThat(part1(fileInputs[1]), `is`(150150))
        assertThat(part2(fileInputs[2]), `is`(4))
        assertThat(part2(fileInputs[1]), `is`(352))
    }

    private fun String.toDirectedEdgeMap() = this.split(Regex("\\s+"))
            .map { it.split(")") }
            .groupBy({ it[0] }, { it[1] })

    private fun part1(input: String) = dfsDepthSum(input.toDirectedEdgeMap(), 0, "COM")

    private fun dfsDepthSum(edgeMap: Map<String, List<String>>, depth: Int, vertex: String): Int =
            depth + (edgeMap[vertex]?.map { dfsDepthSum(edgeMap, depth + 1, it) }?.sum() ?: 0)

    private fun String.toUndirectedEdgeMap() = this.toDirectedEdgeMap().entries
            .flatMap { entry -> entry.value.flatMap { listOf(Pair(entry.key, it), Pair(it, entry.key)) } }
            .groupBy({ it.first }, { it.second })

    private fun part2(input: String) = distBfs(input.toUndirectedEdgeMap(), "YOU", "SAN") - 2

    private data class DistEntry(val node: String, val dist: Int)

    private fun distBfs(edgeMap: Map<String, List<String>>, source: String, dest: String): Int {
        val queue: Queue<DistEntry> = ArrayDeque()
        queue.add(DistEntry(source, 0))
        val visited = mutableSetOf<String>()
        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            if (cur.node == dest) return cur.dist
            if (!visited.add(cur.node)) continue
            edgeMap[cur.node]?.let { adjList -> adjList.map { DistEntry(it, cur.dist + 1) }.forEach { queue.add(it) } }
        }
        throw RuntimeException("Could not find path")
    }
}