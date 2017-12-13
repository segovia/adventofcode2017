package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D08_TwoFactorAuthentication {

    private static final int HEIGHT = 6;
    private static final int WIDTH = 50;

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D08_TwoFactorAuthentication.class);
        assertThat(run(fileInputs.get(0)), is(6));
        assertThat(run(fileInputs.get(1)), is(115));
    }

    private int run(String input) throws Exception {
        long[] display = new long[HEIGHT];
        String[] lines = input.split("\\n");
        for (String line : lines) {
            System.out.println(line);
            String[] tokens = line.split(" ");
            if ("rect".equals(tokens[0])) {
                int[] ab = Utils.toIntArray(tokens[1].split("x"));
                for (int i = 0; i < ab[1]; i++) {
                    display[i] |= (1L << ab[0]) - 1L; // with length full of ones
                }
            } else {
                int fixed = Integer.parseInt(tokens[2].substring(2, tokens[2].length()));
                int offset = Integer.parseInt(tokens[4]) % WIDTH;
                if ("row".equals(tokens[1])) {
                    display[fixed] = rotate(display[fixed], WIDTH, offset);
                } else {
                    long cols = 0L;
                    for (int i = 0; i < HEIGHT; i++) {
                        long val = display[i] >>> fixed & 1L;
                        cols |= val << i;
                    }
                    cols = rotate(cols, HEIGHT, offset);
                    for (int i = 0; i < HEIGHT; i++) {
                        display[i] &= ~(1L << fixed); // reset bit
                        long val = (cols >>> i) & 1L;
                        display[i] |= val << fixed;
                    }
                }
            }
            print(display);
        }
        int count = 0;
        for (int i = 0; i < HEIGHT; i++) count += Long.bitCount(display[i]);
        return count;
    }

    private long rotate(long input, int size, int offset) {
        long mask = (1L << size) - 1L;
        long leftSide = input >>> size - offset;
        return (input << offset) & mask | leftSide;
    }

    private void print(long[] display) {
        for (long line : display) {
            System.out.println(new StringBuilder(String.format("%" + WIDTH + "s", Long.toBinaryString(line))
                                                         .replace('0', ' ')
                                                         .replace('1', '*'))
                                       .reverse()
                                       .toString()
            );
        }
    }
}