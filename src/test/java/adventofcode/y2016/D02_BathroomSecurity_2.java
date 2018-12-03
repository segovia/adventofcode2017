package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D02_BathroomSecurity_2 {

    char[][] keyPad = new char[][]{
            new char[]{'X', 'X', '1', 'X', 'X'},
            new char[]{'X', '2', '3', '4', 'X'},
            new char[]{'5', '6', '7', '8', '9'},
            new char[]{'X', 'A', 'B', 'C', 'X'},
            new char[]{'X', 'X', 'D', 'X', 'X'},
    };

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D02_BathroomSecurity_2.class);
        assertThat(run(fileInputs.get(0)), is("5DB3"));
        assertThat(run(fileInputs.get(1)), is("6BBAD"));
    }

    private String run(String input) {
        int i = 2;
        int j = 0;

        StringBuilder code = new StringBuilder();
        String[] lines = input.split("\\n");
        for (String line : lines) {
            for (int k = 0; k < line.length(); k++) {
                char c = line.charAt(k);
                switch (c) {
                    case 'U':
                        i -= (i > 0 && keyPad[i - 1][j] != 'X') ? 1 : 0;
                        break;
                    case 'D':
                        i += (i < 4 && keyPad[i + 1][j] != 'X') ? 1 : 0;
                        break;
                    case 'L':
                        j -= (j > 0 && keyPad[i][j - 1] != 'X') ? 1 : 0;
                        break;
                    case 'R':
                        j += (j < 4 && keyPad[i][j + 1] != 'X') ? 1 : 0;
                        break;
                }
            }

            code.append(keyPad[i][j]);
        }
        return code.toString();

    }
}
