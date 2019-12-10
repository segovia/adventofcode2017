package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class D10_MonitoringStation {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)

    @Test
    fun test() {
        assertThat(part1(fileInputs[0]), `is`(8))
        assertThat(part1(fileInputs[1]), `is`(33))
        assertThat(part1(fileInputs[2]), `is`(35))
        assertThat(part1(fileInputs[3]), `is`(41))
        assertThat(part1(fileInputs[4]), `is`(210))
        assertThat(part1(fileInputs[5]), `is`(282))
        assertThat(part2(fileInputs[4]), `is`(802))
        assertThat(part2(fileInputs[5]), `is`(1008))
    }

    private data class Pos(val x: Int, val y: Int)

    private fun part1(input: String): Int {
        val meteors = extractMeteors(input)
        val posAnglesMap = makePosToAnglesMap(meteors)
        return posAnglesMap.values.map { it.size }.max() ?: -1
    }

    private fun part2(input: String): Int {
        val meteors = extractMeteors(input)
        val pos = makePosToAnglesMap(meteors).entries.maxBy { it.value.size }?.key!!
        val twoHundredthPos = meteors.filter { pos != it }
                .map { getAngle(pos, it) to it }
                .groupBy({ it.first }, { it.second })
                .mapValues { entry -> entry.value.sortedBy { manDist(pos, it) } }
                .flatMap { it.value.mapIndexed { index, pos -> Pair(it.key + index * 360, pos) } }
                .sortedBy { it.first }
                .map { it.second }[199]
        return twoHundredthPos.x * 100 + twoHundredthPos.y
    }

    private fun manDist(a: Pos, b: Pos) = abs(b.y - a.y) + abs(b.x - a.x)

    private fun makePosToAnglesMap(meteors: Set<Pos>) = meteors
            .flatMap { a -> meteors.filter { a != it }.map { a to getAngle(a, it) } }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }

    private fun getAngle(a: Pos, b: Pos) =
            ((PI - atan2((b.x - a.x).toDouble(), (b.y - a.y).toDouble())) * 1800 / PI).toInt() / 10.0

    private fun extractMeteors(input: String) = input.split(Regex("\\s+"))
            .mapIndexed { yIdx, line ->
                line.toCharArray()
                        .mapIndexed { xIdx, c -> if (c == '#') Pos(xIdx, yIdx) else null }
                        .filterNotNull()
                        .toList()
            }
            .flatten()
            .toSet()

}