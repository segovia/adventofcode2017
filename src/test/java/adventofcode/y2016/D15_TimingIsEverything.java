package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D15_TimingIsEverything {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D15_TimingIsEverything.class);
        assertThat(run(fileInputs.get(0)), is(5));
        assertThat(run(fileInputs.get(1)), is(376777));
        assertThat(run(fileInputs.get(2)), is(3903937));
    }

    private int run(String input) throws Exception {
        String[] lines = input.split("\\n");
        int[] discSizes = new int[lines.length];
        int[] discInitPos = new int[lines.length];

        int largestDiskIdx = 0;
        for (int i = 0; i < lines.length; i++) {
            String[] tokens = lines[i].split("\\s");
            discSizes[i] = Integer.parseInt(tokens[3]);
            discInitPos[i] = Integer.parseInt(tokens[11].substring(0, tokens[11].length() - 1));
            if (discSizes[largestDiskIdx] < discSizes[i]) largestDiskIdx = i;
        }

        int offset = (discInitPos[largestDiskIdx] + largestDiskIdx + 1) % discSizes[largestDiskIdx];
        if (offset > 0) offset = discSizes[largestDiskIdx] - offset;
        int step = discSizes[largestDiskIdx];
        for (int i = 0; true; i++) {
            int t = i * step + offset;
            boolean ok = true;
            for (int j = 0; j < discSizes.length; j++) {
                if ((t + discInitPos[j] + j + 1) % discSizes[j] != 0) {
                    ok = false;
                    break;
                }
            }
            if (ok) return t;
        }
    }
}