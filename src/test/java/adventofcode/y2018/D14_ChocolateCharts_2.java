package adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D14_ChocolateCharts_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("51589"), is(9));
        assertThat(run("01245"), is(5));
        assertThat(run("92510"), is(18));
        assertThat(run("59414"), is(2018));
        assertThat(run("157901"), is(20317612));
    }

    private int run(String input) {
        List<Integer> nums = new ArrayList<>();
        nums.add(3);
        nums.add(7);
        int a = 0;
        int b = 1;
        while (true) {
            int sum = nums.get(a) + nums.get(b);
            if (sum >= 10) {
                nums.add(sum / 10);
                if (foundInput(input, nums)) {
                    return nums.size() - input.length();
                }
            }
            nums.add(sum % 10);
            if (foundInput(input, nums)) {
                return nums.size() - input.length();
            }
            a = (a + nums.get(a) + 1) % nums.size();
            b = (b + nums.get(b) + 1) % nums.size();
        }
    }

    private boolean foundInput(String input, List<Integer> nums) {
        if (nums.size() < input.length()) return false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) - '0' != nums.get(nums.size() - input.length() + i)) {
                return false;
            }
        }
        return true;
    }
}
