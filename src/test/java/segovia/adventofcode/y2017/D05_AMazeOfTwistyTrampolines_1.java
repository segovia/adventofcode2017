package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D05_AMazeOfTwistyTrampolines_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D05_AMazeOfTwistyTrampolines_1.class);
        assertThat(run(fileInputs.get(0)), is(5L));
        assertThat(run(fileInputs.get(1)), is(5L));
        assertThat(run(fileInputs.get(2)), is(391540L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; ++i) nums[i] = Integer.parseInt(lines[i]);

        int count = 0;
        for (int i = 0; i < nums.length; i += nums[i]++) ++count;
        return count;
    }
}
