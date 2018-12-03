package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D13_PacketScanners_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D13_PacketScanners_1.class);
        assertThat(run(fileInputs.get(0)), is(24));
        assertThat(run(fileInputs.get(1)), is(2604));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int sum = 0;
        for (String line : lines) {
            int[] vals = Utils.toIntArray(line.split(": "));
            if (vals[1] == 1 || vals[0] % (2 * vals[1] - 2) == 0) {
                sum += vals[0] * vals[1];
            }
        }
        return sum;
    }
}
