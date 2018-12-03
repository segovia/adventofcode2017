package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D02_CorruptionChecksum_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D02_CorruptionChecksum_1.class);
        assertThat(run(fileInputs.get(0)), is(18L));
        assertThat(run(fileInputs.get(1)), is(18L));
        assertThat(run(fileInputs.get(2)), is(44216L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        Long sum = 0L;
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            Long min = Long.MAX_VALUE;
            Long max = Long.MIN_VALUE;
            for (String token : tokens) {
                Long num = Long.parseLong(token);
                min = Math.min(num, min);
                max = Math.max(num, max);
            }
            sum += max - min;
        }
        return sum;
    }
}
