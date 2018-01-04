package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D02_IWasToldThereWouldBeNoMath_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("2x3x4"), is(58));
        assertThat(run("1x1x10"), is(43));
        List<String> fileInputs = Utils.getInputsFromFiles(D02_IWasToldThereWouldBeNoMath_1.class);
        assertThat(run(fileInputs.get(0)), is(1586300));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int sum = 0;
        for (String line : lines) {
            int[] vals = Utils.toIntArray(line.split("x"));
            int[] sides = {vals[0] * vals[1], vals[0] * vals[2], vals[1] * vals[2]};
            Arrays.sort(sides);
            for (int side : sides) sum += side * 2;
            sum += sides[0];
        }
        return sum;
    }
}
