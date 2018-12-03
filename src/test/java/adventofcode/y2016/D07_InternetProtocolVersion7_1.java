package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D07_InternetProtocolVersion7_1 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D07_InternetProtocolVersion7_1.class);
        assertThat(run(fileInputs.get(0)), is(2));
        assertThat(run(fileInputs.get(1)), is(0));
        assertThat(run(fileInputs.get(2)), is(105));
    }

    private int run(String input) throws Exception {
        String[] lines = input.split("\\n");
        int count = 0;

        outer:
        for (String line : lines) {
            String[] parts = line.split("(\\[|])");
            for (int i = 1; i < parts.length; i += 2) {
                if (hasABBA(parts[i])) {
                    continue outer;
                }
            }

            for (int i = 0; i < parts.length; i += 2) {
                if (hasABBA(parts[i])) {
                    ++count;
                    break;
                }
            }
        }
        return count;
    }

    private boolean hasABBA(String s) {
        for (int i = 0; i < s.length() - 3; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            if (a == b) continue;
            if (a == s.charAt(i + 3) && b == s.charAt(i + 2)) return true;
        }
        return false;
    }
}