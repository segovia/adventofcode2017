package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D22_SporificaVirus_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D22_SporificaVirus_1.class);
        assertThat(run(fileInputs.get(0), 70), is(41));
        assertThat(run(fileInputs.get(1), 10_000), is(5369));
    }

    private int run(String input, int iterations) {
        Set<Pos> infectedPos = new HashSet<>();
        String[] lines = input.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                if (c == '#') infectedPos.add(new Pos(i, j));
            }
        }

        Pos cur = new Pos(lines.length / 2, lines[0].length() / 2);
        int dir = 0; // 0: up, 1: right, 2: down, 3: left
        int infectCount = 0;
        for (int it = 0; it < iterations; it++) {
            boolean curInfected = infectedPos.remove(cur);
            dir = (dir + (curInfected ? 1 : 3)) % 4;
            if (!curInfected) {
                infectedPos.add(new Pos(cur));
                ++infectCount;
            }
            cur.i += (dir == 0 ? -1 : (dir == 2 ? 1 : 0));
            cur.j += (dir == 3 ? -1 : (dir == 1 ? 1 : 0));
        }
        return infectCount;
    }

    private static class Pos {
        int i, j;

        public Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Pos(Pos o) {
            this.i = o.i;
            this.j = o.j;
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
