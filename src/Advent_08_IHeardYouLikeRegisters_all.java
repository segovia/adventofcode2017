import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent_08_IHeardYouLikeRegisters_all {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Utils.getInputsFromFiles(Advent_08_IHeardYouLikeRegisters_all.class.getSimpleName());
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static String run(String input) {
        Map<String, Integer> memory = new HashMap<>();
        int maxValue = Integer.MIN_VALUE;
        for (String line : input.split("\\n")) {
            String[] tokens = line.split("\\s");
            String register = tokens[0];
            String op = tokens[1];
            int val = Integer.parseInt(tokens[2]);
            String left = tokens[4];
            String cond = tokens[5];
            int right = Integer.parseInt(tokens[6]);
            if (!checkCondition(memory, left, cond, right)) {
                continue;
            }
            int newVal = memory.getOrDefault(register, 0) + ("inc".equals(op) ? val : -val);
            memory.put(register, newVal);
            maxValue = Math.max(newVal, maxValue);
        }

        return memory.values().stream().mapToInt(Integer::intValue).max().getAsInt() + " - " + maxValue;
    }

    private static boolean checkCondition(Map<String, Integer> memory, String left, String cond, int right) {
        int leftVal = memory.getOrDefault(left, 0);
        switch (cond) {
            case ">":
                return leftVal > right;
            case ">=":
                return leftVal >= right;
            case "<":
                return leftVal < right;
            case "<=":
                return leftVal <= right;
            case "==":
                return leftVal == right;
            case "!=":
                return leftVal != right;
        }
        throw new RuntimeException("oops");

    }
}
