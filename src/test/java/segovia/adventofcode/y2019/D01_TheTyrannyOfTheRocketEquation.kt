package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import segovia.adventofcode.Utils
import segovia.adventofcode.toListOfLong

class D01_TheTyrannyOfTheRocketEquation {

    private val fileInputs = Utils.getInputsFromFiles(this.javaClass)
    @Test
    fun testPart1() {
        assertThat(calcFuel(12), `is`(2L))
        assertThat(calcFuel(14), `is`(2L))
        assertThat(calcFuel(1969), `is`(654L))
        assertThat(calcFuel(100756), `is`(33583L))
        assertThat(part1(fileInputs[0]), `is`(34241L))
        assertThat(part1(fileInputs[1]), `is`(3426455L))
    }

    private fun calcFuel(mass: Long) = mass / 3 - 2
    private fun part1(input: String) = input.toListOfLong().map(::calcFuel).sum()

    @Test
    fun testPart2() {
        assertThat(recurseCalcFuel(12), `is`(2L))
        assertThat(recurseCalcFuel(1969), `is`(966L))
        assertThat(recurseCalcFuel(100756), `is`(50346L))
        assertThat(part2(fileInputs[0]), `is`(51316L))
        assertThat(part2(fileInputs[1]), `is`(5136807L))
    }

    private fun recurseCalcFuel(mass: Long):Long {
        val fuel = calcFuel(mass)
        return if (fuel > 0) fuel + recurseCalcFuel(fuel) else 0
    }
    private fun part2(input: String) = input.toListOfLong().map(::recurseCalcFuel).sum()
}