package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D22_GridComputing_1 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D22_GridComputing_1.class);
        assertThat(run(fileInputs.get(0),3,3), is(7));
        assertThat(run(fileInputs.get(1),26,38), is(950));
    }

    private int run(String input, int height, int width) throws Exception {
        int[] used = new int[width * height];
        int[] size = new int[width * height];
        String[] lines = input.split("\\n");
        for (int i = 2; i < lines.length; i++) {
            String[] tokens = lines[i].split("\\s+");
            String[] fileNameTokens = tokens[0].split("-");
            int x = Integer.parseInt(fileNameTokens[1].substring(1));
            int y = Integer.parseInt(fileNameTokens[2].substring(1));
            int idx = y * width + x;
            used[idx] = Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 1));
            size[idx] = Integer.parseInt(tokens[1].substring(0, tokens[1].length() - 1));
        }
        int count = 0;
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < size.length; j++) {
                if (i == j || used[i] == 0) continue;
                if (size[j] - used[j] >= used[i]) ++count;
            }
        }
        return count;
    }
}