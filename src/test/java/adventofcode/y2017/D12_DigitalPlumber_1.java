package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D12_DigitalPlumber_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D12_DigitalPlumber_1.class);
        assertThat(run(fileInputs.get(0)), is(2));
        assertThat(run(fileInputs.get(1)), is(179));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        Map<String, String[]> map = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" <-> ");
            map.put(parts[0], parts[1].split(", "));
        }
        Set<String> visited = new HashSet<>();
        Deque<String> toVisit = new ArrayDeque<>();
        int groupCount = 0;
        for (String id : map.keySet()) {
            if (visited.contains(id)) continue;
            toVisit.add(id);
            while (!toVisit.isEmpty()) {
                String cur = toVisit.pop();
                if (!visited.add(cur)) continue;
                toVisit.addAll(Arrays.asList(map.get(cur)));
            }
            ++groupCount;
        }
        return groupCount;
    }
}
