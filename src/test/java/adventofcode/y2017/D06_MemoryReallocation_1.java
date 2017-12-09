package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D06_MemoryReallocation_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("0 2 7 0"), is(5L));
        assertThat(run("10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6"), is(14029L));
    }

    private long run(String input) {
        int[] nums = Arrays.stream(input.split("\\s")).mapToInt(Integer::parseInt).toArray();

        Set<Object> set = new HashSet<>();
        set.add(toList(nums));
        for (int count = 1; true; ++count) {
            System.out.println(Arrays.toString(nums));
            int curLargest = 0;
            for (int i = 1; i < nums.length; i++) if (nums[curLargest] < nums[i]) curLargest = i;
            int rest = nums[curLargest] % nums.length;
            int div = nums[curLargest] / nums.length;
            nums[curLargest] = 0;
            for (int i = 0; i < nums.length; i++) nums[i] += div;
            for (int i = 0; i < rest; i++) ++nums[(curLargest + 1 + i) % nums.length];
            if (!set.add(toList(nums))) {
                return count;
            }
        }
    }

    private static List<Integer> toList(int[] nums) {
        List<Integer> list = new ArrayList<>(nums.length);
        for (int num : nums) list.add(num);
        return list;
    }
}
