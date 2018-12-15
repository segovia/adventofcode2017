package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static segovia.adventofcode.Utils.swap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D09_AllInASingleNight {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D09_AllInASingleNight.class);
        assertThat(run(fileInputs.get(0), true), is(605));
        assertThat(run(fileInputs.get(1), true), is(117));
        assertThat(run(fileInputs.get(0), false), is(982));
        assertThat(run(fileInputs.get(1), false), is(909));
    }

    private int run(String input, boolean shortest) {
        String[] lines = input.split("\\n");
        Map<String, Integer> nameIdxMap = new HashMap<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s");
            addIdx(nameIdxMap, tokens[0]);
            addIdx(nameIdxMap, tokens[2]);
        }

        int[][] distMap = new int[nameIdxMap.size()][nameIdxMap.size()];
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            int a = nameIdxMap.get(tokens[0]);
            int b = nameIdxMap.get(tokens[2]);
            distMap[a][b] = Integer.parseInt(tokens[4]);
            distMap[b][a] = distMap[a][b];
        }

        int[] path = new int[nameIdxMap.size()];
        for (int i = 0; i < path.length; i++) path[i] = i;
        return bestDist(distMap, path, 0, shortest);
    }

    private void addIdx(Map<String, Integer> nameIdxMap, String name) {
        nameIdxMap.computeIfAbsent(name, k -> nameIdxMap.size());
    }

    private int bestDist(int[][] distMap, int[] path, int depth, boolean shortest) {
        if (depth == path.length) return 0;

        int bestDist = shortest ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (int i = depth; i < path.length; i++) {
            swap(path, depth, i);
            int dist = depth > 0 ? distMap[path[depth]][path[depth - 1]] : 0;
            dist += bestDist(distMap, path, depth + 1, shortest);
            bestDist = shortest ? Math.min(bestDist, dist) : Math.max(bestDist, dist);
            swap(path, depth, i);
        }
        return bestDist;
    }

}