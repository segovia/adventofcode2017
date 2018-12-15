package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D16_AuntSue {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D16_AuntSue.class);
        assertThat(run(fileInputs.get(0), fileInputs.get(1), false), is(213));
        assertThat(run(fileInputs.get(0), fileInputs.get(1), true), is(323));
    }

    private int run(String lookingForStr, String input, boolean updatedRetro) {
        String[] lines = input.split("\\n");
        Map<String, Integer> lookingFor = makeLookingFor(lookingForStr);
        for (String line : lines) {
            int firstColon = line.indexOf(':');
            String[] values = line.substring(firstColon + 2).split(", ");

            boolean possibleMatch = true;
            for (String value : values) {
                String[] tokens = value.split(": ");
                Integer lookingForVal = lookingFor.get(tokens[0]);
                Integer actual = Integer.parseInt(tokens[1]);
                if (updatedRetro && ("cats".equals(tokens[0]) || "trees".equals(tokens[0]))) {
                    if (lookingForVal >= actual) {
                        possibleMatch = false;
                        break;
                    }
                } else if (updatedRetro && ("pomeranians".equals(tokens[0]) || "goldfish".equals(tokens[0]))) {
                    if (lookingForVal <= actual) {
                        possibleMatch = false;
                        break;
                    }
                } else if (lookingForVal != actual) {
                    possibleMatch = false;
                    break;
                }
            }
            if (possibleMatch) return Integer.parseInt(line.substring(0, firstColon).split("\\s")[1]);
        }
        return -1;
    }

    private Map<String, Integer> makeLookingFor(String lookingForStr) {
        Map<String, Integer> lookingFor = new HashMap<>();
        for (String line : lookingForStr.split("\\n")) {
            String[] tokens = line.split(": ");
            lookingFor.put(tokens[0], Integer.parseInt(tokens[1]));
        }
        return lookingFor;
    }
}