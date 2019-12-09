package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import java.util.*

class D05_SunnyWithAChanceOfAsteroids {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        assertThat(runProgram(fileInputs[0].toLongArray(), 1), `is`(7692125L))

        val equal8Pos = longArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        assertThat(runProgram(equal8Pos.copyOf(), 8), `is`(1L))
        assertThat(runProgram(equal8Pos.copyOf(), 7), `is`(0L))

        val lessThan8Pos = longArrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        assertThat(runProgram(lessThan8Pos.copyOf(), 8), `is`(0L))
        assertThat(runProgram(lessThan8Pos.copyOf(), 7), `is`(1L))

        val equal8Imm = longArrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
        assertThat(runProgram(equal8Imm.copyOf(), 8), `is`(1L))
        assertThat(runProgram(equal8Imm.copyOf(), 7), `is`(0L))

        val lessThan8Imm = longArrayOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
        assertThat(runProgram(lessThan8Imm.copyOf(), 8), `is`(0L))
        assertThat(runProgram(lessThan8Imm.copyOf(), 7), `is`(1L))

        val jumpTest1 = longArrayOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        assertThat(runProgram(jumpTest1.copyOf(), 0), `is`(0L))
        assertThat(runProgram(jumpTest1.copyOf(), 7), `is`(1L))

        val jumpTest2 = longArrayOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        assertThat(runProgram(jumpTest2.copyOf(), 0), `is`(0L))
        assertThat(runProgram(jumpTest2.copyOf(), 7), `is`(1L))

        assertThat(runProgram(fileInputs[1].toLongArray(), 1), `is`(999L))
        assertThat(runProgram(fileInputs[1].toLongArray(), 8), `is`(1000L))
        assertThat(runProgram(fileInputs[1].toLongArray(), 20), `is`(1001L))

        assertThat(runProgram(fileInputs[0].toLongArray(), 5), `is`(14340395L))
    }

    private fun runProgram(program: LongArray, input: Long) = IntCodeComputer(program, ArrayDeque(listOf(input))).run().output.last()
}