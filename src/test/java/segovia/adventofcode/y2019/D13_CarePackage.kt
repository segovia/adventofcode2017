package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import kotlin.math.abs

class D13_CarePackage {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        assertThat(part1(IntCodeComputer(fileInputs[0].toLongArray())), `is`(357))
        assertThat(IntCodeComputer(fileInputs[0].toLongArray()).part2(), `is`(17468L))
    }

    private fun part1(program: IntCodeComputer) =
            program.run().output.mapIndexed { idx, v -> if (idx % 3 == 2 && v == 2L) 1 else 0 }.sum()

    private fun IntCodeComputer.part2(): Long {
        memory[0] = 2
        var score = -1L
        while (!finished) {
            run()
            var balX = -1L
            var paddleX = -1L
            (output.indices step 3).forEach {
                when {
                    output[it + 2] == 3L -> paddleX = output[it]
                    output[it + 2] == 4L -> balX = output[it]
                    output[it] == -1L -> score = output[it + 2]
                }
            }
            input.add(balX.compareTo(paddleX).toLong())
            output.clear()
        }
        return score
    }
}

