package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adventofcode.Utils.swap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D13_KnightsOfTheDinnerTable {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D13_KnightsOfTheDinnerTable.class);
        assertThat(run(fileInputs.get(0), false), is(330));
        assertThat(run(fileInputs.get(1), false), is(733));
        assertThat(run(fileInputs.get(1), true), is(725));
    }

    private int run(String input, boolean addSelf) {
        String[] lines = input.split("\\n");
        Map<String, Integer> nameToIndex = new HashMap<>();
        int[][] deltaMap = new int[20][20];
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            String firstName = tokens[0];
            String lastName = tokens[10].substring(0, tokens[10].length() - 1);
            int delta = Integer.parseInt(tokens[3]) * ("gain".equals(tokens[2]) ? 1 : -1);
            deltaMap[nameToIndex.computeIfAbsent(firstName, k -> nameToIndex.size())][
                    nameToIndex.computeIfAbsent(lastName, k -> nameToIndex.size())] = delta;
        }
        int[] positions = new int[nameToIndex.size() + (addSelf ? 1 : 0)];
        for (int i = 1; i < positions.length; i++) positions[i] = i;

        return findMax(deltaMap, positions, 1);
    }

    private int findMax(int[][] deltaMap, int[] positions, int depth) {
        if (positions.length == depth) {
            return deltaMap[positions[depth - 1]][positions[0]] +
                    deltaMap[positions[0]][positions[depth - 1]];
        }
        int bestCost = Integer.MIN_VALUE;
        for (int i = depth; i < positions.length; i++) {
            swap(positions, depth, i);
            int cost = deltaMap[positions[depth - 1]][positions[depth]] +
                    deltaMap[positions[depth]][positions[depth - 1]];
            cost += findMax(deltaMap, positions, depth + 1);
            bestCost = Math.max(bestCost, cost);
            swap(positions, depth, i);
        }
        return bestCost;
    }
}