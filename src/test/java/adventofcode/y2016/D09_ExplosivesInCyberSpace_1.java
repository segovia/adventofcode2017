package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D09_ExplosivesInCyberSpace_1 {

    private static final int HEIGHT = 6;
    private static final int WIDTH = 50;

    @Test
    public void test() throws Exception {
        assertThat(run("ADVENT"), is(6));
        assertThat(run("A(1x5)BC"), is(7));
        assertThat(run("(3x3)XYZ"), is(9));
        assertThat(run("A(2x2)BCD(2x2)EFG"), is(11));
        assertThat(run("(6x1)(1x3)A"), is(6));
        assertThat(run("X(8x2)(3x3)ABCY"), is(18));

        List<String> fileInputs = Utils.getInputsFromFiles(D09_ExplosivesInCyberSpace_1.class);
        assertThat(run(fileInputs.get(0)), is(6));
    }

    private int run(String input) throws Exception {
        int size = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                int end = input.indexOf(')', i);
                String marker = input.substring(i + 1, end);
                int[] vals = Utils.toIntArray(marker.split("x"));
                i = end + vals[0];
                size += vals[0] * vals[1];
            } else {
                ++size;
            }
        }
        return size;
    }

}