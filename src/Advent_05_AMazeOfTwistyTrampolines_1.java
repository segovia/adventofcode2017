import java.io.IOException;
import java.util.List;

public class Advent_05_AMazeOfTwistyTrampolines_1 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_05_AMazeOfTwistyTrampolines_1.class);
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
        String[] lines = input.split("\\n");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; ++i) nums[i] = Integer.parseInt(lines[i]);

        int count = 0;
        for (int i = 0; i < nums.length; i += nums[i]++) ++count;
        return count;
    }
}
