package segovia.adventofcode.y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D25_ClockSignal_2 {

    @Test
    public void test() throws Exception {
        assertThat(run(1), is("101011111001"));
        assertThat(run(2), is("011011111001"));
        assertThat(run(3), is("111011111001"));

        // answer from code below
        assertThat(run(0xaaa - 2548 /* 182 */), is("010101010101"));
    }

    private String run(int aVal) throws Exception {
        // the assembunny code basically outputs the binary representation of (aVal + 2548) in reversed order
        // so basically we want (aVal + 2548) to be 0xaaa because 0xaaa == 0b101010101010
        // outer loop removed
        return new StringBuilder(Integer.toBinaryString(aVal + 2548)).reverse().toString();
    }
}