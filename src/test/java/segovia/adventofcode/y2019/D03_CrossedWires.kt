package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class D03_CrossedWires {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)

    @Test
    fun test() {
        assertThat(part1(fileInputs[0]), `is`(6))
        assertThat(part1(fileInputs[1]), `is`(159))
        assertThat(part1(fileInputs[2]), `is`(135))
        assertThat(part1(fileInputs[3]), `is`(1017))

        assertThat(part2(fileInputs[0]), `is`(30))
        assertThat(part2(fileInputs[1]), `is`(610))
        assertThat(part2(fileInputs[2]), `is`(410))
        assertThat(part2(fileInputs[3]), `is`(11432))
    }

    private data class Point(val left: Int, val top: Int)
    private data class Wire(val start: Point, val end: Point, val delay: Int) {
        fun isHorizontal() = start.top == end.top
    }

    private data class Intersection(val hWire: Wire, val vWire: Wire)

    private fun makeWires(inputLine: String): List<Wire> {
        var curP = Point(0, 0)
        var delay = 0
        return inputLine.split(",").map {
            val size = it.substring(1).toInt()
            val wire = when (it[0]) {
                'U' -> Wire(curP, Point(curP.left, curP.top - size), delay)
                'D' -> Wire(curP, Point(curP.left, curP.top + size), delay)
                'L' -> Wire(curP, Point(curP.left - size, curP.top), delay)
                'R' -> Wire(curP, Point(curP.left + size, curP.top), delay)
                else -> throw Exception("Unexpected direction ${it[0]}")
            }
            curP = wire.end
            delay += size
            wire
        }
    }

    private fun getIntersection(a: Wire, b: Wire): Intersection? {
        if (a.isHorizontal() == b.isHorizontal()) return null // assuming no collinear intersections
        val hWire = if (a.isHorizontal()) a else b
        val vWire = if (hWire == b) a else b
        return if (max(vWire.start.top, vWire.end.top) < hWire.start.top
                || min(vWire.start.top, vWire.end.top) > hWire.start.top
                || max(hWire.start.left, hWire.end.left) < vWire.start.left
                || min(hWire.start.left, hWire.end.left) > vWire.start.left
                || abs(vWire.start.left) + abs(hWire.start.top) == 0) null
        else Intersection(hWire, vWire)
    }

    private fun findMinDist(input: String, distFunc: (intersection: Intersection) -> Int): Int {
        val lines = input.split(Regex("\\s+"))
        val firstWires = makeWires(lines[0])
        return makeWires(lines[1])
                .flatMap { wire -> firstWires.map { getIntersection(it, wire) } }
                .filterNotNull()
                .map(distFunc)
                .min() ?: throw Exception("No intersections found")
    }

    private fun part1(input: String) = findMinDist(input) {
        abs(it.vWire.start.left) + abs(it.hWire.start.top)
    }

    private fun part2(input: String) = findMinDist(input) {
        it.vWire.delay + abs(it.hWire.start.top - it.vWire.start.top) +
                it.hWire.delay + abs(it.vWire.start.left - it.hWire.start.left)
    }

}