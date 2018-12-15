package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static segovia.adventofcode.Utils.toIntArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D10_KnotHash_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("3,4,1,5", 5), is(12));
        List<String> fileInputs = Utils.getInputsFromFiles(D10_KnotHash_1.class);
        assertThat(run(fileInputs.get(0), 256), is(37230));
    }


    private int run(String input, int listSize) {
        int[] list = new int[listSize];
        for (int i = 0; i < listSize; i++) list[i] = i;

        int[] actions = toIntArray(input.split(","));
        int idx = 0;
        for (int skip = 0; skip < actions.length; skip++) {
            reverse(list, idx, actions[skip]);
            idx = (idx + actions[skip] + skip) % listSize;
        }
        return list[0] * list[1];
    }

    private void reverse(int[] list, int idx, int length) {
        for (int i = 0; i < length / 2; i++) {
            int a = (idx + i) % list.length;
            int b = (idx + length - i - 1) % list.length;
            int aux = list[a];
            list[a] = list[b];
            list[b] = aux;
        }
    }
}
