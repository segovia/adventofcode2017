package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static segovia.adventofcode.Utils.toIntArray;
import static segovia.adventofcode.y2015.D06_ProbablyAFireHazard.Mode.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D06_ProbablyAFireHazard {

    @Test
    public void test() throws IOException {
        assertThat(run("turn on 0,0 through 999,999"), is(1_000_000L));
        assertThat(run("toggle 0,0 through 999,0"), is(1000L));
        assertThat(run("turn on 0,0 through 999,999\ntoggle 0,0 through 999,0"), is(999_000L));
        assertThat(run("turn on 0,0 through 999,999\nturn off 499,499 through 500,500"), is(999_996L));

        List<String> fileInputs = Utils.getInputsFromFiles(D06_ProbablyAFireHazard.class);
        assertThat(run(fileInputs.get(0)), is(377891L));
        assertThat(runStar2(fileInputs.get(0)), is(14110788L));
    }

    private long run(String input) {
        return runInstructions(input, (map, i, j, m) -> {
            if (m == TOGGLE) map[i][j] ^= 1;
            else map[i][j] = m == ON ? 1 : 0;
        });
    }

    private long runStar2(String input) {
        return runInstructions(input, (map, i, j, m) -> {
            map[i][j] += m == TOGGLE ? 2 : (m == ON ? 1 : -1);
            if (map[i][j] < 0) map[i][j] = 0;
        });
    }

    static int runInstructions(String input, RunnableInstruction runnable) {
        String[] lines = input.split("\\n");
        int[][] lights = new int[1000][1000];
        for (String line : lines) {
            String[] tokens = line.split(" ");

            Mode m;
            int[] start;
            int[] end;
            switch (line.substring(0, 7)) {
                case "turn on":
                case "turn of":
                    start = toIntArray(tokens[2].split(","));
                    end = toIntArray(tokens[4].split(","));
                    m = tokens[1].equals("on") ? ON : OFF;
                    break;
                case "toggle ":
                    start = toIntArray(tokens[1].split(","));
                    end = toIntArray(tokens[3].split(","));
                    m = TOGGLE;
                    break;
                default:
                    throw new RuntimeException("oops");
            }

            for (int i = start[0]; i <= end[0]; i++) {
                for (int j = start[1]; j <= end[1]; j++) {
                    runnable.run(lights, i, j, m);
                }
            }
        }

        int count = 0;
        for (int i = 0; i < 1000; i++) for (int j = 0; j < 1000; j++) count += lights[i][j];
        return count;
    }

    interface RunnableInstruction {
        void run(int[][] lights, int i, int j, Mode m);
    }

    enum Mode {ON, OFF, TOGGLE}
}
