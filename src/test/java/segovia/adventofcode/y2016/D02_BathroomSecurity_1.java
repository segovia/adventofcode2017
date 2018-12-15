package segovia.adventofcode.y2016;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D02_BathroomSecurity_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D02_BathroomSecurity_1.class);
        assertThat(run(fileInputs.get(0)), is("1985"));
        assertThat(run(fileInputs.get(1)), is("44558"));
    }

    private String run(String input) {
        int i = 1;
        int j = 1;

        StringBuilder code = new StringBuilder();
        String[] lines = input.split("\\n");
        for (String line : lines) {
            for (int k = 0; k < line.length(); k++) {
                char c = line.charAt(k);
                switch (c) {
                    case 'U':
                        i -= (i > 0) ? 1 : 0;
                        break;
                    case 'D':
                        i += (i < 2) ? 1 : 0;
                        break;
                    case 'L':
                        j -= (j > 0) ? 1 : 0;
                        break;
                    case 'R':
                        j += (j < 2) ? 1 : 0;
                        break;
                }
            }

            code.append(i * 3 + j + 1);
        }
        return code.toString();

    }
}
