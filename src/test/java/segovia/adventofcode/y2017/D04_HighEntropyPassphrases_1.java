package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D04_HighEntropyPassphrases_1 {


    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D04_HighEntropyPassphrases_1.class);
        assertThat(run(fileInputs.get(0)), is(2L));
        assertThat(run(fileInputs.get(1)), is(5L));
        assertThat(run(fileInputs.get(2)), is(451L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        int invalidCount = 0;
        for (String line : lines) {
            String[] words = line.split("\\s");
            Set<Object> set = new HashSet<>();
            for (String word : words) {
                if (!set.add(word)) {
                    ++invalidCount;
                    break;
                }
            }
        }
        return lines.length - invalidCount;
    }
}
