package segovia.adventofcode.y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D12_LeonardosMonorail_extra {

    @Test
    public void test() throws Exception {
        assertThat(findNthFibonacciPlus272(28), is(318083L));
        assertThat(findNthFibonacciPlus272(35), is(9227737L));
    }

    // This is a translation and optimization of the assembunny code
    private long findNthFibonacciPlus272(int nth) throws Exception {
        long prev = 1;
        long cur = 1;
        for (int i = 0; i < nth - 2; i++) {
            cur = cur + prev;
            prev = cur - prev;
        }
        return cur + 272;
    }

}