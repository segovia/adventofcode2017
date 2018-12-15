package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static segovia.adventofcode.Utils.toIntArray;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D24_ElectroMagneticMoat_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D24_ElectroMagneticMoat_2.class);
        assertThat(run(fileInputs.get(0)), is(19));
        assertThat(run(fileInputs.get(1)), is(1799));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        Map<Integer, Set<Component>> pinToComponentMap = new HashMap<>();
        for (String line : lines) {
            Component c = new Component(line);
            pinToComponentMap.computeIfAbsent(c.pinsOnA, k -> new HashSet<>()).add(c);
            pinToComponentMap.computeIfAbsent(c.pinsOnB, k -> new HashSet<>()).add(c);
        }
        Result best = new Result(0, 0);
        recurse(0, new HashSet<>(), pinToComponentMap, new Result(0, 0), best);
        return best.strength;
    }

    private void recurse(int curPins, Set<Component> used, Map<Integer, Set<Component>> pinToComponentMap, Result cur, Result best) {
        Set<Component> nextComponents = pinToComponentMap.get(curPins).stream()
                .filter(c -> !used.contains(c))
                .collect(toSet());
        if (nextComponents.isEmpty()) {
            if (cur.len > best.len || cur.len == best.len && cur.strength > best.strength) {
                best.len = cur.len;
                best.strength = cur.strength;
            }
            return;
        }
        for (Component c : nextComponents) {
            int otherPins = c.pinsOnA == curPins ? c.pinsOnB : c.pinsOnA;
            used.add(c);
            cur.strength += c.pinsOnA + c.pinsOnB;
            cur.len++;
            recurse(otherPins, used, pinToComponentMap, cur, best);
            used.remove(c);
            cur.strength -= c.pinsOnA + c.pinsOnB;
            cur.len--;
        }
    }

    private static class Component {
        int pinsOnA;
        int pinsOnB;

        Component(String line) {
            int[] pins = toIntArray(line.split("/"));
            pinsOnA = Math.min(pins[0], pins[1]);
            pinsOnB = Math.max(pins[0], pins[1]);
        }

        @Override
        public String toString() {
            return "(" + pinsOnA + "_" + pinsOnB + ")";
        }
    }

    private static class Result {
        int len;
        int strength;

        public Result(int len, int strength) {
            this.len = len;
            this.strength = strength;
        }
    }

}
