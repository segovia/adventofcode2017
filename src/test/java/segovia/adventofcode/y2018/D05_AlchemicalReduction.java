package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D05_AlchemicalReduction {

    @Test
    public void test() throws IOException {
        assertThat(run("dabAcCaCBAcCcaDA"), is("10 4"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("10450 4624"));
    }

    private static final int DIST = Math.abs('A' - 'a');

    private String run(String input) {
        StringBuilder sb = reduce(input);
        int bestLen = Integer.MAX_VALUE;
        for(int c = 0; c < 26; ++c) {
            StringBuilder withoutC = new StringBuilder();
            for (int i = 0; i < sb.length(); i++) {
                if (Character.toLowerCase(sb.charAt(i)) - 'a' != c) {
                    withoutC.append(sb.charAt(i));
                }
            }
            bestLen = Math.min(bestLen, reduce(withoutC).length());
        }
        return sb.length() + " " + bestLen;
    }

    private StringBuilder reduce(CharSequence input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (sb.length() > 0 && Math.abs(sb.charAt(sb.length() - 1) - input.charAt(i)) == DIST) {
                sb.setLength(sb.length() - 1);
            } else {
                sb.append(input.charAt(i));
            }
        }
        return sb;
    }
}