package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D19_MedicineForRudoplh_2 {

    @Test
    @Ignore("This takes forever...")
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D19_MedicineForRudoplh_2.class);
        assertThat(run(fileInputs.get(0)), is(6));
        assertThat(run(fileInputs.get(1)), is(6));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        Map<String, String> map = new HashMap<>();
        int longestKey = 0;
        for (int i = 0; i < lines.length - 2; i++) {
            String[] tokens = lines[i].split(" => ");
            map.put(tokens[1], tokens[0]);
            longestKey = Math.max(longestKey, tokens[1].length());
        }

        String mol = lines[lines.length - 1];
        bestLen = Integer.MAX_VALUE;
        hits = 0L;
        largestLen = 0;
        return shortestPath(map, longestKey, mol, new HashMap<>(), 0, 0);
    }

    long hits;
    int bestLen;
    int largestLen;

    private int shortestPath(Map<String, String> map, int longestKey, String s, Map<String, Integer> best, int len, int startIndex) {
        if (len > largestLen) {
            largestLen = len;
        }
        if ("e".equals(s)) {
            if (len < bestLen) {
                bestLen = len;
            }
            return 0;
        }
        Integer dist = best.get(s);
        if (dist != null) {
            ++hits;
            if (dist != Integer.MAX_VALUE && len + dist < bestLen) {
                bestLen = len + dist;
            }
            return dist;
        }
        dist = Integer.MAX_VALUE;
        for (int i = startIndex; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < longestKey && i + j < s.length(); j++) {
                sb.append(s.charAt(i + j));
                String val = map.get(sb.toString());
                if (val == null) continue;

                int result = shortestPath(map,
                                          longestKey,
                                          s.substring(0, i) + val + s.substring(i + j + 1, s.length()),
                                          best,
                                          len + 1,
                                          Math.max(0, i - longestKey));
                if (result != Integer.MAX_VALUE && result + 1 < dist) {
                    dist = result + 1;
                }
            }
        }
        best.put(s, dist);
        return dist;
    }
}