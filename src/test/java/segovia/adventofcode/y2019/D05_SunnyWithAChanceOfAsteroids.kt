package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import java.lang.Exception

class D05_SunnyWithAChanceOfAsteroids {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toIntArray() = this.split(",").map(String::toInt).toIntArray()

    @Test
    fun test() {
        assertThat(runProgram(fileInputs[0].toIntArray(), 1), `is`(7692125))

        val equal8Pos = intArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        assertThat(runProgram(equal8Pos.copyOf(), 8), `is`(1))
        assertThat(runProgram(equal8Pos.copyOf(), 7), `is`(0))

        val lessThan8Pos = intArrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        assertThat(runProgram(lessThan8Pos.copyOf(), 8), `is`(0))
        assertThat(runProgram(lessThan8Pos.copyOf(), 7), `is`(1))

        val equal8Imm = intArrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
        assertThat(runProgram(equal8Imm.copyOf(), 8), `is`(1))
        assertThat(runProgram(equal8Imm.copyOf(), 7), `is`(0))

        val lessThan8Imm = intArrayOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
        assertThat(runProgram(lessThan8Imm.copyOf(), 8), `is`(0))
        assertThat(runProgram(lessThan8Imm.copyOf(), 7), `is`(1))

        val jumpTest1 = intArrayOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        assertThat(runProgram(jumpTest1.copyOf(), 0), `is`(0))
        assertThat(runProgram(jumpTest1.copyOf(), 7), `is`(1))

        val jumpTest2 = intArrayOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        assertThat(runProgram(jumpTest2.copyOf(), 0), `is`(0))
        assertThat(runProgram(jumpTest2.copyOf(), 7), `is`(1))

        assertThat(runProgram(fileInputs[1].toIntArray(), 1), `is`(999))
        assertThat(runProgram(fileInputs[1].toIntArray(), 8), `is`(1000))
        assertThat(runProgram(fileInputs[1].toIntArray(), 20), `is`(1001))

        assertThat(runProgram(fileInputs[0].toIntArray(), 5), `is`(14340395))
    }

    private class Op(op: Int) {
        val code = op % 100
        val param1IsImmediate = (op / 100) % 10 == 1
        val param2IsImmediate = (op / 1000) % 10 == 1
    }

    private fun getVal(arr: IntArray, idx: Int, immediate: Boolean) = if (immediate) arr[idx] else arr[arr[idx]]
    private fun get2Params(arr: IntArray, idx: Int, op: Op) =
            Pair(getVal(arr, idx + 1, op.param1IsImmediate),
                    getVal(arr, idx + 2, op.param2IsImmediate))

    private fun doOps(arr: IntArray, input: Int): Int {
        var offset = 0
        var output = -1
        while (true) {
            val op = Op(arr[offset])
            if (op.code == 99) break
            when (op.code) {
                1, 2 -> {
                    val (a, b) = get2Params(arr, offset, op)
                    arr[arr[offset + 3]] = if (op.code == 1) a + b else a * b
                    offset += 4
                }
                3 -> {
                    arr[arr[offset + 1]] = input
                    offset += 2
                }
                4 -> {
                    output = getVal(arr, offset + 1, op.param1IsImmediate)
                    offset += 2
                }
                5, 6 -> {
                    val (a, b) = get2Params(arr, offset, op)
                    offset = if (op.code == 5 && a != 0 || op.code == 6 && a == 0) b
                    else offset + 3
                }
                7, 8 -> {
                    val (a, b) = get2Params(arr, offset, op)
                    arr[arr[offset + 3]] = if (op.code == 7 && a < b || op.code == 8 && a == b) 1 else 0
                    offset += 4
                }
                else -> throw Exception("Unknown op code ${op.code}")
            }
        }
        return output
    }

    private fun runProgram(program: IntArray, input: Int): Int {
        return doOps(program, input)
    }
}