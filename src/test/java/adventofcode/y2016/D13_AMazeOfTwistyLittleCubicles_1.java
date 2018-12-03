package adventofcode.y2016;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D13_AMazeOfTwistyLittleCubicles_1 {

    @Test
    public void test() throws Exception {
        assertThat(run(10, 7, 4), is(11L));
        assertThat(run(1358, 31, 39), is(96L));
    }

    private long run(int input, int targetX, int targetY) throws Exception {
        List<Pos> nextList = new ArrayList<>();
        nextList.add(new Pos(1, 1));
        int len = 0;
        Set<Object> visited = new HashSet<>();
        while (!nextList.isEmpty()) {
            List<Pos> curList = nextList;
            nextList = new ArrayList<>();
            for (Pos cur : curList) {
                if (!visited.add(cur)) continue;
                if (cur.x == targetX && cur.y == targetY) return len;
                addNext(nextList, cur.x + 1, cur.y, input);
                addNext(nextList, cur.x - 1, cur.y, input);
                addNext(nextList, cur.x, cur.y + 1, input);
                addNext(nextList, cur.x, cur.y - 1, input);
            }
            ++len;
        }
        return -1L;
    }

    static void addNext(List<Pos> nextList, int x, int y, int input) {
        if (isWall(x, y, input)) return;
        nextList.add(new Pos(x, y));
    }

    private static boolean isWall(int x, int y, int input) {
        return x < 0 || y < 0 || Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + input) % 2 != 0;
    }

    static class Pos {
        int x, y;

        Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos)) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

}