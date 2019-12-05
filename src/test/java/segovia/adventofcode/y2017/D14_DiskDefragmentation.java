package segovia.adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D14_DiskDefragmentation {

    private final D10_KnotHash_2 knotHash = new D10_KnotHash_2();

    @Test
    public void testBitCount() throws IOException {
        assertThat(getBitCount("flqrgnkx"), is(8108));
        assertThat(getBitCount("oundnydw"), is(8106));
    }

    @Test
    public void testRegionCount() throws IOException {
        assertThat(countRegions("flqrgnkx"), is(1242));
        assertThat(countRegions("oundnydw"), is(1164));
    }

    @Test
    public void testAuxMethods() throws IOException {
        assertThat(knotHash.run("flqrgnkx-0"), is("d4f76bdcbf838f8416ccfa8bc6d1f9e6"));
        assertThat(hexToBinaryString("d4f76bdcbf838f8416ccfa8bc6d1f9e6").binaryString.substring(0, 16),
                   is("##.#.#..####.###"));
    }


    private int getBitCount(String input) {
        int totalBitCount = 0;
        for (int i = 0; i < 128; i++) {
            String knotHash = this.knotHash.run(input + "-" + i);
            HexToStringResult hexToStringResult = hexToBinaryString(knotHash);
            totalBitCount += hexToStringResult.bitCount;
        }
        return totalBitCount;
    }

    private int countRegions(String input) {
        String[] map = makeMap(input);

        Set<Integer> visited = new HashSet<>();
        int groupCount = 0;
        for (int pos = 0; pos < 128 * 128; pos++) {
            if (visited.contains(pos) || map[pos / 128].charAt(pos % 128) != '#') continue;

            Deque<Integer> stack = new ArrayDeque<>();
            stack.add(pos);
            while (!stack.isEmpty()) {
                int cur = stack.pop();
                if (!visited.add(cur)) continue;
                int i = cur / 128;
                int j = cur % 128;

                if (i > 0 && map[i - 1].charAt(j) == '#') stack.push(toPos(i - 1, j));
                if (i < 127 && map[i + 1].charAt(j) == '#') stack.push(toPos(i + 1, j));
                if (j > 0 && map[i].charAt(j - 1) == '#') stack.push(toPos(i, j - 1));
                if (j < 127 && map[i].charAt(j + 1) == '#') stack.push(toPos(i, j + 1));
            }
            ++groupCount;
        }
        return groupCount;
    }

    private int toPos(int i, int j) {
        return i * 128 + j;
    }

    private String[] makeMap(String input) {
        String[] map = new String[128];
        for (int i = 0; i < 128; i++) {
            String knotHash = this.knotHash.run(input + "-" + i);
            HexToStringResult hexToStringResult = hexToBinaryString(knotHash);
            map[i] = hexToStringResult.binaryString;
        }
        return map;
    }

    private HexToStringResult hexToBinaryString(String hex) {
        StringBuilder sb = new StringBuilder();
        int bitCount = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int val = (c >= '0' && c <= '9') ? c - '0' : 10 + c - 'a';
            sb.append(String.format("%4s", Integer.toBinaryString(val))
                              .replace('0', '.')
                              .replace(' ', '.')
                              .replace('1', '#'));
            bitCount += Integer.bitCount(val);
        }
        return new HexToStringResult(bitCount, sb.toString());
    }

    private static class HexToStringResult {
        int bitCount;
        String binaryString;

        public HexToStringResult(int bitCount, String binaryString) {
            this.bitCount = bitCount;
            this.binaryString = binaryString;
        }
    }
}
