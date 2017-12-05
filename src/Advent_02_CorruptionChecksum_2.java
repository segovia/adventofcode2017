import java.io.IOException;
import java.util.List;

public class Advent_02_CorruptionChecksum_2 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_02_CorruptionChecksum_2.class);
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
        String[] lines = input.split("\\n");
        Long sum = 0L;
        for (String line : lines) {
            String[] tokens = line.split("\\s");

            outer:
            for (int j = 0; j < tokens.length - 1; j++) {
                for (int k = j + 1; k < tokens.length; k++) {
                    Long a = Long.parseLong(tokens[j]);
                    Long b = Long.parseLong(tokens[k]);
                    long max = Math.max(a, b);
                    long min = Math.min(a, b);
                    if (max % min == 0) {
                        sum += max / min;
                        break outer;
                    }
                }
            }
        }
        return sum;
    }
}
