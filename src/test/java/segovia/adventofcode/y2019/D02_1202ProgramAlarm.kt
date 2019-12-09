package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils

class D02_1202ProgramAlarm {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        testOps(longArrayOf(1, 0, 0, 0, 99), longArrayOf(2, 0, 0, 0, 99))
        testOps(longArrayOf(2, 3, 0, 3, 99), longArrayOf(2, 3, 0, 6, 99))
        testOps(longArrayOf(2, 4, 4, 5, 99, 0), longArrayOf(2, 4, 4, 5, 99, 9801))
        testOps(longArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99), longArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99))
        testOps(longArrayOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50), longArrayOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))
        assertThat(part1(fileInputs[0]), `is`(4576384L))
        assertThat(part2(fileInputs[0]), `is`(5398L))
    }

    private fun testOps(input: LongArray, output: LongArray) {
        assertThat(IntCodeComputer(input).run().memory.copyOf(output.size), `is`(output))
    }

    private fun LongArray.setInput(noun: Long, verb: Long) : LongArray {
        this[1] = noun
        this[2] = verb
        return this
    }

    private fun runProgram(program: LongArray) = IntCodeComputer(program).run().memory[0]

    private fun part1(input: String) = runProgram(input.toLongArray().setInput(12, 2))

    private fun part2(input: String): Long {
        val program = input.toLongArray()
        for (i in 0..99L)
            for (j in 0..99L)
                if (runProgram(program.copyOf().setInput(i, j)) == 19690720L)
                    return 100 * i + j
        throw RuntimeException("Could not find answer")
    }

}