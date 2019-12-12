package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import kotlin.math.abs

class D12_TheNBodyProblem {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)


    @Test
    fun test() {
        assertThat(part1(fileInputs[0].toMoons(), 10), `is`(179L))
        assertThat(part1(fileInputs[1].toMoons(), 100), `is`(1940L))
        assertThat(part1(fileInputs[2].toMoons(), 1000), `is`(7202L))
        assertThat(part2(fileInputs[0].toMoons()), `is`(2772L))
        assertThat(part2(fileInputs[1].toMoons()), `is`(4686774924L))
        assertThat(part2(fileInputs[2].toMoons()), `is`(537881600740876L))
    }

    private fun String.toMoons() = split('\n')
            .map { Regex("<x=(.+), y=(.+), z=(.+)>").find(it)?.groupValues!! }
            .map { groupVals -> Moon((1..3).map { groupVals[it].toLong() }.toMutableList()) }

    private data class Moon(val pos: MutableList<Long>, val vel: MutableList<Long> = mutableListOf(0, 0, 0)) {
        fun energy() = pos.map(::abs).sum() * vel.map(::abs).sum()
    }

    private fun part1(moons: List<Moon>, steps: Int): Long {
        repeat(steps) { doStep(moons) }
        return energy(moons)
    }

    private fun energy(moons: List<Moon>) = moons.map { it.energy() }.sum()

    private fun doStep(moons: List<Moon>) {
        applyGravity(moons)
        applyVelocity(moons)
    }

    private fun applyVelocity(moons: List<Moon>) {
        moons.forEach { moon -> (0..2).forEach { moon.pos[it] += moon.vel[it] } }
    }

    private fun applyGravity(moons: List<Moon>) {
        for (i in 0 until moons.size - 1) {
            for (j in i + 1 until moons.size) {
                for (k in 0..2) {
                    val a = moons[i].pos[k]
                    val b = moons[j].pos[k]
                    if (a > b) {
                        moons[i].vel[k] -= 1L
                        moons[j].vel[k] += 1L
                    } else if (a < b) {
                        moons[i].vel[k] += 1L
                        moons[j].vel[k] -= 1L
                    }
                }
            }
        }
    }

    private fun part2(moons: List<Moon>): Long {
        val dim1 = doStep(moons.map { it.pos[0] })
        val dim2 = doStep(moons.map { it.pos[1] })
        val dim3 = doStep(moons.map { it.pos[2] })
        return lcm(lcm(dim1, dim2), dim3)
    }

    private fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

    private fun gcd(a: Long, b: Long): Long {
        if (a < b) return gcd(b, a)
        val r = a % b
        return if (r == 0L) b else gcd(b, r)
    }

    private fun doStep(startPos: List<Long>): Long {
        val startVel = listOf(0L, 0L, 0L, 0L)
        val curPos = startPos.toMutableList()
        val curVel = startVel.toMutableList()
        for (step in 0 until Long.MAX_VALUE) {
            if (step != 0L && curPos == startPos && curVel == startVel) return step
            applyGravity(curPos, curVel)
            applyVelocity(curPos, curVel)
        }
        return -1
    }

    private fun applyGravity(curPos: MutableList<Long>, curVel: MutableList<Long>) {
        for (i in 0..2) {
            for (j in i + 1..3) {
                val a = curPos[i]
                val b = curPos[j]
                if (a > b) {
                    curVel[i] -= 1L
                    curVel[j] += 1L
                } else if (a < b) {
                    curVel[i] += 1L
                    curVel[j] -= 1L
                }
            }
        }
    }

    private fun applyVelocity(curPos: MutableList<Long>, curVel: MutableList<Long>) = (0..3).forEach { curPos[it] += curVel[it] }

}

