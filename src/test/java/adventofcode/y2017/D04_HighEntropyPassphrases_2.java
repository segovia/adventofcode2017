package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D04_HighEntropyPassphrases_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D04_HighEntropyPassphrases_2.class);
        assertThat(run(fileInputs.get(0)), is(2L));
        assertThat(run(fileInputs.get(1)), is(3L));
        assertThat(run(fileInputs.get(2)), is(223L));
    }

    private long run(String input) {
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
