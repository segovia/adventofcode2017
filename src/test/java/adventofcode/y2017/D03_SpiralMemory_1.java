package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D03_SpiralMemory_1 {


    @Test
    public void test() throws IOException {
        assertThat(run("1"), is(0L));
        assertThat(run("2"), is(1L));
        assertThat(run("12"), is(3L));
        assertThat(run("23"), is(2L));
        assertThat(run("1024"), is(31L));
        assertThat(run("277678"), is(475L));
    }

    private long run(String inputStr) {
        int input = Integer.parseInt(inputStr);
        if (input == 1) return 0;

        int nextSq = 3;
        while (nextSq * nextSq < input) nextSq += 2;

        int curVal = (nextSq - 2) * (nextSq - 2);

        for (; true; curVal += nextSq - 1) {
            if (curVal + nextSq - 1 >= input) {
                return nextSq / 2 + Math.abs(input - curVal - nextSq / 2);
            }
        }
    }
}
