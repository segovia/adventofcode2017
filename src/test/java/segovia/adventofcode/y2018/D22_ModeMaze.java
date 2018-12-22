package segovia.adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;
import java.util.PriorityQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D22_ModeMaze {

    @Test
    public void test() throws IOException {
        assertThat(run(10, 10, 510), is("114 45"));
        assertThat(run(9, 739, 10914), is("7380 1013"));
    }

    public static final int PADDING = 750;

    private String run(int tX, int tY, int depth) {
        int[][] map = new int[tY + PADDING][tX + PADDING];
        int risk = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == 0 && j == 0 || j == tX && i == tY) map[i][j] = 0;
                else if (i == 0) map[i][j] = j * 16807;
                else if (j == 0) map[i][j] = i * 48271;
                else map[i][j] = map[i - 1][j] * map[i][j - 1];
                map[i][j] = (map[i][j] + depth) % 20183;
                if (i <= tY && j <= tX) risk += map[i][j] % 3;
            }
        }
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(0, 0, 1, 0));
        boolean[][][] visited = new boolean[map.length][map[0].length][3];
        while (!pq.isEmpty()) {
            State s = pq.poll();
            if (visited[s.i][s.j][s.equipped]) continue;
            visited[s.i][s.j][s.equipped] = true;
            if (s.i == tY && s.j == tX && s.equipped == 1) return risk + " " + s.time;
            for (int i = 0; i < 3; i++) {
                if (map[s.i][s.j] % 3 == i || i == s.equipped) continue;
                addIfNew(pq, visited, new State(s.i, s.j, i, s.time + 7));
            }
            if (s.i > 0 && map[s.i - 1][s.j] % 3 != s.equipped)
                addIfNew(pq, visited, new State(s.i - 1, s.j, s.equipped, s.time + 1));
            if (s.j > 0 && map[s.i][s.j - 1] % 3 != s.equipped)
                addIfNew(pq, visited, new State(s.i, s.j - 1, s.equipped, s.time + 1));
            if (map[s.i + 1][s.j] % 3 != s.equipped)
                addIfNew(pq, visited, new State(s.i + 1, s.j, s.equipped, s.time + 1));
            if (map[s.i][s.j + 1] % 3 != s.equipped)
                addIfNew(pq, visited, new State(s.i, s.j + 1, s.equipped, s.time + 1));
        }
        return null;
    }

    private void addIfNew(PriorityQueue<State> pq, boolean[][][] visited, State s) {
        if (visited[s.i][s.j][s.equipped]) return;
        pq.add(s);
    }

    private static class State implements Comparable<State> {
        int i, j, time;
        int equipped; // 0 neither, 1 torch, 2 climbing gear

        State(int i, int j, int equipped, int time) {
            this.i = i;
            this.j = j;
            this.equipped = equipped;
            this.time = time;
        }
        public int compareTo(State o) {
            return Integer.compare(time, o.time);
        }
    }
}
