package segovia.adventofcode.y2016;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D07_InternetProtocolVersion7_2 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D07_InternetProtocolVersion7_2.class);
        assertThat(run(fileInputs.get(0)), is(0));
        assertThat(run(fileInputs.get(1)), is(3));
        assertThat(run(fileInputs.get(2)), is(258));
    }

    private int run(String input) throws Exception {
        String[] lines = input.split("\\n");
        int count = 0;

        for (String line : lines) {
            String[] parts = line.split("(\\[|])");
            Set<String> babs = new HashSet<>();
            for (int i = 0; i < parts.length; i += 2) {
                babs.addAll(getBABs(parts[i]));
            }

            for (int i = 1; i < parts.length; i += 2) {
                final String part = parts[i];
                if (babs.stream().anyMatch(part::contains)) {
                    ++count;
                    break;
                }
            }
        }
        return count;
    }

    private Set<String> getBABs(String s) {
        Set<String> babs = new HashSet<>();
        for (int i = 0; i < s.length() - 2; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            if (a == b) continue;
            if (a == s.charAt(i + 2)) {
                babs.add(new String(new char[]{b, a, b}));
            }
        }
        return babs;
    }
}