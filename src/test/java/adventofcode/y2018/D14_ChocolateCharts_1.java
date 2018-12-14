package adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D14_ChocolateCharts_1 {

    @Test
    public void test() throws IOException {
        assertThat(run(9), is("5158916779"));
        assertThat(run(5), is("0124515891"));
        assertThat(run(18), is("9251071085"));
        assertThat(run(2018), is("5941429882"));
        assertThat(run(157901), is("9411137133"));
    }

    private String run(int input) {
        int targetSize = input + 10;
        List<Integer> nums = new ArrayList<>(targetSize);
        nums.add(3);
        nums.add(7);
        int a = 0;
        int b = 1;
        while (nums.size() < targetSize) {
            int sum = nums.get(a) + nums.get(b);
            if (sum >= 10) {
                nums.add(sum / 10);
            }
            nums.add(sum % 10);
            a = (a + nums.get(a) + 1) % nums.size();
            b = (b + nums.get(b) + 1) % nums.size();
        }
        return nums.subList(input, targetSize)
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
