package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D01_ChronalCalibration_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("+1\n+1\n+1"), is(3L));
        assertThat(run("+1\n+1\n-2"), is(0L));
        assertThat(run("-1\n-2\n-3"), is(-6L));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(518L));
    }

    private long run(String input) {
        return Arrays.
                stream(input.split("\\s+")).
                mapToLong(Long::parseLong).
                sum();
    }
}
