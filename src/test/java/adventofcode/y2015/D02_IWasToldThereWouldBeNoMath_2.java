package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D02_IWasToldThereWouldBeNoMath_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("2x3x4"), is(34));
        assertThat(run("1x1x10"), is(14));
        List<String> fileInputs = Utils.getInputsFromFiles(D02_IWasToldThereWouldBeNoMath_2.class);
        assertThat(run(fileInputs.get(0)), is(3737498));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int sum = 0;
        for (String line : lines) {
            int[] vals = Utils.toIntArray(line.split("x"));
            Arrays.sort(vals);
            sum += 2 * (vals[0] + vals[1]) + vals[0] * vals[1] * vals[2];
        }
        return sum;
    }
}
