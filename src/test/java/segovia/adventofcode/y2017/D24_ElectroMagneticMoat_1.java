package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static segovia.adventofcode.Utils.toIntArray;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D24_ElectroMagneticMoat_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D24_ElectroMagneticMoat_1.class);
        assertThat(run(fileInputs.get(0)), is(31));
        assertThat(run(fileInputs.get(1)), is(1859));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        Map<Integer, Set<Component>> pinToComponentMap = new HashMap<>();
        for (String line : lines) {
            Component c = new Component(line);
            pinToComponentMap.computeIfAbsent(c.pinsOnA, k -> new HashSet<>()).add(c);
            pinToComponentMap.computeIfAbsent(c.pinsOnB, k -> new HashSet<>()).add(c);
        }
        return recurse(0, new HashSet<>(), pinToComponentMap);
    }

    private int recurse(int curPins, Set<Component> used, Map<Integer, Set<Component>> pinToComponentMap) {
        Set<Component> nextComponents = pinToComponentMap.get(curPins).stream()
                .filter(c -> !used.contains(c))
                .collect(toSet());
        if (nextComponents.isEmpty()) {
            return 0;
        }
        int max = 0;
        for (Component c : nextComponents) {
            int otherPins = c.pinsOnA == curPins ? c.pinsOnB : c.pinsOnA;
            used.add(c);
            max = Math.max(max, c.pinsOnA + c.pinsOnB + recurse(otherPins, used, pinToComponentMap));
            used.remove(c);
        }
        return max;
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
            return pinsOnA + ", " + pinsOnB;
        }
    }
}
