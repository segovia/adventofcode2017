package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D10_TheStarsAlign {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(3));
        assertThat(run(fileInputs.get(1)), is(10054));
    }

    private int run(String input) {
        List<Point> points = Arrays.stream(input.split("\\r?\\n")).map(Point::new).collect(toList());
        int timeStep = 0;
        int height = Integer.MAX_VALUE;
        while (true) {
            int curMaxY = Integer.MIN_VALUE, curMinY = Integer.MAX_VALUE;
            for (Point point : points) {
                int yPos = point.yP + point.yV * timeStep;
                curMaxY = Integer.max(curMaxY, yPos);
                curMinY = Integer.min(curMinY, yPos);
            }
            int curHeight = curMaxY - curMinY;
            if (curHeight > height) {
                --timeStep;
                break;
            }
            height = curHeight;
            ++timeStep;
        }
        printTimeStep(points, timeStep);
        return timeStep;
    }

    private void printTimeStep(List<Point> points, int timeStep) {
        int maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;
        for (Point point : points) {
            int xPos = point.xP + point.xV * timeStep;
            maxX = Integer.max(maxX, xPos);
            minX = Integer.min(minX, xPos);
            int yPos = point.yP + point.yV * timeStep;
            maxY = Integer.max(maxY, yPos);
            minY = Integer.min(minY, yPos);
        }
        char[][] map = new char[maxY - minY + 1][maxX - minX + 1];
        for (Point point : points) {
            int xPos = point.xP + point.xV * timeStep - minX;
            int yPos = point.yP + point.yV * timeStep - minY;
            map[yPos][xPos] = '#';
        }
        for (char[] line : map) {
            for (char c : line) {
                System.out.print(c == 0 ? ' ' : c);
            }
            System.out.println();
        }
    }

    private static final Pattern PATTERN = Pattern.compile(
            ".*<\\s*([-]?\\d+),\\s*([-]?\\d+)>.*<\\s*([-]?\\d+),\\s*([-]?\\d+)>");
    private static class Point {
        int xP, yP, xV, yV;
        public Point(String s) {
            Matcher matcher = PATTERN.matcher(s);
            matcher.find();
            xP = Integer.parseInt(matcher.group(1));
            yP = Integer.parseInt(matcher.group(2));
            xV = Integer.parseInt(matcher.group(3));
            yV = Integer.parseInt(matcher.group(4));
        }
    }
}
