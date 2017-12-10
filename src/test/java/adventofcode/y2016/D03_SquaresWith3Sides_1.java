package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static adventofcode.Utils.toIntArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D03_SquaresWith3Sides_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("5 10 25\n10 10 10\n"), is(1));
        List<String> fileInputs = Utils.getInputsFromFiles(D03_SquaresWith3Sides_1.class);
        assertThat(run(fileInputs.get(0)), is(993));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int count = 0;
        for (String line : lines) {
            int[] sides = toIntArray(line.trim().split("\\s+"));
            Arrays.sort(sides);
            if (sides[0] + sides[1] > sides[2]) {
                ++count;
            }
        }
        return count;

    }
}
