package adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D20_InfiniteElvesAndInfiniteHouses_2 {

    @Test
    public void test() throws IOException {
        assertThat(run(80), is(6));
        assertThat(run(29000000), is(705600));
    }

    private int run(int input) {
        return normRun(input/10);
    }

    private int normRun(int input) {
        int[] n = new int[input+1];
        for (int i = 1; i <= input; i++) {
            for (int j = i; j < n.length && j / i <= 50; j+= i) {
                n[j] += i;
            }
            if (n[i]*11 >= input*10) return i;
        }
        return -1;
    }
}