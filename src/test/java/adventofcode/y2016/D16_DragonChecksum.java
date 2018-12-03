package adventofcode.y2016;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D16_DragonChecksum {

    @Test
    public void test() throws Exception {
        assertThat(run("10000", 20), is("01100"));
        assertThat(run("01111010110010011", 272), is("00100111000101111"));
        assertThat(run("01111010110010011", 35651584), is("11101110011100110"));
    }

    private String run(String input, int targetLen) throws Exception {
        char[] bits = input.toCharArray();
        while (bits.length < targetLen) {
            char[] next = Arrays.copyOf(bits, 2 * bits.length + 1);
            next[bits.length] = '0';
            for (int i = 0; i < bits.length; i++) {
                next[next.length - 1 - i] = bits[i] == '0' ? '1' : '0';
            }
            bits = next;
        }

        char[] checkSumInput = Arrays.copyOf(bits, targetLen);
        char[] checkSum;
        do {
            checkSum = new char[checkSumInput.length / 2];
            for (int i = 0; i < checkSumInput.length; i += 2) {
                checkSum[i / 2] = checkSumInput[i] == checkSumInput[i + 1] ? '1' : '0';
            }
            checkSumInput = checkSum;
        } while (checkSum.length % 2 == 0);
        return new String(checkSum);
    }
}