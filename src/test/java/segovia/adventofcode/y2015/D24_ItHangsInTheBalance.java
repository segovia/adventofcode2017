package segovia.adventofcode.y2015;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D24_ItHangsInTheBalance {

    @Test
    public void test() throws Exception {
        int[] exampleInput = {1, 2, 3, 4, 5, 7, 8, 9, 10, 11};
        int[] testInput = {1, 2, 3, 5, 7, 13, 17, 19, 23, 29, 31, 37, 41, 43, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
                101, 103, 107, 109, 113};
        assertThat(run(exampleInput, 3), is(99L));
        assertThat(run(testInput, 3), is(10723906903L));
        assertThat(run(exampleInput, 4), is(44L));
        assertThat(run(testInput, 4), is(74850409L));
    }

    private long run(int[] input, int groups) throws Exception {
        Arrays.sort(input);
        int totalSum = 0;
        for (int i : input) totalSum += i;
        int partSum = totalSum / groups;

        for (int needed = 1; needed < input.length; needed++) {
            long val = recurse(0, needed, 0, input, partSum, 0, 1);
            if (val != Long.MAX_VALUE) return val;
        }
        return -1;
    }

    private long recurse(long mask, int needed, int curIdx, int[] input, int size, int curSum, long curProduct) {
        if (curSum > size) return Long.MAX_VALUE;
        if (needed == 0) {
            if (curProduct == 1 || curSum != size) return Long.MAX_VALUE;
            return curProduct;
        }

        long best = Long.MAX_VALUE;
        for (int i = curIdx; i < input.length - (needed - 1) && curProduct < best; i++) {
            best = Math.min(best,
                            recurse(mask | 1 << i,
                                    needed - 1,
                                    i + 1,
                                    input,
                                    size,
                                    curSum + input[i],
                                    curProduct * input[i]));
        }
        return best;
    }
}