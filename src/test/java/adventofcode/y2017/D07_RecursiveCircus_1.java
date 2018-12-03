package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D07_RecursiveCircus_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D07_RecursiveCircus_1.class);
        assertThat(run(fileInputs.get(0)), is("tknk"));
        assertThat(run(fileInputs.get(1)), is("rqwgj"));
    }

    private String run(String input) {
        Set<String> existingIds = new HashSet<>();
        Set<String> referencedIds = new HashSet<>();
        for (String line : input.split("\\n")) {
            String[] tokens = line.split("\\s");
            String nodeId = tokens[0];
            existingIds.add(nodeId);
            if (tokens.length <= 2) continue;
            for (int i = 3; i < tokens.length; i++) {
                int tokenLen = tokens[i].length() - (i < tokens.length - 1 ? 1 : 0);
                referencedIds.add(tokens[i].substring(0, tokenLen));
            }
        }
        for (String id : existingIds) {
            if (!referencedIds.contains(id)) {
                return id;
            }
        }
        return "oops";
    }
}
