package segovia.adventofcode.y2019

import java.util.*

class IntCodeComputer(program: LongArray, val input: Queue<Long> = ArrayDeque()) {
    val memory = program.copyOf(program.size + 1000)
    val output: MutableList<Long> = mutableListOf()
    var finished: Boolean = false
    private var relativeBase: Int = 0
    private var offset: Int = 0

    fun run(): IntCodeComputer {
        loop@while (true) {
            val op = Op(memory[offset].toInt())
            when (op.code) {
                1, 2 -> doAddOrMul(op)
                3 -> if (input.isNotEmpty()) readInput(op) else return this
                4 -> writeOutput(op)
                5, 6 -> jumpTo(op)
                7, 8 -> compare(op)
                9 -> adjustRelativeBase(op)
                99 -> break@loop
                else -> throw Exception("Unknown op code ${op.code}")
            }
        }
        finished = true
        return this
    }

    private fun doAddOrMul(op: Op) {
        val (a, b) = get2Params(op)
        setVal(offset + 3, op.param3Mode, if (op.code == 1) a + b else a * b)
        offset += 4
    }

    private fun readInput(op: Op) {
        setVal(offset + 1, op.param1Mode, input.poll())
        offset += 2
    }

    private fun writeOutput(op: Op) {
        output.add(get1Param(op))
        offset += 2
    }

    private fun jumpTo(op: Op) {
        val (a, b) = get2Params(op)
        offset = if (op.code == 5 && a != 0L || op.code == 6 && a == 0L) b.toInt()
        else offset + 3
    }

    private fun compare(op: Op) {
        val (a, b) = get2Params(op)
        setVal(offset + 3, op.param3Mode, if (op.code == 7 && a < b || op.code == 8 && a == b) 1 else 0)
        offset += 4
    }

    private fun adjustRelativeBase(op: Op) {
        relativeBase += get1Param(op).toInt()
        offset += 2
    }

    private fun get1Param(op: Op) = getVal(offset + 1, op.param1Mode)
    private fun get2Params(op: Op) = Pair(get1Param(op), getVal(offset + 2, op.param2Mode))

    private fun getVal(idx: Int, mode: Int) = when (mode) {
        1 -> memory[idx]
        2 -> memory[getRelativeIdx(idx)]
        else -> memory[memory[idx].toInt()]
    }

    private fun setVal(idx: Int, mode: Int, v: Long) {
        if (mode == 2) memory[getRelativeIdx(idx)] = v
        else memory[memory[idx].toInt()] = v
    }

    private fun getRelativeIdx(idx: Int) = relativeBase + memory[idx].toInt()

    private class Op(op: Int) {
        val code = op % 100
        val param1Mode = (op / 100) % 10
        val param2Mode = (op / 1000) % 10
        val param3Mode = (op / 10000) % 10
    }
}