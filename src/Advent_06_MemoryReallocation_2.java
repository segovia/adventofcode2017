import java.util.*;

import static java.util.Arrays.asList;

public class Advent_06_MemoryReallocation_2 {


    private static final List<String> INPUTS = asList(
            "0 2 7 0",
            "10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6");


    public static void main(String[] args) {
        for (String input : INPUTS) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
        int[] original = Arrays.stream(input.split("\\s")).mapToInt(Integer::parseInt).toArray();
        LoopStartResult result = findLoopStart(original);
        return result.distTraveled - distanceUntilStart(original, result.startArray);
    }

    private static int distanceUntilStart(int[] original, List<Integer> start) {
        int[] nums = Arrays.copyOf(original, original.length);
        int dist = 0;
        for (; !start.equals(toList(nums)); ++dist) doStep(nums);
        return dist;
    }

    private static LoopStartResult findLoopStart(int[] original) {
        int[] nums = Arrays.copyOf(original, original.length);
        Set<Object> set = new HashSet<>();
        List<Integer> list = toList(nums);
        int dist = 0;
        for (; set.add(list); list = toList(nums), ++dist) doStep(nums);
        return new LoopStartResult(list, dist);
    }

    private static class LoopStartResult {
        List<Integer> startArray;
        int distTraveled;

        public LoopStartResult(List<Integer> startArray, int distTraveled) {
            this.startArray = startArray;
            this.distTraveled = distTraveled;
        }
    }

    private static void doStep(int[] nums) {
        int curLargest = 0;
        for (int i = 1; i < nums.length; i++) if (nums[curLargest] < nums[i]) curLargest = i;
        int rest = nums[curLargest] % nums.length;
        int div = nums[curLargest] / nums.length;
        nums[curLargest] = 0;
        for (int i = 0; i < nums.length; i++) nums[i] += div;
        for (int i = 0; i < rest; i++) ++nums[(curLargest + 1 + i) % nums.length];
    }

    private static List<Integer> toList(int[] nums) {
        List<Integer> list = new ArrayList<>(nums.length);
        for (int num : nums) list.add(num);
        return list;
    }
}
