import java.util.*;

import static java.util.Arrays.asList;

public class Advent_06_MemoryReallocation_1 {


    private static final List<String> INPUTS = asList(
            "0 2 7 0",
            "10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6");


    public static void main(String[] args) {
        for (String input : INPUTS) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
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
