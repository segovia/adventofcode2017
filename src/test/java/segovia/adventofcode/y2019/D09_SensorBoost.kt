package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import java.util.*

class D09_SensorBoost {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        assertThat(solution(longArrayOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)),
                `is`(mutableListOf<Long>(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)))
        assertThat(solution(longArrayOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0)), `is`(mutableListOf(1219070632396864)))
        assertThat(solution(longArrayOf(104, 1125899906842624, 99)), `is`(mutableListOf(1125899906842624)))
        assertThat(solution(fileInputs[0].toLongArray(), listOf(1)), `is`(mutableListOf(2465411646)))
        assertThat(solution(fileInputs[0].toLongArray(), listOf(2)), `is`(mutableListOf<Long>(69781)))
    }

    private fun solution(program: LongArray, input: List<Long> = listOf()) =
            IntCodeComputer(program, ArrayDeque(input)).run().output


}