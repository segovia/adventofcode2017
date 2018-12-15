package segovia.adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D15_DuelingGenerators_2 {

    @Test
    public void test() throws IOException {
        assertThat(run(65, 8921), is(309));
        assertThat(run(116, 299), is(298));
    }

    private long A_MULT = 16807;
    private long B_MULT = 48271;

    private int run(int a, int b) {
        long mask16Bit = (1L << 16) - 1L;
        long curA = a, curB = b;
        int matchCount = 0;
        for (int i = 0; i < 5_000_000; i++) {
            curA = nextVal(curA, A_MULT, 4);
            curB = nextVal(curB, B_MULT, 8);
            if ((curA & mask16Bit) == (curB & mask16Bit)) ++matchCount;
        }
        return matchCount;
    }

    private long nextVal(long n, long mult, int criteria) {
        long next = n;
        do {
            next = (next * mult) % ((long) Integer.MAX_VALUE);
        } while (next % criteria != 0);
        return next;
    }
}
