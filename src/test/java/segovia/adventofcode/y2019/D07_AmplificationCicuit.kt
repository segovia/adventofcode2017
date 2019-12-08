package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import segovia.adventofcode.Utils.swap
import java.util.*
import kotlin.math.max

class D07_AmplificationCicuit {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toIntArray() = this.split(",").map(String::toInt).toIntArray()

    @Test
    fun test() {
        assertThat(simulateCircuit(fileInputs[0].toIntArray(), intArrayOf(4, 3, 2, 1, 0)), `is`(43210))
        assertThat(simulateCircuit(fileInputs[1].toIntArray(), intArrayOf(0, 1, 2, 3, 4)), `is`(54321))
        assertThat(simulateCircuit(fileInputs[2].toIntArray(), intArrayOf(1, 0, 4, 3, 2)), `is`(65210))
        assertThat(findMax(fileInputs[3].toIntArray(), intArrayOf(0, 1, 2, 3, 4)), `is`(67023))

        assertThat(simulateCircuit(fileInputs[4].toIntArray(), intArrayOf(9, 8, 7, 6, 5)), `is`(139629729))
        assertThat(simulateCircuit(fileInputs[5].toIntArray(), intArrayOf(9, 7, 8, 5, 6)), `is`(18216))
        assertThat(findMax(fileInputs[3].toIntArray(), intArrayOf(5, 6, 7, 8, 9)), `is`(7818398))
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

    private data class ProgramState(val program: IntArray, var offset: Int = 0, val input: Queue<Int> = ArrayDeque(), var output: Int = 0, var finished: Boolean = false)

    private fun doOps(s: ProgramState) {
        while (true) {
            val op = Op(s.program[s.offset])
            if (op.code == 99) break
            when (op.code) {
                1, 2 -> {
                    val (a, b) = get2Params(s.program, s.offset, op)
                    s.program[s.program[s.offset + 3]] = if (op.code == 1) a + b else a * b
                    s.offset += 4
                }
                3 -> {
                    if (s.input.isEmpty()) return
                    s.program[s.program[s.offset + 1]] = s.input.poll()
                    s.offset += 2
                }
                4 -> {
                    s.output = getVal(s.program, s.offset + 1, op.param1IsImmediate)
                    s.offset += 2
                }
                5, 6 -> {
                    val (a, b) = get2Params(s.program, s.offset, op)
                    s.offset = if (op.code == 5 && a != 0 || op.code == 6 && a == 0) b
                    else s.offset + 3
                }
                7, 8 -> {
                    val (a, b) = get2Params(s.program, s.offset, op)
                    s.program[s.program[s.offset + 3]] = if (op.code == 7 && a < b || op.code == 8 && a == b) 1 else 0
                    s.offset += 4
                }
                else -> throw Exception("Unknown op code ${op.code}")
            }
        }
        s.finished = true
    }

    private fun simulateCircuit(program: IntArray, phaseSeq: IntArray): Int {
        val states = phaseSeq.map { val state = ProgramState(program.copyOf()); state.input.add(it); state }
        states[0].input.add(0)
        var curPhase = 0
        while (!states.last().finished) {
            doOps(states[curPhase])
            states[(curPhase + 1) % states.size].input.add(states[curPhase].output)
            curPhase = if (curPhase + 1 == states.size) 0 else curPhase + 1
        }
        return states.last().output
    }

    private fun findMax(program: IntArray, phaseSeq: IntArray, depth: Int): Int {
        if (depth == phaseSeq.size) {
            return simulateCircuit(program, phaseSeq)
        }
        var max = Int.MIN_VALUE
        for (i in depth until phaseSeq.size) {
            swap(phaseSeq, i, depth)
            max = max(max, findMax(program, phaseSeq, depth + 1))
            swap(phaseSeq, i, depth)
        }
        return max
    }

    private fun findMax(program: IntArray, phaseSeq: IntArray) = findMax(program, phaseSeq, 0)

}