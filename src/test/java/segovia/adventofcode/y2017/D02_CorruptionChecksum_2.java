package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D02_CorruptionChecksum_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D02_CorruptionChecksum_2.class);
        assertThat(run(fileInputs.get(0)), is(7L));
        assertThat(run(fileInputs.get(1)), is(9L));
        assertThat(run(fileInputs.get(2)), is(320L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        Long sum = 0L;
        for (String line : lines) {
            String[] tokens = line.split("\\s");

            outer:
            for (int j = 0; j < tokens.length - 1; j++) {
                for (int k = j + 1; k < tokens.length; k++) {
                    Long a = Long.parseLong(tokens[j]);
                    Long b = Long.parseLong(tokens[k]);
                    long max = Math.max(a, b);
                    long min = Math.min(a, b);
                    if (max % min == 0) {
                        sum += max / min;
                        break outer;
                    }
                }
            }
        }
        return sum;
    }
}
