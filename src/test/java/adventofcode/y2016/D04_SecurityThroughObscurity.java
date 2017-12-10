package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.Collections.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D04_SecurityThroughObscurity {

    @Test
    public void test() throws IOException {
        assertThat(run(
                "aaaaa-bbb-z-y-x-123[abxyz]\n" +
                        "a-b-c-d-e-f-g-h-987[abcde]\n" +
                        "not-a-real-room-404[oarel]\n" +
                        "totally-real-room-200[decoy]"
        ), is("1514 - "));
        List<String> fileInputs = Utils.getInputsFromFiles(D04_SecurityThroughObscurity.class);
        assertThat(run(fileInputs.get(0)), is("409147 - northpole object storage (991)"));
    }

    private String run(String input) {
        String[] lines = input.split("\\n");
        int sectorIdSum = 0;
        String result = "";
        for (String line : lines) {
            String[] tokens = line.split("-");
            String computedChecksum = computeChecksum(tokens);
            String lastToken = tokens[tokens.length - 1];
            int firstBracket = lastToken.indexOf('[');
            String checksum = lastToken.substring(firstBracket + 1, lastToken.length() - 1);
            if (computedChecksum.equals(checksum)) {
                int sectorId = Integer.parseInt(lastToken.substring(0, firstBracket));
                sectorIdSum += sectorId;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tokens.length - 1; i++) {
                    String token = tokens[i];
                    for (int j = 0; j < token.length(); j++) {
                        sb.append((char) ((token.charAt(j) + sectorId - 'a') % 26 + 'a'));
                    }
                    sb.append(' ');
                }
                String str = sb.toString();
                if (str.contains("northpole")) {
                    result = str + "(" + sectorId + ")";
                }
            }
        }
        return sectorIdSum + " - " + result;
    }

    private String computeChecksum(String[] tokens) {
        // 0 to n-1 tokens are names
        int[] counts = new int[26];
        for (int i = 0; i < tokens.length - 1; i++) {
            String token = tokens[i];
            for (int j = 0; j < token.length(); j++) {
                ++counts[token.charAt(j) - 'a'];
            }
        }
        Map<Integer, String> freqMap = new HashMap<>();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                freqMap.merge(counts[i], String.valueOf((char) (i + 'a')), (oldVal, val) -> oldVal + val);
            }
        }
        List<Integer> keys = new ArrayList<>(freqMap.keySet());
        keys.sort(reverseOrder());
        String computedCheckSum = "";
        for (int i = 0; computedCheckSum.length() < 5; ++i) {
            computedCheckSum += freqMap.get(keys.get(i));
        }
        return computedCheckSum.substring(0, 5);
    }
}
