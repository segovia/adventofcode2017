package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static adventofcode.Utils.toIntArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D03_SquaresWith3Sides_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("5 10 25\n10 10 10\n3 10 30\n"), is(2));
        List<String> fileInputs = Utils.getInputsFromFiles(D03_SquaresWith3Sides_2.class);
        assertThat(run(fileInputs.get(0)), is(1849));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int count = 0;
        for (int i = 0; i < lines.length; i += 3) {
            int[] row1 = readRow(lines[i]);
            int[] row2 = readRow(lines[i + 1]);
            int[] row3 = readRow(lines[i + 2]);

            for (int j = 0; j < 3; j++) {
                if (isTriangle(new int[]{row1[j], row2[j], row3[j]})) {
                    ++count;
                }
            }
        }
        return count;

    }

    private boolean isTriangle(int[] sides) {
        Arrays.sort(sides);
        return sides[0] + sides[1] > sides[2];
    }

    private int[] readRow(String line) {
        return toIntArray(line.trim().split("\\s+"));
    }
}
