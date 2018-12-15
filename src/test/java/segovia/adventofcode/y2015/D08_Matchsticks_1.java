package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D08_Matchsticks_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D08_Matchsticks_1.class);
        assertThat(run(fileInputs.get(0)), is(12));
        assertThat(run(fileInputs.get(1)), is(1371));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int sum = 0;
        for (String line : lines) sum += line.length() - countCodeLetters(line);
        return sum;
    }

    private int countCodeLetters(String line) {
        int count = 0;
        for (int i = 1; i < line.length() - 1; i++) {
            char c = line.charAt(i);
            ++count;
            if (c == '\\') i += (line.charAt(i + 1) == 'x') ? 3 : 1;
        }
        return count;
    }
}
