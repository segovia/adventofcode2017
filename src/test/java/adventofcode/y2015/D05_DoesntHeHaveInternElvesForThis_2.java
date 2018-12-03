package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D05_DoesntHeHaveInternElvesForThis_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("qjhvhtzxzqqjkmpb"), is(1L));
        assertThat(run("xxyxx"), is(1L));
        assertThat(run("uurcxstgmygtbstg"), is(0L));
        assertThat(run("ieodomkazucvgmuy"), is(0L));
        List<String> fileInputs = Utils.getInputsFromFiles(D05_DoesntHeHaveInternElvesForThis_2.class);
        assertThat(run(fileInputs.get(0)), is(51L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        return Arrays.stream(lines).filter(this::isNice).count();
    }

    private boolean isNice(String s) {
        Map<String, Integer> knownPairsToFirstPos = new HashMap<>();
        boolean hasDoublePair = false;
        boolean hasRepeatLetterWithInBetween = false;
        for (int i = 0; i < s.length() - 1 && !(hasDoublePair && hasRepeatLetterWithInBetween); i++) {
            String pair = s.substring(i, i + 2);
            Integer prevPos = knownPairsToFirstPos.get(pair);
            if (prevPos != null && i - prevPos >= 2) hasDoublePair = true;
            knownPairsToFirstPos.putIfAbsent(pair, i);
            if (i < s.length() - 2 && s.charAt(i) == s.charAt(i + 2)) hasRepeatLetterWithInBetween = true;
        }
        return hasDoublePair && hasRepeatLetterWithInBetween;
    }
}
