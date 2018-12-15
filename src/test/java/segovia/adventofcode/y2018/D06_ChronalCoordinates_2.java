package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D06_ChronalCoordinates_2 {

    public static final int START = 0;
    public static final int END = 500;

    @Test
    public void test() throws IOException {
        assertThat(run("" +
                               "1, 1\n" +
                               "1, 6\n" +
                               "8, 3\n" +
                               "3, 4\n" +
                               "5, 5\n" +
                               "8, 9",
                       32), is(16));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0), 10000), is(42513));
    }

    private int run(String input, int maxDistSum) {
        List<Point> points = Arrays.stream(input.split("\\r?\\n")).map(Point::new).collect(Collectors.toList());
        int totalCoords = 0;
        for (int i = START; i < END; i++) {
            for (int j = START; j < END; j++) {
                int distSum = 0;
                for(Point p : points) {
                    distSum += p.dist(i, j);
                }
                if (distSum < maxDistSum) {
                    ++totalCoords;
                }
            }
        }
        return totalCoords;
    }

    private static class Point {
        int i;
        int j;

        public Point(String s) {
            String[] split = s.split(", ");
            i = Integer.parseInt(split[0]);
            j = Integer.parseInt(split[1]);
        }

        public int dist(int i, int j) {
            return Math.abs(this.i - i) + Math.abs(this.j - j);
        }
    }
}