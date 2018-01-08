package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D14_ReindeerOlympics_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D14_ReindeerOlympics_1.class);
        assertThat(run(fileInputs.get(0), 1000), is(1120));
        assertThat(run(fileInputs.get(1), 2503), is(2655));

    }

    private int run(String input, int seconds) {
        String[] lines = input.split("\\n");
        int maxDist = 0;
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            int speed = Integer.parseInt(tokens[3]);
            int run = Integer.parseInt(tokens[6]);
            int rest = Integer.parseInt(tokens[13]);

            int cycle = run + rest;
            int completeCycles = seconds / cycle;
            int partial = Math.min(run, seconds % cycle);
            int dist = (completeCycles * run + partial) * speed;
            maxDist = Math.max(dist, maxDist);
        }
        return maxDist;
    }
}