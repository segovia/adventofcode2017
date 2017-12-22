package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adventofcode.y2017.D22_SporificaVirus_2.State.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D22_SporificaVirus_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D22_SporificaVirus_2.class);
        assertThat(run(fileInputs.get(0), 100), is(26));
        assertThat(run(fileInputs.get(0), 10_000), is(2608));
        assertThat(run(fileInputs.get(1), 10_000), is(2439));
    }

    @Test
    @Ignore("Ignored since this test takes 4s to run")
    public void testLong() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D22_SporificaVirus_2.class);
        assertThat(run(fileInputs.get(0), 10_000_000), is(2511944));
        assertThat(run(fileInputs.get(1), 10_000_000), is(2510774));
    }

    private int run(String input, int iterations) {
        Map<Pos, State> posMap = new HashMap<>();
        String[] lines = input.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                if (c == '#') posMap.put(new Pos(i, j), INFECTED);
            }
        }

        Pos cur = new Pos(lines.length / 2, lines[0].length() / 2);
        int dir = 0; // 0: up, 1: right, 2: down, 3: left
        int infectCount = 0;
        for (int it = 0; it < iterations; it++) {
            State curState = posMap.get(cur);
            State nextState = null;
            if (curState == null) {
                dir = (dir + 3) % 4;
                nextState = WEAK;
            } else if (curState == WEAK) {
                nextState = INFECTED;
                ++infectCount;
            } else if (curState == INFECTED) {
                dir = (dir + 1) % 4;
                nextState = FLAGGED;
            } else if (curState == FLAGGED) {
                dir = (dir + 2) % 4;
            }

            if (nextState == null) posMap.remove(cur);
            else posMap.put(new Pos(cur), nextState);

            cur.i += (dir == 0 ? -1 : (dir == 2 ? 1 : 0));
            cur.j += (dir == 3 ? -1 : (dir == 1 ? 1 : 0));
        }
        return infectCount;
    }

    enum State {WEAK, INFECTED, FLAGGED}

    private static class Pos {
        int i, j;

        public Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Pos(Pos o) {
            this(o.i, o.j);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return i == pos.i && j == pos.j;
        }

        @Override
        public int hashCode() {
            int result = i;
            result = 31 * result + j;
            return result;
        }
    }
}
