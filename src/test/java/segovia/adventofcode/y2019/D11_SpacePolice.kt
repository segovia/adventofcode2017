package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import java.lang.RuntimeException

class D11_SpacePolice {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    private fun String.toLongArray() = this.split(",").map(String::toLong).toLongArray()

    @Test
    fun test() {
        assertThat(part1(), `is`(2160))
        assertThat(part2(), `is`(249))
    }

    private fun part1() = simulatePainting(IntCodeComputer(fileInputs[0].toLongArray()))
    private fun part2() = simulatePainting(IntCodeComputer(fileInputs[0].toLongArray()), mutableMapOf(Pos(0, 0) to 1))

    private fun simulatePainting(program: IntCodeComputer, panels: MutableMap<Pos, Int> = mutableMapOf()): Int {
        val robot = Robot()
        while (!program.finished) {
            program.input.add(panels[robot.pos]?.toLong() ?: 0)
            program.run()
            panels[robot.pos] = program.output[0].toInt()
            robot.move(program.output[1].toInt())
            program.output.clear()
        }
//        print(panels)
        return panels.size
    }

    private data class Pos(val x: Int, val y: Int)
    private data class Robot(var pos: Pos = Pos(0, 0), var direction: Char = '^') {
        fun move(turnDirection: Int) {
            when {
                turnDirection == 0 && direction == '>' || turnDirection == 1 && direction == '<' -> moveTo(Pos(pos.x, pos.y - 1), '^')
                turnDirection == 0 && direction == 'v' || turnDirection == 1 && direction == '^' -> moveTo(Pos(pos.x + 1, pos.y), '>')
                turnDirection == 0 && direction == '<' || turnDirection == 1 && direction == '>' -> moveTo(Pos(pos.x, pos.y + 1), 'v')
                turnDirection == 0 && direction == '^' || turnDirection == 1 && direction == 'v' -> moveTo(Pos(pos.x - 1, pos.y), '<')
            }
        }

        private fun moveTo(newPos: Pos, newDirection: Char) {
            pos = newPos
            direction = newDirection
        }
    }

    // Used to check result of part2
    @Suppress("unused")
    private fun print(panels: Map<Pos, Int>) {
        val minX = panels.keys.map { it.x }.min()!!
        val maxX = panels.keys.map { it.x }.max()!!
        val minY = panels.keys.map { it.y }.min()!!
        val maxY = panels.keys.map { it.y }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(if (panels[Pos(x, y)] == 1) '#' else ' ')
            }
            println()
        }
        println()
    }
}

