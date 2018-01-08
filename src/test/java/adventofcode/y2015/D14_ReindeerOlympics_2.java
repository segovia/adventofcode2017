package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D14_ReindeerOlympics_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D14_ReindeerOlympics_2.class);
        assertThat(run(fileInputs.get(0), 1000), is(689));
        assertThat(run(fileInputs.get(1), 2503), is(1059));
    }

    private int run(String input, int seconds) {
        String[] lines = input.split("\\n");
        Reindeer[] rs = Arrays.stream(lines).map(Reindeer::new).toArray(Reindeer[]::new);
        for (int s = 0; s < seconds; s++) {
            // simulate
            for (Reindeer r : rs) if (r.run > s % r.cycle) r.cur += r.speed;

            int curMax = 0;
            for (Reindeer r : rs) curMax = Math.max(r.cur, curMax);

            for (Reindeer r : rs) if (curMax == r.cur) ++r.points;
        }
        int maxPoints =0;
        for (Reindeer r : rs) maxPoints = Math.max(r.points, maxPoints);
        return maxPoints;
    }

    private static class Reindeer {
        int speed, run, rest, cycle, cur, points;

        Reindeer(String line) {
            String[] tokens = line.split("\\s");

            speed = Integer.parseInt(tokens[3]);
            run = Integer.parseInt(tokens[6]);
            rest = Integer.parseInt(tokens[13]);
            cycle = run + rest;
        }
    }
}