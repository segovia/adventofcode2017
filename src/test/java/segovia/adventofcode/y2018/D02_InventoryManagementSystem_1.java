package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D02_InventoryManagementSystem_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("abcdef\nbababc\nabbcde\nabcccd\naabcdd\n\nabcdee\nababab"), is(12L));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(6150L));
    }

    private long run(String input) {
        String[] words = input.split("\\s+");
        int with2 = 0;
        int with3 = 0;
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            int curWith2 = 0;
            int curWith3 = 0;
            for (int i = 0; i < word.length(); i++) {
                int val = counts.getOrDefault(word.charAt(i), 0) + 1;
                counts.put(word.charAt(i), val);
                if (val == 2) ++curWith2;
                else if (val == 4) --curWith3;
                else if (val == 3) {
                    --curWith2;
                    ++curWith3;
                }
            }
            if (curWith2 > 0) ++with2;
            if (curWith3 > 0) ++with3;
            counts.clear();
        }
        return with2 * with3;
    }
}
