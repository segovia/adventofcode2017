package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D01_ChronalCalibration_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("-1\n+1"), is(0L));
        assertThat(run("+3\n+3\n+4\n-2\n-4"), is(10L));
        assertThat(run("-6\n+3\n+8\n+5\n-6"), is(5L));
        assertThat(run("+7\n+7\n-2\n-7\n-4"), is(14L));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(72889L));
    }

    private long run(String input) {
        long[] nums = Arrays.
                stream(input.split("\\s+")).
                mapToLong(Long::parseLong)
                .toArray();
        Set<Long> seen = new HashSet<>();
        Long cur = 0L;
        while (true) {
            for (Long num : nums) {
                if (!seen.add(cur)) return cur;
                cur += num;
            }
        }
    }
}
