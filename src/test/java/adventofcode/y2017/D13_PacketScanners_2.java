package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D13_PacketScanners_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D13_PacketScanners_2.class);
        assertThat(run(fileInputs.get(0)), is(10));
        assertThat(run(fileInputs.get(1)), is(3941460));
    }

    private int run(String input) {
        String[] linesStr = input.split("\\n");
        int[][] lines = new int[linesStr.length][2];
        for (int i = 0; i < linesStr.length; i++) {
            lines[i] = Utils.toIntArray(linesStr[i].split(": "));
        }

        for (int i = 0; true; i++) {
            if (!isCaught(lines, i)) return i;
        }
    }

    private boolean isCaught(int[][] lines, int delay) {
        for (int[] vals : lines) {
            if (vals[1] == 1 || (vals[0] + delay) % (2 * vals[1] - 2) == 0) {
                return true;
            }
        }
        return false;
    }
}
