package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D20_FirewallRules {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D20_FirewallRules.class);
        assertThat(run(fileInputs.get(0), 9L), is("3 - 2"));
        assertThat(run(fileInputs.get(1), 4294967295L), is("22887907 - 109"));
    }

    private String run(String input, long max) throws Exception {
        Range[] ranges = Arrays.stream(input.split("\\n")).map(Range::new).toArray(Range[]::new);
        Arrays.sort(ranges);

        Long firstAllowed = null;
        long allowedCount = 0;
        long maxCovered = -1;
        for (Range r : ranges) {
            if (r.start > maxCovered + 1) {
                if (firstAllowed == null) firstAllowed = maxCovered + 1;
                allowedCount += r.start - maxCovered - 1;
            }
            maxCovered = Math.max(r.end, maxCovered);
        }
        allowedCount += max - maxCovered;
        return firstAllowed + " - " + allowedCount;
    }

    private static class Range implements Comparable<Range> {
        long start;
        long end;

        Range(String line) {
            long[] longs = Utils.toLongArray(line.split("-"));
            start = longs[0];
            end = longs[1];
        }

        @Override
        public int compareTo(Range o) {
            int startCompare = Long.compare(start, o.start);
            if (startCompare != 0) return startCompare;
            return Long.compare(end, o.end);
        }

        @Override
        public String toString() {
            return start + "-" + end;
        }
    }
}