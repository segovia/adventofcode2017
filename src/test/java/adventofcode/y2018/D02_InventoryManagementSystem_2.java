package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D02_InventoryManagementSystem_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("abcde\nfghij\nklmno\npqrst\nfguij\n\naxcye\nwvxyz"), is("fgij"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("rteotyxzbodglnpkudawhijsc"));
    }

    private String run(String input) {
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            String a = words[i];
            for (int j = i + 1; j < words.length; j++) {
                String b = words[j];
                for (int k = 0; k < a.length(); k++) { // we assume words have same length
                    if (a.charAt(k) == b.charAt(k)) sb.append(a.charAt(k));
                }
                if (sb.length() == a.length() - 1) return sb.toString();
                sb.setLength(0);
            }
        }
        return "oops";
    }
}
