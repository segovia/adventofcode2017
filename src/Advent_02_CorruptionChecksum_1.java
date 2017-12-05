import java.io.IOException;
import java.util.List;

public class Advent_02_CorruptionChecksum_1 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_02_CorruptionChecksum_1.class);
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
        String[] lines = input.split("\\n");
        Long sum = 0L;
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            Long min = Long.MAX_VALUE;
            Long max = Long.MIN_VALUE;
            for (String token : tokens) {
                Long num = Long.parseLong(token);
                min = Math.min(num, min);
                max = Math.max(num, max);
            }
            sum += max - min;
        }
        return sum;
    }
}
