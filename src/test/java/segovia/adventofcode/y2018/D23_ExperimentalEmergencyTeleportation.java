package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D23_ExperimentalEmergencyTeleportation {

    @Test
    public void test() throws IOException {
        assertThat(run("pos=<0,0,0>, r=4\n" +
                "pos=<1,0,0>, r=1\n" +
                "pos=<4,0,0>, r=3\n" +
                "pos=<0,2,0>, r=1\n" +
                "pos=<0,5,0>, r=3\n" +
                "pos=<0,0,3>, r=1\n" +
                "pos=<1,1,1>, r=1\n" +
                "pos=<1,1,2>, r=1\n" +
                "pos=<1,3,1>, r=1"), is("7 4"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("935 138697281"));
    }

    private String run(String input) {
        List<Nanobot> bots = Arrays.stream(input.split("\\r?\\n")).map(Nanobot::new).collect(Collectors.toList());
        Nanobot max = bots.stream().max(Comparator.comparingInt(a -> a.r)).get();
        long count = bots.stream().filter(b -> max.dist(b) <= max.r).count();

        TreeMap<Integer, Integer> ranges = new TreeMap<>();
        for (Nanobot n : bots) {
            int distFromZero = n.distToOrigin();
            ranges.put(Math.max(0, distFromZero - n.r), 1);
            ranges.put(distFromZero + n.r, -1);
        }
        int result = 0;
        int curInRange = 0;
        int inRangeCount = 0;
        for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
            curInRange += range.getValue();
            if (curInRange <= inRangeCount) continue;
            result = range.getKey();
            inRangeCount = curInRange;
        }
        return count + " " + result;
    }

    private static final Pattern PATTERN = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");

    private static class Nanobot {
        int[] pos = new int[3];
        int r;

        Nanobot(String s) {
            Matcher m = PATTERN.matcher(s);
            m.find();
            for (int i = 0; i < pos.length; i++) {
                pos[i] = Integer.parseInt(m.group(i + 1));
            }
            r = Integer.parseInt(m.group(4));
        }

        int dist(Nanobot bot) {
            int dist = 0;
            for (int i = 0; i < pos.length; i++) {
                dist += Math.abs(pos[i] - bot.pos[i]);
            }
            return dist;
        }

        int distToOrigin() {
            int dist = 0;
            for (int p : pos) {
                dist += Math.abs(p);
            }
            return dist;
        }
    }

}
