package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D07_SomeAssemblyRequired {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D07_SomeAssemblyRequired.class);
        assertThat(run(fileInputs.get(0), "e"), is(507));
        assertThat(run(fileInputs.get(1), "a"), is(956));
        assertThat(runWithBOverride(fileInputs.get(1), "a"), is(40149));
    }

    private int run(String input, String name) {
        String[] lines = input.split("\\n");
        Map<String, Wire> wireMap = new HashMap<>();
        for (String line : lines) {
            Wire wire = new Wire(line);
            wireMap.put(wire.name, wire);
        }
        return evalWire(wireMap, name);
    }

    private int runWithBOverride(String input, String name) {
        String[] lines = input.split("\\n");
        Map<String, Wire> wireMap = new HashMap<>();
        for (String line : lines) {
            Wire wire = new Wire(line);
            wireMap.put(wire.name, wire);
            if ("b".equals(wire.name)) wire.val = 956;
        }
        return evalWire(wireMap, name);
    }

    private Integer evalWire(Map<String, Wire> wireMap, String name) {
        if (Character.isDigit(name.charAt(0))) return Integer.parseInt(name);
        Wire wire = wireMap.get(name);
        if (wire.val != null) return wire.val;
        String[] source = wire.source.split(" ");
        if (source.length == 1) {
            wire.val = evalWire(wireMap, source[0]); // set
        } else if (source.length == 2) {
            wire.val = ~evalWire(wireMap, source[1]); // not
        } else { // length 3
            switch (source[1]) {
                case "AND":
                    wire.val = evalWire(wireMap, source[0]) & evalWire(wireMap, source[2]);
                    break;
                case "OR":
                    wire.val = evalWire(wireMap, source[0]) | evalWire(wireMap, source[2]);
                    break;
                case "LSHIFT":
                    wire.val = evalWire(wireMap, source[0]) << evalWire(wireMap, source[2]);
                    break;
                case "RSHIFT":
                    wire.val = evalWire(wireMap, source[0]) >>> evalWire(wireMap, source[2]);
                    break;
            }

        }
        int bit16Mask = (1 << 16) - 1;
        wire.val &= bit16Mask;
        return wire.val;
    }

    private static class Wire {
        String name;
        String source;
        Integer val = null;

        Wire(String line) {
            String[] split = line.split(" -> ");
            source = split[0];
            name = split[1];
        }
    }

}
