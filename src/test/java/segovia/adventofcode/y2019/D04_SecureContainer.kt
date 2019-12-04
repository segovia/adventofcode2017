package segovia.adventofcode.y2019

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class D04_SecureContainer {

    @Test
    fun test() {
        assertThat(isValid(111111), `is`(true))
        assertThat(isValid(223450), `is`(false))
        assertThat(isValid(123789), `is`(false))
        assertThat(isValid(123389), `is`(true))
        assertThat(part1(183564, 657474), `is`(1610))

        assertThat(isValid(112233, true), `is`(true))
        assertThat(isValid(123444, true), `is`(false))
        assertThat(isValid(111122, true), `is`(true))
        assertThat(part2(183564, 657474), `is`(1104))
    }

    private fun isValid(n: Int, requireAtLeastOneDouble: Boolean = false): Boolean {
        var aux = n
        var prevDigit = 10
        var hadRepeat = false
        var doubledDigit = -10 // negative means no double
        while (aux > 0) {
            val digit = aux % 10
            aux /= 10
            if (prevDigit < digit) return false
            if (prevDigit == digit) {
                hadRepeat = true
                if (doubledDigit < 0 && doubledDigit != -digit) doubledDigit = digit
                else if (doubledDigit > 0 && doubledDigit == digit) doubledDigit = -digit
            }
            prevDigit = digit
        }
        return hadRepeat && (!requireAtLeastOneDouble || doubledDigit > 0)
    }

    private fun count(start: Int, end: Int, requireAtLeastOneDouble: Boolean = false) = (start..end)
            .filter { isValid(it, requireAtLeastOneDouble) }
            .count()

    private fun part1(start: Int, end: Int) = count(start, end)
    private fun part2(start: Int, end: Int) = count(start, end, true)

}