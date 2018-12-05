package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.Comparator.comparing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D04_ReposeRecord {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("240 4455"));
        assertThat(run(fileInputs.get(1)), is("67558 78990"));
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
    private String run(String input) {
        String[] entries = input.split("\\r?\\n");
        Arrays.sort(entries);
        Map<Integer, GuardHistory> historyMap = new HashMap<>();
        GuardHistory cur = null;
        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            if (entry.contains("begins")) {
                int id = getId(entry);
                cur = historyMap.computeIfAbsent(id, GuardHistory::new);
            } else {
                cur.markSleep(getMinute(entry), getMinute(entries[++i]));
            }
        }
        Collection<GuardHistory> histories = historyMap.values();
        GuardHistory bestTotalSleep = histories.stream().max(comparing(GuardHistory::getTotalSleep)).get();
        GuardHistory bestTopTimeVal = histories.stream().max(comparing(GuardHistory::getTopTimeVal)).get();
        return bestTotalSleep.id * bestTotalSleep.topTime + " " + bestTopTimeVal.id * bestTopTimeVal.topTime;
    }

    private int getId(String entry) {
        return Integer.parseInt(entry.replaceAll(".*#(\\d+).*", "$1"));
    }

    private int getMinute(String entry) {
        String[] time = entry.replaceAll(".*(\\d\\d:\\d\\d).*", "$1").split(":");
        return "23".equals(time[0]) ? 0 : Integer.parseInt(time[1]);
    }

    private static class GuardHistory {
        int id;
        int[] timeline = new int[60];
        int totalSleep = 0;
        int topTime = 0;
        int topTimeVal = 0;

        GuardHistory(int id) {
            this.id = id;
        }

        int getTotalSleep() {
            return totalSleep;
        }

        int getTopTimeVal() {
            return topTimeVal;
        }

        void markSleep(int start, int end) {
            for (int i = start; i < end; i++) {
                ++timeline[i];
                ++totalSleep;
                if (timeline[i] > topTimeVal) {
                    topTimeVal = timeline[i];
                    topTime = i;
                }
            }
        }
    }
}
