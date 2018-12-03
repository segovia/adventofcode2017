package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static adventofcode.Utils.swap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D24_AirDuctSpelunking {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D24_AirDuctSpelunking.class);
        assertThat(run(fileInputs.get(0), false), is(14));
        assertThat(run(fileInputs.get(0), true), is(20));
        assertThat(run(fileInputs.get(1), false), is(462));
        assertThat(run(fileInputs.get(1), true), is(676));
    }

    private int run(String input, boolean returnToZero) throws Exception {
        String[] lines = input.split("\\n");
        int width = lines[0].length();
        char[] map = linearizeMap(lines);
        int maxIdx = 0;
        for (char c : map) if (Character.isDigit(c)) maxIdx = Math.max(maxIdx, c - '0');

        int digits = maxIdx + 1;
        int[][] distMap = findDistMap(width, map, digits);
        int[] pathArray = new int[digits];
        for (int i = 0; i < pathArray.length; i++) pathArray[i] = i;

        return shortestPath(distMap, pathArray, 0, returnToZero);
    }

    private int shortestPath(int[][] distMap, int[] pathArray, int depth, boolean returnToZero) {
        if (depth == pathArray.length)
            return returnToZero ? distMap[pathArray[0]][pathArray[depth - 1]] : 0;

        int minDist = Integer.MAX_VALUE;
        int maxSwapTarget = depth == 0 ? 0 : pathArray.length - depth - 1;
        for (int i = 0; i <= maxSwapTarget; i++) {
            swap(pathArray, depth, depth + i);
            int dist = depth == 0 ? 0 : distMap[pathArray[depth]][pathArray[depth - 1]];
            dist += shortestPath(distMap, pathArray, depth + 1, returnToZero);
            minDist = Math.min(minDist, dist);
            swap(pathArray, depth, depth + i);
        }
        return minDist;
    }

    private int[][] findDistMap(int width, char[] map, int digits) {
        int[] position = getDigitPositions(map, digits);
        int[][] distMap = new int[digits][digits];
        for (int idx = 0; idx < digits - 1; idx++) {
            boolean[] visited = new boolean[map.length];
            List<Integer> nextBorder = new ArrayList<>();
            nextBorder.add(position[idx]);
            int dist = 0;
            while (!nextBorder.isEmpty()) {
                List<Integer> border = nextBorder;
                nextBorder = new ArrayList<>();
                for (Integer cur : border) {
                    char c = map[cur];
                    if (visited[cur] || c == '#') continue;
                    visited[cur] = true;
                    if (Character.isDigit(c)) {
                        int curIdx = c - '0';
                        distMap[idx][curIdx] = dist;
                        distMap[curIdx][idx] = dist;
                    }
                    nextBorder.add(cur - width);
                    nextBorder.add(cur + width);
                    nextBorder.add(cur - 1);
                    nextBorder.add(cur + 1);
                }
                ++dist;
            }
        }
        return distMap;
    }

    private int[] getDigitPositions(char[] map, int digits) {
        int[] position = new int[digits];
        for (int i = 0; i < map.length; i++) if (Character.isDigit(map[i])) position[map[i] - '0'] = i;
        return position;
    }

    private char[] linearizeMap(String[] lines) {
        int height = lines.length;
        int width = lines[0].length();
        char[] map = new char[height * width];
        for (int i = 0; i < lines.length; i++) System.arraycopy(lines[i].toCharArray(), 0, map, i * width, width);
        return map;
    }
}