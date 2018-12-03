package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class D01_ChronalCalibration_2_Robust {

    @Test
    public void test() throws IOException {
        assertThat(run("+100000000\n-99999999\n"), is(100000000L));
        assertThat(run("-1\n+1"), is(0L));
        assertThat(run("+3\n+3\n+4\n-2\n-4"), is(10L));
        assertThat(run("-6\n+3\n+8\n+5\n-6"), is(5L));
        assertThat(run("+7\n+7\n-2\n-7\n-4"), is(14L));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(72889L));
        assertThat(run("+1\n+1"), is(nullValue()));
    }

    private Long run(String input) {
        long[] nums = Arrays.
                stream(input.split("\\s+")).
                mapToLong(Long::parseLong).
                toArray();

        Set<Long> seen = new LinkedHashSet<>();
        Long cur = 0L;
        seen.add(cur);
        for (Long num : nums) {
            cur += num;
            if (!seen.add(cur)) return cur;
        }
        long offset = Arrays.stream(nums).sum();
        if (offset == 0L) return null;
        long offsetAbs = Math.abs(offset);
        long bestIteration = Long.MAX_VALUE;
        Long bestVal = null;
        Long[] seenArr = new ArrayList<>(seen).toArray(new Long[0]);
        for (int i = 1; i < seenArr.length - 1; i++) {
            for (int j = i + 1; j < seenArr.length; j++) {
                long diff = Math.abs(seenArr[i] - seenArr[j]);
                if (diff % offsetAbs != 0) continue;
                long loops = diff / offsetAbs;
                int curPos = (offset > 0 && seenArr[i] < seenArr[j] || offset < 0 && seenArr[i] > seenArr[j]) ? i : j;
                long iterations = loops * seenArr.length + curPos;
                if (iterations < bestIteration) {
                    bestIteration = iterations;
                    bestVal = seenArr[curPos == i ? j : i];
                }
            }
        }
        return bestVal;
    }
}
