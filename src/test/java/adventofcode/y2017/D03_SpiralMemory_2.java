package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D03_SpiralMemory_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("1"), is(2L));
        assertThat(run("2"), is(4L));
        assertThat(run("4"), is(5L));
        assertThat(run("5"), is(10L));
        assertThat(run("810"), is(880L));
        assertThat(run("277678"), is(279138L));
    }

    private long run(String inputStr) {
        int input = Integer.parseInt(inputStr);
        int prevLength = 1;
        int[] prev = {1};
        while (true) {

            int curLength = prevLength + 2;
            int[] cur = new int[curLength * curLength - prevLength * prevLength];
            for (int i = 1; i < cur.length; i++) {
                int sideIndex = prevLength == 1 ? 0 : (i / (curLength - 1)); // left, top, right, bottom -> 0,1,2,3
                int sideOffset = i % (curLength - 1);
                int prevSideIndex = prevLength == 1 ? 0 : (prevLength - 1) * sideIndex + sideOffset - 1;

                cur[i] += (i > 1) ? cur[i - 1] : 0; // back
                if (sideOffset == 0) { // on corner
                    cur[i] += prev[(prevSideIndex + 1) % prev.length];  // inner
                } else {
                    cur[i] += prev[prevSideIndex % prev.length]; // inner

                    // backInner
                    if (sideOffset == 1) { // just after corner
                        cur[i] += (i > 1) ? cur[i - 2] : 0;
                    } else {
                        cur[i] += prev[(prevSideIndex - 1) % prev.length];
                    }

                    // frontInner
                    if (i == cur.length - 1) { // just before last corner
                        cur[i] += cur[1];
                    } else if (sideOffset != curLength - 2) { // must not be just before a corner
                        cur[i] += prev[(prevSideIndex + 1) % prev.length];
                    }
                }
                if (cur[i] > input) return cur[i];
            }
            cur[0] = prev[0] + cur[1] + cur[cur.length - 1];
            if (cur[0] > input) return cur[0];
            System.out.println(Arrays.toString(cur));
            prev = cur;
            prevLength = curLength;
        }
    }
}
