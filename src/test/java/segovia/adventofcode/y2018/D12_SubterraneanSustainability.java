package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D12_SubterraneanSustainability {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run("#..#.#..##......###...###", fileInputs.get(0)), is(325));
        assertThat(run("##.#.#.##..#....######..#..#...#.#..#.#.#..###.#.#.#..#.." +
                "###.##.#..#.##.##.#.####..##...##..#..##.#.", fileInputs.get(1)), is(2140));
        assertThat(predictVal(50_000_000_000L), is(1900000000384L));
    }


    private static final int OFFSET = 40;
    private static final int GEN = 20;

    private int run(String initialState, String input) {
        Set<Integer> growSet = Arrays
                .stream(input.split("\\r?\\n"))
                .map(this::toInt)
                .filter(Objects::nonNull)
                .collect(toSet());
        boolean[] state = new boolean[initialState.length() + OFFSET * 2];
        for (int i = 0; i < initialState.length(); i++) {
            state[i + OFFSET] = initialState.charAt(i) == '#';
        }
        boolean[] nextState = new boolean[state.length];
        for (int g = 0; g < GEN; g++) {
            int window = 0;
            for (int i = 0; i < state.length - 2; i++) {
                window = (window % 10000) * 10 + (state[i + 2] ? 1 : 0);
                nextState[i] = growSet.contains(window);
            }
            boolean[] aux = state;
            state = nextState;
            nextState = aux;
        }
        return doSum(state);
    }

    private int doSum(boolean[] state) {
        int sum = 0;
        for (int i = 0; i < state.length; i++) {
            sum += state[i] ? i - OFFSET : 0;
        }
        return sum;
    }

    private Integer toInt(String s) {
        if (s.endsWith(" => .")) return null;
        int val = 0;
        for (int i = 0; i < 5; i++) {
            val = 10 * val + (s.charAt(i) == '#' ? 1 : 0);
        }
        return val;
    }

    private long predictVal(long gen) {
        long genAtLinearStart = 98L;
        long valueAtLinearStart = 4108L;
        long diffBetweenGen = 38L;
        return (gen - genAtLinearStart) * diffBetweenGen + valueAtLinearStart;
    }
}
