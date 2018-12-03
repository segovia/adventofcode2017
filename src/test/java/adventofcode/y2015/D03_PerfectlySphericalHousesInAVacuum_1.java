package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D03_PerfectlySphericalHousesInAVacuum_1 {

    @Test
    public void test() throws IOException {
        assertThat(run(">"), is(2));
        assertThat(run("^>v<"), is(4));
        assertThat(run("^v^v^v^v^v"), is(2));
        List<String> fileInputs = Utils.getInputsFromFiles(D03_PerfectlySphericalHousesInAVacuum_1.class);
        assertThat(run(fileInputs.get(0)), is(2081));
    }

    private int run(String input) {
        int curX = 0;
        int curY = 0;
        Set<String> visited = new HashSet<>();
        visited.add(toString(curX, curY));
        for (int i = 0; i < input.length(); i++) {
            switch(input.charAt(i)) {
                case '^':
                    --curY;
                    break;
                case 'v':
                    ++curY;
                    break;
                case '<':
                    --curX;
                    break;
                case '>':
                    ++curX;
                    break;
            }
            visited.add(toString(curX, curY));
        }
        return visited.size();
    }

    private String toString(int curX, int curY) {
        return curX + "," + curY;
    }
}
