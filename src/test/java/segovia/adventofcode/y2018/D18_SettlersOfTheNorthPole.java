package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D18_SettlersOfTheNorthPole {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0), 10), is(1147));
        assertThat(run(fileInputs.get(1), 10), is(480150));
        assertThat(run(fileInputs.get(1), 1_000_000_000), is(233020));
    }

    private int run(String input, int rounds) {
        char[][] map = inputToMap(input);
        BidiStateRoundMap bidiMap = new BidiStateRoundMap();
        String lastState = "";
        for (int i = 0; i < rounds; i++) {
            map = simulateStep(map);
            lastState = mapToString(map);
            if (!bidiMap.putIfAbsent(lastState)) break;
        }
        if (bidiMap.size() == rounds) return calcResultNumber(lastState);
        Integer startRound = bidiMap.getRound(lastState);
        int expected = (rounds -1 - startRound) % (bidiMap.size() - startRound) + startRound;
        return calcResultNumber(bidiMap.getState(expected));
    }

    private class BidiStateRoundMap {
        List<String> roundToState = new ArrayList<>();
        Map<String, Integer> stateToRound = new HashMap<>();

        Integer getRound(String state) {
            return stateToRound.get(state);
        }
        String getState(Integer round) {
            return roundToState.get(round);
        }
        boolean putIfAbsent(String state){
            boolean added = null == stateToRound.putIfAbsent(state, roundToState.size());
            if (added) roundToState.add(state);
            return added;
        }
        int size() {
            return roundToState.size();
        }
    }

    private int calcResultNumber(String mapStr) {
        int tree = 0, yard = 0;
        for (int i = 0; i < mapStr.length(); i++) {
            if (mapStr.charAt(i) == '|') ++tree;
            else if (mapStr.charAt(i) == '#') ++yard;
        }
        return tree * yard;
    }

    private char[][] inputToMap(String input) {
        String[] split = input.split("\\r?\\n");
        char[][] map = new char[split.length][];
        for (int i = 0; i < split.length; i++) map[i] = split[i].toCharArray();
        return map;
    }

    private char[][] simulateStep(char[][] map) {
        char[][] next = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                int adjTrees = countAdjMatching(map, i, j, '|');
                int adjLumberYards = countAdjMatching(map, i, j, '#');
                if (c == '.' && adjTrees >= 3) next[i][j] = '|';
                else if (c == '|' && adjLumberYards >= 3) next[i][j] = '#';
                else if (c == '#' && (adjLumberYards == 0 || adjTrees == 0)) next[i][j] = '.';
                else next[i][j] = c;
            }
        }
        map = next;
        return map;
    }

    private String mapToString(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : map) sb.append(row);
        return sb.toString();
    }

    private int countAdjMatching(char[][] map, int posI, int posJ, char c) {
        int count = 0;
        for (int i = posI - 1; i <= posI + 1 && i < map.length; i++) {
            for (int j = posJ - 1; j <= posJ + 1 && j < map[0].length; j++) {
                if (i == posI && j == posJ || i < 0 || j < 0) continue;
                if (map[i][j] == c) ++count;
            }
        }
        return count;
    }
}
