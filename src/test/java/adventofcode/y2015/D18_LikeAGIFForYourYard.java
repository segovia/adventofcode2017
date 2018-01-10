package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D18_LikeAGIFForYourYard {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D18_LikeAGIFForYourYard.class);
        assertThat(run(fileInputs.get(0), 4, false), is(4));
        assertThat(run(fileInputs.get(1), 100, false), is(1061));
        assertThat(run(fileInputs.get(0), 4, true), is(14));
        assertThat(run(fileInputs.get(1), 100, true), is(1061));
    }

    private int run(String input, int steps, boolean stuckLights) {
        String[] lines = input.split("\\n");
        int len = lines.length;
        boolean[][] lights = new boolean[len][len];
        boolean[][] next = new boolean[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                lights[i][j] = lines[i].charAt(j) == '#';
            }
        }

        if (stuckLights) activateStuckLights(lights);
        for (int step = 0; step < steps; step++) {
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    int neighborOnCount = 0;
                    if (safeOn(lights, i - 1, j - 1)) neighborOnCount++;
                    if (safeOn(lights, i - 1, j)) neighborOnCount++;
                    if (safeOn(lights, i - 1, j + 1)) neighborOnCount++;
                    if (safeOn(lights, i, j + 1)) neighborOnCount++;
                    if (safeOn(lights, i + 1, j + 1)) neighborOnCount++;
                    if (safeOn(lights, i + 1, j)) neighborOnCount++;
                    if (safeOn(lights, i + 1, j - 1)) neighborOnCount++;
                    if (safeOn(lights, i, j - 1)) neighborOnCount++;

                    next[i][j] = (lights[i][j] && neighborOnCount >= 2 && neighborOnCount <= 3)
                            || (!lights[i][j] && neighborOnCount == 3);
                }
            }

            boolean[][] aux = lights;
            lights = next;
            next = aux;
            if (stuckLights) activateStuckLights(lights);
        }

        int count = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (lights[i][j]) count++;
            }
        }
        return count;
    }

    private void activateStuckLights(boolean[][] lights) {
        lights[0][0] = true;
        lights[lights.length - 1][0] = true;
        lights[0][lights.length - 1] = true;
        lights[lights.length - 1][lights.length - 1] = true;
    }

    private boolean safeOn(boolean[][] lights, int i, int j) {
        return i >= 0 && i < lights.length && j >= 0 && j < lights.length && lights[i][j];
    }
}