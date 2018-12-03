package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D12_DigitalPlumber_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D12_DigitalPlumber_2.class);
        assertThat(run(fileInputs.get(0)), is(6));
        assertThat(run(fileInputs.get(1)), is(169));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        Map<String, String[]> map = new HashMap<>();
        for (String line : lines){
            String[] parts = line.split(" <-> ");
            map.put(parts[0], parts[1].split(", "));
        }
        Set<String> connected = new HashSet<>();
        Deque<String> toVisit = new ArrayDeque<>();
        toVisit.add("0");
        while(!toVisit.isEmpty()) {
            String cur = toVisit.pop();
            if (!connected.add(cur)) {
                continue;
            }
            toVisit.addAll(Arrays.asList(map.get(cur)));
        }
        return connected.size();
    }
}
