package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D05_AMazeOfTwistyTrampolines_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D05_AMazeOfTwistyTrampolines_2.class);
        assertThat(run(fileInputs.get(0)), is(10L));
        assertThat(run(fileInputs.get(1)), is(10L));
        assertThat(run(fileInputs.get(2)), is(30513679L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; ++i) nums[i] = Integer.parseInt(lines[i]);

        int count = 0;
        for (int i = 0; i < nums.length; i += (nums[i] >= 3 ? nums[i]-- : nums[i]++)) ++count;
        return count;
    }
}
