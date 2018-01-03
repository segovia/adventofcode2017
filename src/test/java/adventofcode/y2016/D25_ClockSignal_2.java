package adventofcode.y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D25_ClockSignal_2 {

    @Test
    public void test() throws Exception {
        assertThat(run(1), is("101011111001"));
        assertThat(run(2), is("011011111001"));
        assertThat(run(3), is("111011111001"));

        // answer from code below
        assertThat(run(182), is("010101010101"));
    }

    private String run(int aVal) throws Exception {
        // the sequence from back to front should be
        // 1, 2, 5, 10, 21, 42 ... 2730
        // 2730 is the first number over 2548, therefore, aVal must be 182
        StringBuilder sb = new StringBuilder();
        long n = aVal + 2548;
        // outer loop removed
        for (long i = n; i > 0; i /= 2)
            sb.append(i % 2 == 0 ? 0 : 1);
        return sb.toString();
    }
}