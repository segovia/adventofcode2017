package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D10_KnotHash_2 {

    private static final int LIST_SIZE = 256;

    @Test
    public void test() throws IOException {
        assertThat(run(""), is("a2582a3a0e66e6e86e3812dcb672a272"));
        assertThat(run("AoC 2017"), is("33efeb34ea91902bb2f59c9920caa6cd"));
        assertThat(run("1,2,3"), is("3efbe78a8d82f29979031a4aa0b16a9d"));
        assertThat(run("1,2,4"), is("63960835bcdc130f0b66d7ff4f6a5a8e"));
        List<String> fileInputs = Utils.getInputsFromFiles(D10_KnotHash_2.class);
        assertThat(run(fileInputs.get(0)), is("70b856a24d586194331398c7fcfa0aaf"));
    }


    public String run(String input) {
        int[] list = makeInitialList();
        int[] actions = toAsciiIntArray(input);
        doKnotting(list, actions);
        int[] xors = makeXorArray(list);
        return toHexaString(xors);
    }

    private int[] makeInitialList() {
        int[] list = new int[LIST_SIZE];
        for (int i = 0; i < LIST_SIZE; i++) list[i] = i;
        return list;
    }

    private String toHexaString(int[] xors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xors.length; i++) {
            sb.append(intToHexa(xors[i] / 16)).append(intToHexa(xors[i] % 16));
        }
        return sb.toString();
    }

    private char intToHexa(int i) {
        return (char) (i >= 10 ? i - 10 + 'a' : i + '0');
    }

    private int[] makeXorArray(int[] list) {
        int[] xors = new int[16];
        for (int i = 0; i < xors.length; i++) {
            for (int j = 0; j < 16; j++) {
                xors[i] ^= list[i * 16 + j];
            }
        }
        return xors;
    }

    private void doKnotting(int[] list, int[] actions) {
        int idx = 0;
        int skip = 0;
        for (int round = 0; round < 64; round++) {
            for (int i = 0; i < actions.length; i++, skip++) {
                reverse(list, idx, actions[i]);
                idx = (idx + actions[i] + skip) % LIST_SIZE;
            }
        }
    }

    private int[] toAsciiIntArray(String input) {
        char[] chars = input.toCharArray();
        int[] asciis = new int[chars.length + 5];
        for (int i = 0; i < chars.length; i++) asciis[i] = (int) chars[i];
        // add suffix actions
        asciis[chars.length] = 17;
        asciis[chars.length + 1] = 31;
        asciis[chars.length + 2] = 73;
        asciis[chars.length + 3] = 47;
        asciis[chars.length + 4] = 23;

        return asciis;
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
