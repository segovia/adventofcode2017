package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D10_BalanceBots {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D10_BalanceBots.class);
        assertThat(run(fileInputs.get(0), 2, 5), is("2 - 30"));
        assertThat(run(fileInputs.get(1), 17, 61), is("181 - 12567"));
    }

    private String run(String input, int... bots) throws Exception {
        Map<Integer, Set<Integer>> botValMap = new HashMap<>();
        Map<Integer, List<Integer>> botGiveMap = new HashMap<>();
        String[] lines = input.split("\\n");
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            if ("value".equals(tokens[0])) {
                int val = Integer.parseInt(tokens[1]);
                int bot = Integer.parseInt(tokens[5]);
                botValMap.computeIfAbsent(bot, k -> new TreeSet<>()).add(val);
            } else {
                int bot = Integer.parseInt(tokens[1]);
                List<Integer> givesTo = botGiveMap.computeIfAbsent(bot, k -> new ArrayList<>());

                int loTarget = Integer.parseInt(tokens[6]);
                if ("bot".equals(tokens[5])) givesTo.add(loTarget);
                else givesTo.add(-loTarget - 1);

                int hiTarget = Integer.parseInt(tokens[11]);
                if ("bot".equals(tokens[10])) givesTo.add(hiTarget);
                else givesTo.add(-hiTarget - 1);
            }
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>(botValMap.entrySet().stream()
                .filter(e -> e.getValue().size() == 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));
        int answer = -1;
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            if (visited.contains(cur)) continue;
            visited.add(cur);
            Iterator<Integer> valsIterator = botValMap.get(cur).iterator();
            int lo = valsIterator.next();
            int hi = valsIterator.next();
            if (lo == bots[0] && hi == bots[1]) answer = cur;

            List<Integer> receives = botGiveMap.get(cur);
            if (receives == null) continue;
            if (receives.get(0) != null) {
                Set<Integer> valSet = botValMap.computeIfAbsent(receives.get(0), k -> new TreeSet<>());
                valSet.add(lo);
                if (valSet.size() == 2) queue.add(receives.get(0));
            }
            if (receives.get(1) != null) {
                Set<Integer> valSet = botValMap.computeIfAbsent(receives.get(1), k -> new TreeSet<>());
                valSet.add(hi);
                if (valSet.size() == 2) queue.add(receives.get(1));
            }
        }
        List<Integer> outputVals = new ArrayList<>();
        outputVals.addAll(botValMap.get(-1));
        outputVals.addAll(botValMap.get(-2));
        outputVals.addAll(botValMap.get(-3));
        int mult = 1;
        for (Integer val : outputVals) {
            mult *= val;
        }

        return answer + " - " + mult;
    }

}