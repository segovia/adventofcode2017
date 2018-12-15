package segovia.adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D11_ChronalCharge {

    @Test
    public void test() throws IOException {
        assertThat(getPowerLevel(3, 5, 8), is(4));
        assertThat(getPowerLevel(122, 79, 57), is(-5));
        assertThat(getPowerLevel(217, 196, 39), is(0));
        assertThat(getPowerLevel(101, 153, 71), is(4));

        assertThat(run(18, true), is("33,45,3"));
        assertThat(run(9221, true), is("20,77,3"));
        assertThat(run(18, false), is("90,269,16"));
        assertThat(run(42, false), is("232,251,12"));
        assertThat(run(9221, false), is("143,57,10"));
    }

    private static final int MAP_SIZE = 300;

    private String run(int serial, boolean fixedSize) {
        int[][] sumMap = new int[MAP_SIZE + 1][MAP_SIZE + 1];
        int bestSum = Integer.MIN_VALUE;
        String bestCoords = "";
        for (int y = 1; y <= MAP_SIZE; y++) {
            for (int x = 1; x <= MAP_SIZE; x++) {
                sumMap[y][x] = getPowerLevel(x, y, serial) + sumMap[y - 1][x] + sumMap[y][x - 1] - sumMap[y - 1][x - 1];
                for (int size = fixedSize ? 3 : 1; size <= Math.min(Math.min(y, x), fixedSize ? 3 : MAP_SIZE); size++) {
                    int sum = sumMap[y][x] - sumMap[y - size][x] - sumMap[y][x - size] + sumMap[y - size][x - size];
                    if (sum > bestSum) {
                        bestSum = sum;
                        bestCoords = (x - size + 1) + "," + (y - size + 1) + "," + size;
                    }
                }
            }
        }
        return bestCoords;
    }

    private int getPowerLevel(int x, int y, int serial) {
        return (((x + 10) * y + serial) * (x + 10) / 100) % 10 - 5;
    }
}
