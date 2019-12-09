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
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        assertThat(simulateCircuit(fileInputs[0].toLongArray(), longArrayOf(4, 3, 2, 1, 0)), `is`(43210L))
        assertThat(simulateCircuit(fileInputs[1].toLongArray(), longArrayOf(0, 1, 2, 3, 4)), `is`(54321L))
        assertThat(simulateCircuit(fileInputs[2].toLongArray(), longArrayOf(1, 0, 4, 3, 2)), `is`(65210L))
        assertThat(findMax(fileInputs[3].toLongArray(), longArrayOf(0, 1, 2, 3, 4)), `is`(67023L))

        assertThat(simulateCircuit(fileInputs[4].toLongArray(), longArrayOf(9, 8, 7, 6, 5)), `is`(139629729L))
        assertThat(simulateCircuit(fileInputs[5].toLongArray(), longArrayOf(9, 7, 8, 5, 6)), `is`(18216L))
        assertThat(findMax(fileInputs[3].toLongArray(), longArrayOf(5, 6, 7, 8, 9)), `is`(7818398L))
    }

    private fun simulateCircuit(program: LongArray, phaseSeq: LongArray): Long {
        val amp = phaseSeq.map { IntCodeComputer(program.copyOf(), ArrayDeque(listOf(it))) }
        amp[0].input.add(0)
        var curPhase = 0
        while (!amp.last().finished) {
            amp[curPhase].run()
            amp[(curPhase + 1) % amp.size].input.add(amp[curPhase].output.last())
            curPhase = if (curPhase + 1 == amp.size) 0 else curPhase + 1
        }
        return amp.last().output.last()
    }

    private fun findMax(program: LongArray, phaseSeq: LongArray, depth: Int): Long {
        if (depth == phaseSeq.size) {
            return simulateCircuit(program, phaseSeq)
        }
        var max = Long.MIN_VALUE
        for (i in depth until phaseSeq.size) {
            swap(phaseSeq, i, depth)
            max = max(max, findMax(program, phaseSeq, depth + 1))
            swap(phaseSeq, i, depth)
        }
        return max
    }

    private fun findMax(program: LongArray, phaseSeq: LongArray) = findMax(program, phaseSeq, 0)

}