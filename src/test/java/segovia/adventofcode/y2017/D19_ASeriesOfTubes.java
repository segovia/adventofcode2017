package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D19_ASeriesOfTubes {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D19_ASeriesOfTubes.class);
        assertThat(run(fileInputs.get(0)), is("ABCDEF - 38"));
        assertThat(run(fileInputs.get(1)), is("VEBTPXCHLI - 18702"));
    }

    private String run(String input) {
        String[] map = input.split("\\n");
        int len = map[0].length();
        int curPos = findInitialJ(map[0]);
        int[][] dir = new int[][]{
                new int[]{-1, 0}, // up
                new int[]{0, 1}, // right
                new int[]{1, 0}, // down
                new int[]{0, -1} // left
        };
        int curDir = 2;

        StringBuilder path = new StringBuilder();
        int steps = 1;
        while (true) {
            int i = curPos / len;
            int j = curPos % len;
            char c = map[i].charAt(j);
            if (c == '+') {
                // dir change
                int dirChange = (curDir + 1) % 4;
                if (isValidToContinueAfterDirChange(map, dir, i, j, dirChange)) {
                    curDir = dirChange;
                } else {
                    // other dir must be valid
                    curDir = (curDir + 3) % 4;
                }
            } else if (c >= 'A' && c <= 'Z') {
                // letter
                path.append(c);
            }
            // just carry on
            if (!isValidToContinue(map, dir, i, j, curDir)) {
                return path.toString() + " - " + steps;
            }
            curPos = getPosAfterContinue(map, dir, i, j, curDir);
            ++steps;
        }
    }

    private boolean isValidToContinue(String[] map, int[][] dir, int i, int j, int curDir) {
        int newI = i + dir[curDir][0];
        int newJ = j + dir[curDir][1];
        return isValidPos(map, newI, newJ);
    }

    private boolean isValidToContinueAfterDirChange(String[] map, int[][] dir, int i, int j, int curDir) {
        int newI = i + dir[curDir][0];
        int newJ = j + dir[curDir][1];
        return isValidPos(map, newI, newJ) && map[newI].charAt(newJ) != '+';
    }

    private int getPosAfterContinue(String[] map, int[][] dir, int i, int j, int dirChange) {
        int newI = i + dir[dirChange][0];
        int newJ = j + dir[dirChange][1];
        return newI * map[0].length() + newJ;
    }

    private boolean isValidPos(String[] map, int i, int j) {
        return i >= 0 && i < map.length && j >= 0 && j < map[0].length() && map[i].charAt(j) != ' ';
    }

    private int findInitialJ(String s) {
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == '|') return j;
        }
        return -1;
    }
}
