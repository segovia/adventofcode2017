package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D08_Matchsticks_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D08_Matchsticks_2.class);
        assertThat(run(fileInputs.get(0)), is(19));
        assertThat(run(fileInputs.get(1)), is(2117));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int sum = 0;
        for (String line : lines) sum += countStringLetters(line) - line.length();
        return sum;
    }

    private int countStringLetters(String line) {
        int count = 2;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            count += (c == '\\' || c == '"') ? 2 : 1;
        }
        return count;
    }
}
