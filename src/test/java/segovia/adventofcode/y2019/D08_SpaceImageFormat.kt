package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils

class D08_SpaceImageFormat {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toIntArray() = this.toCharArray().map { c -> c - '0' }.toIntArray()

    @Test
    fun test() {
        assertThat(part1(fileInputs[0].toIntArray()), `is`(1965))
        assertThat(part2(fileInputs[0].toIntArray()), `is`(55))
    }

    private val width = 25
    private val height = 6
    private val pixelsPerLayer = width * height

    private fun countInts(layer: Int, arr: IntArray): Array<Int> {
        val counts = arrayOf(0, 0, 0)
        for (i in 0 until pixelsPerLayer) counts[arr[layer * pixelsPerLayer + i]] += 1
        return counts
    }

    private fun part1(arr: IntArray): Int {
        val counts = (0 until arr.size / pixelsPerLayer).map { it to countInts(it, arr) }.minBy { it.second[0] }!!.second
        return counts[1] * counts[2]
    }

    private fun part2(arr: IntArray): Int {
        val final = (0 until pixelsPerLayer).map { index ->
            (index until arr.size step pixelsPerLayer).map { arr[it] }.find { it != 2 } ?: 2
        }
        for (i in 0 until pixelsPerLayer) {
            val pixel = final[i]
            print(if (pixel == 1) '#' else ' ')
            if (i % width == width - 1) println()
        }
        return final.sum()
    }

}