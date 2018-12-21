package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D20_ARegularMap {

    @Test
    public void test() throws IOException {
        assertThat(run("^WNE$"), is("3 0"));
        assertThat(run("^ENWWW(NEEE|SSE(EE|N))$"), is("10 0"));
        assertThat(run("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"), is("18 0"));
        assertThat(run("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$"), is("23 0"));
        assertThat(run("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$"), is("31 0"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("4180 8321"));
    }

    private static final int SIZE = 220;

    private String run(String input) {
        char[][] map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) map[i][j] = ' ';
        Position start = new Position(SIZE / 2, SIZE / 2);
        map[start.i][start.j] = 'X';
        runRegex(map, input, 1, start);
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) if (map[i][j] == '?') map[i][j] = '#';
        return farthestRoom(map, start);
    }

    private int runRegex(char[][] map, String str, int strIdx, Position p) {
        Position curP = new Position(p.i, p.j);
        boolean openPar = false;
        for (int cur = strIdx; cur < str.length(); cur++) {
            initPosition(map, curP);
            char c = str.charAt(cur);
            int dir = c == 'N' || c == 'W' ? -1 : 1;
            if (c == 'N' || c == 'S') {
                map[curP.i + dir][curP.j] = '-';
                curP.i += 2 * dir;
            } else if (c == 'E' || c == 'W') {
                map[curP.i][curP.j + dir] = '|';
                curP.j += 2 * dir;
            } else if (c == '(' || openPar && c == '|') {
                cur = runRegex(map, str, cur + 1, curP);
                openPar = str.charAt(cur) == '|';
                if (openPar) --cur;
            } else if (c == '|' || c == ')') return cur;
        }
        return str.length();
    }

    private void initPosition(char[][] map, Position p) {
        getSurroundingPositions(p).forEach(curP -> {
            if ((p.i == curP.i || p.j == curP.j) && map[curP.i][curP.j] == ' ') map[curP.i][curP.j] = '?';
            else if ((p.i != curP.i && p.j != curP.j)) map[curP.i][curP.j] = '#';
        });
    }

    private String farthestRoom(char[][] map, Position start) {
        Deque<Position> q, next = new ArrayDeque<>();
        next.add(start);
        int count = -1;
        int countAtLeast1000 = 0;
        while (!next.isEmpty()) {
            ++count;
            q = next;
            next = new ArrayDeque<>();
            while (!q.isEmpty()) {
                Position p = q.poll();
                if (map[p.i][p.j] == '.') continue;
                map[p.i][p.j] = '.';
                if (count >= 1000) ++countAtLeast1000;
                for (Position sp : getSurroundingPositions(p)) {
                    if (map[sp.i][sp.j] == '#') continue;
                    Position np = new Position(p.i + 2 * (sp.i - p.i), p.j + 2 * (sp.j - p.j));
                    if (map[np.i][np.j] == '.') continue;
                    next.push(np);
                }
            }
        }
        return count + " " + countAtLeast1000;
    }

    private List<Position> getSurroundingPositions(Position p) {
        List<Position> positions = new ArrayList<>();
        for (int i = p.i - 1; i <= p.i + 1; i++) {
            for (int j = p.j - 1; j <= p.j + 1; j++) {
                if (p.i == i && p.j == j) continue;
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    private static class Position {
        int i, j;

        Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
