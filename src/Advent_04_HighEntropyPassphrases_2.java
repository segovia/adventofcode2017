import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent_04_HighEntropyPassphrases_2 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_04_HighEntropyPassphrases_2.class);
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static long run(String input) {
        String[] lines = input.split("\\n");
        int invalidCount = 0;
        for (String line : lines) {
            String[] words = line.split("\\s");
            Set<Object> set = new HashSet<>();
            for (String word : words) {
                char[] wordChars = word.toCharArray();
                Arrays.sort(wordChars);
                if (!set.add(new String(wordChars))) {
                    ++invalidCount;
                    break;
                }
            }
        }
        return lines.length - invalidCount;
    }
}
