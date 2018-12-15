package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D01_InverseCaptcha_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("1212"), is(6L));
        assertThat(run("1221"), is(0L));
        assertThat(run("123425"), is(4L));
        assertThat(run("123123"), is(12L));
        assertThat(run("12131415"), is(4L));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_InverseCaptcha_2.class);
        assertThat(run(fileInputs.get(0)), is(1284L));
    }

    private long run(String input) {
        long sum = 0;
        int len = input.length();
        for (int i = 0; i < len; i++) {
            int nextPos = (i + len / 2) % len;
            if (input.charAt(i) == input.charAt(nextPos)) {
                sum += input.charAt(i) - '0';
            }
        }
        return sum;
    }
}
