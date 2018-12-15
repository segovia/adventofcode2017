package segovia.adventofcode.y2016;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D22_GridComputing_2 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D22_GridComputing_2.class);
        assertThat(run(fileInputs.get(0), 3, 3), is(7));
        assertThat(run(fileInputs.get(1), 26, 38), is(256));
    }

    private int run(String input, int height, int width) throws Exception {
        int[] size = new int[width * height];
        int[] used = new int[width * height];
        String[] lines = input.split("\\n");
        for (int i = 2; i < lines.length; i++) {
            String[] tokens = lines[i].split("\\s+");
            String[] fileNameTokens = tokens[0].split("-");
            int x = Integer.parseInt(fileNameTokens[1].substring(1));
            int y = Integer.parseInt(fileNameTokens[2].substring(1));
            int idx = y * width + x;
            size[idx] = Integer.parseInt(tokens[1].substring(0, tokens[1].length() - 1));
            used[idx] = Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 1));
        }
        int count = 0;
        // could go in straight line?
        int startIdx = width - 1;
        for (int i = 1; i < width - 2; i++) {
            if (size[i] < used[startIdx]) System.out.println("Cannot pass through: " + i);
        }

//        print(width, size, used);
        for (int target = startIdx - 1; target >= 0; target--) {
            int source = target + 1;
            if (avail(size, used, target) < used[source]) {
                String movePath = findMovePath(width, size, used, source);
                int cur = source;
                int prevValue = used[source];
                for (int i = 0; i < movePath.length(); i++) {
                    cur = toNextPos(width, movePath.charAt(i), cur);
                    int aux = used[cur];
                    used[cur] = prevValue;
                    prevValue = aux;
                }
                used[source] = 0;
                used[cur] += prevValue;
                count += movePath.length();
            } else {
                used[target] += used[source];
                used[source] = 0;
                ++count;
            }
//            print(width, size, used);
        }
        return count;
    }

    private String findMovePath(int width, int[] size, int[] used, int source) {
        int[] visited = new int[size.length];
        visited[source] = source;
        Deque<String> queue = new ArrayDeque<>();
        queue.add("L");
        while (!queue.isEmpty()) {
            int cur = source;
            int prev = -1;
            String path = queue.poll();
            for (int i = 0; i < path.length(); i++) {
                prev = cur;
                cur = toNextPos(width, path.charAt(i), cur);
            }
            if (visited[cur] == source) continue;
            visited[cur] = source;
            if (avail(size, used, cur) >= used[prev]) return path;

            int curX = cur % width;
            int height = size.length / width;
            int curY = cur / width;

            if (curY > 0 && size[cur - width] >= used[cur]) queue.add(path + 'U');
            if (curY < height - 1 && size[cur + width] >= used[cur]) queue.add(path + 'D');
            if (curX > 0 && size[cur - 1] >= used[cur]) queue.add(path + 'L');
            if (curX < width - 1 && size[cur + 1] >= used[cur]) queue.add(path + 'R');
        }
        return null;
    }

    private int toNextPos(int width, char move, int cur) {
        switch (move) {
            case 'U':
                cur -= width;
                break;
            case 'D':
                cur += width;
                break;
            case 'L':
                cur -= 1;
                break;
            case 'R':
                cur += 1;
                break;
        }
        return cur;
    }

    @SuppressWarnings("unused")
    private void print(int width, int[] size, int[] used) {
        int height = size.length / width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int cur = i * width + j;
                System.out.print(String.format("%3d/%3d\t", used[cur], size[cur]));
            }
            System.out.println();
        }
        System.out.println();
    }

    private int avail(int[] size, int[] used, int i) {
        return size[i] - used[i];
    }
}