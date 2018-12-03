package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D09_ExplosivesInCyberSpace_2 {

    @Test
    public void test() throws Exception {
        assertThat(run("(3x3)XYZ"), is(9L));
        assertThat(run("X(8x2)(3x3)ABCY"), is(20L));
        assertThat(run("(27x12)(20x12)(13x14)(7x10)(1x12)A"), is(241920L));
        assertThat(run("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"), is(445L));

        List<String> fileInputs = Utils.getInputsFromFiles(D09_ExplosivesInCyberSpace_2.class);
        assertThat(run(fileInputs.get(0)), is(11558231665L));
    }

    private long run(String input) throws Exception {
        return run(input, 0, input.length());
    }

    private long run(String input, int start, int end) throws Exception {
        long size = 0;
        for (int i = start; i < end; i++) {
            char c = input.charAt(i);
            if (c == '(') {
                int endOfMarker = input.indexOf(')', i);
                int[] vals = Utils.toIntArray(input.substring(i + 1, endOfMarker).split("x"));
                i = endOfMarker + vals[0];
                size += vals[1] * run(input, endOfMarker + 1, i + 1);
            } else {
                ++size;
            }
        }
        return size;
    }

}