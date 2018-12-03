package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D10_BalanceBots {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D10_BalanceBots.class);
        assertThat(run(fileInputs.get(0), 2, 5), is("2 - 30"));
        assertThat(run(fileInputs.get(1), 17, 61), is("181 - 12567"));
    }

    private String run(String input, int... bots) throws Exception {
        Map<Integer, Bot> botMap = new HashMap<>();
        String[] lines = input.split("\\n");
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            if ("value".equals(tokens[0])) {
                int val = Integer.parseInt(tokens[1]);
                int botId = Integer.parseInt(tokens[5]);
                botMap.computeIfAbsent(botId, Bot::new).addVal(val);
            } else {
                int botId = Integer.parseInt(tokens[1]);
                Bot bot = botMap.computeIfAbsent(botId, Bot::new);
                bot.setGive(true, "bot".equals(tokens[5]), Integer.parseInt(tokens[6]));
                bot.setGive(false, "bot".equals(tokens[10]), Integer.parseInt(tokens[11]));
            }
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Bot> queue = new ArrayDeque<>(botMap.values().stream().filter(Bot::hasBothVals).collect(Collectors.toList()));
        int answer = -1;
        while (!queue.isEmpty()) {
            Bot cur = queue.poll();
            if (visited.contains(cur.id)) continue;
            visited.add(cur.id);
            if (cur.loVal == bots[0] && cur.hiVal == bots[1]) answer = cur.id;
            addVal(botMap, cur.loBot, cur.loVal, queue);
            addVal(botMap, cur.hiBot, cur.hiVal, queue);

        }
        List<Integer> outputVals = new ArrayList<>();
        outputVals.add(botMap.get(-1).loVal);
        outputVals.add(botMap.get(-2).loVal);
        outputVals.add(botMap.get(-3).loVal);
        int product = 1;
        for (Integer val : outputVals) product *= val;
        return answer + " - " + product;
    }

    private void addVal(Map<Integer, Bot> botMap, Integer botId, int val, Deque<Bot> queue) {
        if (botId == null) return;
        Bot bot = botMap.computeIfAbsent(botId, Bot::new);
        bot.addVal(val);
        if (bot.hasBothVals()) queue.add(bot);
    }

    private static class Bot {
        int id;
        Integer loBot;
        Integer hiBot;
        Integer loVal;
        Integer hiVal;

        Bot(int id) {
            this.id = id;
        }

        void addVal(int val) {
            if (loVal == null || loVal > val) {
                hiVal = loVal;
                loVal = val;
            } else {
                hiVal = val;
            }
        }

        void setGive(boolean isLo, boolean isBot, int botId) {
            int calcBotId = isBot ? botId : -botId - 1;
            if (isLo) loBot = calcBotId;
            else hiBot = calcBotId;
        }

        boolean hasBothVals() {
            return loVal != null && hiVal != null;
        }
    }

}