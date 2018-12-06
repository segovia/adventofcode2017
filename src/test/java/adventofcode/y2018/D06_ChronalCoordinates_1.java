package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D06_ChronalCoordinates_1 {

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
                               "8, 9"), is(17));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(5365));
    }

    private int run(String input) {
        List<Point> points = Arrays.stream(input.split("\\r?\\n")).map(Point::new).collect(Collectors.toList());
        for (int i = START; i < END; i++) {
            for (int j = START; j < END; j++) {
                Point closest = closest(points, i, j);
                if (closest == null) continue;
                if (i == START || i == END - 1 || j == START || j == END - 1) {
                    closest.isInfinite = true;
                    closest.closestPositions = 0;
                }
                if (!closest.isInfinite) {
                    ++closest.closestPositions;
                }
            }
        }
        Point answer = points.stream().max(Comparator.comparing(Point::getClosestPositions)).get();
        return answer.closestPositions;
    }

    public Point closest(List<Point> points, int x, int y) {
        int closestDist = Integer.MAX_VALUE;
        Point closest = null;
        for (int i = 0; i < points.size(); i++) {
            int dist = points.get(i).dist(x, y);
            if (closestDist == dist) {
                closest = null;
            } else if (dist < closestDist) {
                closestDist = dist;
                closest = points.get(i);
            }
        }
        return closest;
    }

    private static class Point {
        int i;
        int j;
        boolean isInfinite = false;
        int closestPositions = 0;

        public Point(String s) {
            String[] split = s.split(", ");
            i = Integer.parseInt(split[0]);
            j = Integer.parseInt(split[1]);
        }

        public int getClosestPositions() {
            return closestPositions;
        }

        public int dist(int i, int j) {
            return Math.abs(this.i - i) + Math.abs(this.j - j);
        }
    }
}