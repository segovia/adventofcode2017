package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D03_PerfectlySphericalHousesInAVacuum_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("^v"), is(3));
        assertThat(run("^>v<"), is(3));
        assertThat(run("^v^v^v^v^v"), is(11));
        List<String> fileInputs = Utils.getInputsFromFiles(D03_PerfectlySphericalHousesInAVacuum_2.class);
        assertThat(run(fileInputs.get(0)), is(2341));
    }

    private int run(String input) {
        int[] curX = {0, 0};
        int[] curY = {0, 0};
        Set<String> visited = new HashSet<>();
        visited.add(toString(curX[0], curY[0]));
        for (int i = 0; i < input.length(); i++) {
            switch(input.charAt(i)) {
                case '^':
                    --curY[i % 2];
                    break;
                case 'v':
                    ++curY[i % 2];
                    break;
                case '<':
                    --curX[i % 2];
                    break;
                case '>':
                    ++curX[i % 2];
                    break;
            }
            visited.add(toString(curX[i % 2], curY[i % 2]));
        }
        return visited.size();
    }

    private String toString(int curX, int curY) {
        return curX + "," + curY;
    }
}
