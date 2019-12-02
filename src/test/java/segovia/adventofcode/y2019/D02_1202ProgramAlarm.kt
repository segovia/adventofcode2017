package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils

class D02_1202ProgramAlarm {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toIntArray() = this.split(",").map(String::toInt).toIntArray()

    @Test
    fun test() {
        testOps(intArrayOf(1, 0, 0, 0, 99), intArrayOf(2, 0, 0, 0, 99))
        testOps(intArrayOf(2, 3, 0, 3, 99), intArrayOf(2, 3, 0, 6, 99))
        testOps(intArrayOf(2, 4, 4, 5, 99, 0), intArrayOf(2, 4, 4, 5, 99, 9801))
        testOps(intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99), intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99))
        testOps(intArrayOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50), intArrayOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))
        assertThat(part1(fileInputs[0]), `is`(4576384))
        assertThat(part2(fileInputs[0]), `is`(5398))
    }

    private fun testOps(input: IntArray, output: IntArray) {
        doOps(input)
        assertThat(input, `is`(output))
    }

    private fun doOp(arr: IntArray, offset: Int): Boolean {
        val op = arr[offset]
        if (op == 99) return false
        val a = arr[arr[offset + 1]]
        val b = arr[arr[offset + 2]]
        arr[arr[offset + 3]] = if (op == 1) a + b else a * b
        return true
    }

    private fun doOps(arr: IntArray) {
        var offset = 0
        while (doOp(arr, offset)) offset += 4
    }

    private fun runProgram(program: IntArray, noun: Int, verb: Int): Int {
        program[1] = noun
        program[2] = verb
        doOps(program)
        return program[0]
    }

    private fun part1(input: String) = runProgram(input.toIntArray(), 12, 2)

    private fun part2(input: String): Int {
        val program = input.toIntArray()
        for (i in 0..99)
            for (j in 0..99)
                if (runProgram(program.copyOf(), i, j) == 19690720)
                    return 100 * i + j
        throw RuntimeException("Could not find answer")
    }

}