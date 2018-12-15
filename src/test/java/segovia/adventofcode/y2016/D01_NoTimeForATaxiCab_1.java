package segovia.adventofcode.y2016;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D01_NoTimeForATaxiCab_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("R2, L3"), is(5));
        assertThat(run("R2, R2, R2"), is(2));
        assertThat(run("R5, L5, R5, R3"), is(12));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_NoTimeForATaxiCab_1.class);
        assertThat(run(fileInputs.get(0)), is(246));
    }

    private int run(String input) {
        int coordX = 0;
        int coordY = 0;
        int[] dirX = {0, 1, 0, -1};
        int[] dirY = {1, 0, -1, 0};
        int dir = 0;

        String[] turns = input.split(", ");
        for (String turn : turns) {
            char c = turn.charAt(0);
            int step = Integer.parseInt(turn.substring(1, turn.length()));
            dir = c == 'R' ? (dir + 1) % 4 : (dir + 3) % 4;
            coordX += dirX[dir] * step;
            coordY += dirY[dir] * step;
        }
        return Math.abs(coordX) + Math.abs(coordY);
    }
}
