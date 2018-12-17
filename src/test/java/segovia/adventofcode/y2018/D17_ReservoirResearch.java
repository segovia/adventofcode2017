package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D17_ReservoirResearch {

    @Test
    public void test() throws IOException {
        assertThat(run("" +
                "x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504"), is("57 29"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("39557 32984"));
    }

    private String run(String input) {
        List<Vals> entries = Arrays.stream(input.split("\\r?\\n")).map(Vals::new).collect(Collectors.toList());
        int maxX = 0;
        int maxY = 0;
        int minY = Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        for (Vals vals : entries) {
            minX = Integer.min(minX, vals.xFirst ? vals.a : vals.b);
            minY = Integer.min(minY, vals.xFirst ? vals.b : vals.a);
            maxX = Integer.max(maxX, vals.xFirst ? vals.a : vals.c);
            maxY = Integer.max(maxY, vals.xFirst ? vals.c : vals.a);
        }
        char[][] map = new char[maxY + 1 - minY][maxX + 2 - --minX];
        for (int i = 0; i < map.length; i++) for (int j = 0; j < map[0].length; j++) map[i][j] = ' ';
        for (Vals vals : entries) {
            int xStart = vals.a, xEnd = vals.a, yStart = vals.b, yEnd = vals.c;
            if (!vals.xFirst) {
                xStart = vals.b;
                xEnd = vals.c;
                yStart = vals.a;
                yEnd = vals.a;
            }
            for (int i = yStart; i <= yEnd; i++) {
                for (int j = xStart; j <= xEnd; j++) {
                    map[i - minY][j - minX] = '#';
                }
            }
        }
        Deque<FlowingWater> q = new ArrayDeque<>();
        q.push(new FlowingWater(500 - minX, 0));
        map[q.peek().y][q.peek().x] = '|';
        simulate(map, q);
        int allWater = 0, retainedWater = 0;
        for (char[] row : map)
            for (char c : row) {
                if (c == '|') ++allWater;
                if (c == '~') {
                    ++allWater;
                    ++retainedWater;
                }
            }
        return allWater + " " + retainedWater;
    }

    private void simulate(char[][] map, Deque<FlowingWater> q) {
        while (!q.isEmpty()) {
            FlowingWater fw = q.poll();
            if (fw.y == map.length - 1) continue;
            char under = map[fw.y + 1][fw.x];
            if (under == '|') continue;
            if (under == ' ') { // stream down
                ++fw.y;
                map[fw.y][fw.x] = '|';
                q.push(fw);
                continue;
            }
            if (under != '#' && under != '~') continue;
            int borderRight = findBorder(map, fw, 1);
            int borderLeft = findBorder(map, fw, -1);
            if (map[fw.y][borderLeft] != '#' && map[fw.y][borderLeft] != '|')
                q.push(new FlowingWater(borderLeft, fw.y));
            if (map[fw.y][borderRight] != '#' && map[fw.y][borderRight] != '|')
                q.push(new FlowingWater(borderRight, fw.y));
            boolean flowing = map[fw.y][borderLeft] != '#' || map[fw.y][borderRight] != '#';
            for (int i = borderLeft; i <= borderRight; i++) {
                if (map[fw.y][i] != '#') map[fw.y][i] = flowing ? '|' : '~';
            }
            if (!flowing) {
                --fw.y;
                q.push(fw);
            }
        }
    }

    private int findBorder(char[][] map, FlowingWater fw, int inc) {
        int curX = fw.x + inc;
        while (0 < curX && curX < map[0].length - 1) {
            if (map[fw.y][curX] == '#') return curX;
            if (map[fw.y + 1][curX] == ' ' | map[fw.y + 1][curX] == '|') return curX;
            curX += inc;
        }
        return curX;
    }

    private static class FlowingWater {
        int x, y;

        FlowingWater(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Vals {
        boolean xFirst;
        int a, b, c;

        Vals(String line) {
            xFirst = line.charAt(0) == 'x';
            String[] split = line.split(",");
            a = Integer.parseInt(split[0].substring(2));
            b = Integer.parseInt(split[1].substring(3, split[1].indexOf(".")));
            c = Integer.parseInt(split[1].substring(split[1].indexOf(".") + 2));
        }
    }
}
