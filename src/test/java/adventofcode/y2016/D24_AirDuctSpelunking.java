package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D24_AirDuctSpelunking {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D24_AirDuctSpelunking.class);
        assertThat(run(fileInputs.get(0), false), is(14));
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

        return shortestPath(distMap, pathArray, 0, Integer.MAX_VALUE, 0, returnToZero);
    }

    private int shortestPath(int[][] distMap, int[] pathArray, int depth, int bestCost, int pathCost, boolean returnToZero) {
        if (depth == pathArray.length){
            System.out.println(Arrays.toString(pathArray));
            return Math.min(pathCost + (returnToZero ? distMap[pathArray[0]][pathArray[depth - 1]] : 0), bestCost);
        }

        int curBestCost = bestCost;
        int maxSwapTarget = depth == 0 ? 0 : pathArray.length - depth - 1;
        for (int i = 0; i <= maxSwapTarget; i++) {
            swap(pathArray, depth, depth + i);
            int nextPathCost = pathCost + (depth == 0 ? 0 : distMap[pathArray[depth]][pathArray[depth - 1]]);
            curBestCost = shortestPath(distMap, pathArray, depth + 1, curBestCost, nextPathCost, returnToZero);
            swap(pathArray, depth, depth + i);
        }
        return curBestCost;
    }

    private void swap(int[] array, int a, int b) {
        if (a == b) return;
        int aux = array[a];
        array[a] = array[b];
        array[b] = aux;
    }

    private int[][] findDistMap(int width, char[] map, int digits) {
        int[] position = new int[digits];
        for (int i = 0; i < map.length; i++) if (Character.isDigit(map[i])) position[map[i] - '0'] = i;

        int height = map.length / width;
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

                    int curI = cur / width;
                    int curJ = cur % width;
                    if (curI > 0) nextBorder.add(cur - width);
                    if (curI < height - 1) nextBorder.add(cur + width);
                    if (curJ > 0) nextBorder.add(cur - 1);
                    if (curJ < width - 1) nextBorder.add(cur + 1);
                }
                ++dist;
            }
        }
        return distMap;
    }

    private char[] linearizeMap(String[] lines) {
        int height = lines.length;
        int width = lines[0].length();
        char[] map = new char[height * width];
        for (int i = 0; i < lines.length; i++) System.arraycopy(lines[i].toCharArray(), 0, map, i * width, width);
        return map;
    }
}