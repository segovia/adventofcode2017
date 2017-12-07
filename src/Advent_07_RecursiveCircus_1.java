import java.io.IOException;
import java.util.*;

public class Advent_07_RecursiveCircus_1 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_07_RecursiveCircus_1.class);
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static String run(String input) {
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
