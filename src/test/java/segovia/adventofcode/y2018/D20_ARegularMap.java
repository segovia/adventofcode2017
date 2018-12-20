package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.*;

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
        Deque<State> q = new ArrayDeque<>();
        q.push(new State(SIZE / 2, SIZE / 2, 1));
        map[SIZE / 2][SIZE / 2] = 'X';
        Set<State> processed = new HashSet<>();
        while (!q.isEmpty()) {
            State s = q.poll();
            if (!processed.add(s)) continue;
            getSurrondingPositions(s.p).forEach(p -> {
                if ((p.i == s.p.i || p.j == s.p.j) && map[p.i][p.j] == ' ') map[p.i][p.j] = '?';
                else if ((p.i != s.p.i && p.j != s.p.j)) map[p.i][p.j] = '#';
            });
            if (input.charAt(s.regexIndex) == 'N') {
                q.add(new State(s.p.i - 2, s.p.j, s.regexIndex + 1));
                map[s.p.i - 1][s.p.j] = '-';
            } else if (input.charAt(s.regexIndex) == 'E') {
                q.add(new State(s.p.i, s.p.j + 2, s.regexIndex + 1));
                map[s.p.i][s.p.j + 1] = '|';
            } else if (input.charAt(s.regexIndex) == 'S') {
                q.add(new State(s.p.i + 2, s.p.j, s.regexIndex + 1));
                map[s.p.i + 1][s.p.j] = '-';
            } else if (input.charAt(s.regexIndex) == 'W') {
                q.add(new State(s.p.i, s.p.j - 2, s.regexIndex + 1));
                map[s.p.i][s.p.j - 1] = '|';
            } else if (input.charAt(s.regexIndex) == '(') {
                int openParen = 0;
                for (int cur = s.regexIndex; cur < input.length(); ++cur) {
                    char c = input.charAt(cur);
                    if (c == '(') ++openParen;
                    else if(c == ')') --openParen;
                    else if (openParen == 0) break;
                    if ((c == '(' || c == '|') && openParen == 1) {
                        q.add(new State(s.p.i, s.p.j, cur + 1));
                    }
                }
            } else if (input.charAt(s.regexIndex) == '|') {
                int openParen = 1;
                for (int cur = s.regexIndex + 1; cur < input.length(); ++cur) {
                    char c = input.charAt(cur);
                    if (c == '(') ++openParen;
                    else if(c == ')') --openParen;
                    if (c == ')' && openParen == 0) {
                        q.add(new State(s.p.i, s.p.j, cur + 1));
                        break;
                    }
                }
            } else if (input.charAt(s.regexIndex) == ')') {
                q.add(new State(s.p.i, s.p.j, s.regexIndex + 1));
            }
        }
        q.clear();
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) if (map[i][j] == '?') map[i][j] = '#';
        return farthestRoomViaBfs(map, new Position(SIZE / 2, SIZE / 2));
    }

    private String farthestRoomViaBfs(char[][] map, Position start){
        Deque<Position> q;
        Deque<Position> next = new ArrayDeque<>();
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
                for (Position sp : getSurrondingPositions(p)) {
                    if (map[sp.i][sp.j] != '|' && map[sp.i][sp.j] != '-') continue;
                    int iDiff = sp.i - p.i;
                    int jDiff = sp.j - p.j;
                    Position np = new Position(p.i + 2 * iDiff, p.j + 2 * jDiff);
                    if (map[np.i][np.j] == '.') continue;
                    next.push(np);
                }
            }
        }
        return count + " " + countAtLeast1000;
    }

    private List<Position> getSurrondingPositions(Position p) {
        List<Position> positions = new ArrayList<>();
        for (int i = p.i - 1; i <= p.i + 1; i++) {
            for (int j = p.j - 1; j <= p.j + 1; j++) {
                if (p.i == i && p.j == j) continue;
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    private static class State {
        Position p;
        int regexIndex;

        public State(int i, int j, int regexIndex) {
            this.p = new Position(i, j);
            this.regexIndex = regexIndex;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return p.i == state.p.i &&
                    p.j == state.p.j &&
                    regexIndex == state.regexIndex;
        }

        public int hashCode() {
            return Objects.hash(p.i, p.j, regexIndex);
        }

        public String toString() {
            return p + "|" + regexIndex;
        }
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public String toString() {
            return i + "," + j;
        }
    }
}
