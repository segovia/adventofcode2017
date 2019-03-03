package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D25_FourDimensionalAdventure {

    @Test
    public void test() throws IOException {
        assertThat(run("" +
                " 0,0,0,0\n" +
                " 3,0,0,0\n" +
                " 0,3,0,0\n" +
                " 0,0,3,0\n" +
                " 0,0,0,3\n" +
                " 0,0,0,6\n" +
                " 9,0,0,0\n" +
                "12,0,0,0"), is(2L));
        assertThat(run("" +
                "-1,2,2,0\n" +
                "0,0,2,-2\n" +
                "0,0,0,-2\n" +
                "-1,2,0,0\n" +
                "-2,-2,-2,2\n" +
                "3,0,2,-1\n" +
                "-1,3,2,2\n" +
                "-1,0,-1,0\n" +
                "0,2,1,-2\n" +
                "3,0,0,0"), is(4L));
        assertThat(run("" +
                "1,-1,0,1\n" +
                "2,0,-1,0\n" +
                "3,2,-1,0\n" +
                "0,0,3,1\n" +
                "0,0,-1,-1\n" +
                "2,3,-2,0\n" +
                "-2,2,0,0\n" +
                "2,-2,0,-1\n" +
                "1,-1,0,-1\n" +
                "3,2,0,2"), is(3L));
        assertThat(run("" +
                "1,-1,-1,-2\n" +
                "-2,-2,0,1\n" +
                "0,2,1,3\n" +
                "-2,3,-2,1\n" +
                "0,2,3,-2\n" +
                "-1,-1,1,-2\n" +
                "0,-2,-1,0\n" +
                "-2,2,3,-1\n" +
                "1,2,2,0\n" +
                "-1,-2,0,-2"), is(8L));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(-5216));
    }

    private long run(String input) {
        List<Constellation> constellations = Arrays.stream(input.split("\\r?\\n"))
                .map(Constellation::new)
                .collect(Collectors.toList());

        for (boolean merged = true; merged;) {
            merged = false;
            for (int i = 0; i < constellations.size() - 1; i++) {
                Constellation ci = constellations.get(i);
                if (!ci.valid) continue;
                for (int j = i + 1; j < constellations.size(); j++) {
                    Constellation cj = constellations.get(j);
                    if (cj.valid && ci.closeEnough(cj)) {
                        ci.merge(cj);
                        merged = true;
                    }
                }
            }
        }
        return constellations.stream().filter(c -> c.valid).count();
    }

    private static class Constellation {
        List<int[]> points = new ArrayList<>();
        boolean valid = true;

        Constellation(String line) {
            String[] split = line.trim().split(",");
            int[] point = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                point[i] = Integer.parseInt(split[i]);
            }
            points.add(point);
        }

        void merge(Constellation o) {
            points.addAll(o.points);
            o.valid = false;
        }

        boolean closeEnough(Constellation o) {
            for (int[] point : points) {
                for (int[] otherPoint : o.points) {
                    if (dist(point, otherPoint) <= 3) return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(int[] point:points) {
                sb.append(Arrays.toString(point));
                sb.append(".");
            }
            return sb.toString();
        }
    }

    private static int dist(int[] point, int[] otherPoint) {
        int sum = 0;
        for (int i = 0; i < point.length; i++) {
            sum += Math.abs(point[i] - otherPoint[i]);
        }
        return sum;
    }

}