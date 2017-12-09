package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D01_InverseCaptcha_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("1122"), is(3L));
        assertThat(run("1111"), is(4L));
        assertThat(run("1234"), is(0L));
        assertThat(run("91212129"), is(9L));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_InverseCaptcha_1.class);
        assertThat(run(fileInputs.get(0)), is(1223L));
    }

    private long run(String input) {
        long sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int nextPos = i + 1 == input.length() ? 0 : (i + 1);
            if (input.charAt(i) == input.charAt(nextPos)) {
                sum += input.charAt(i) - '0';
            }
        }
        return sum;
    }
}
