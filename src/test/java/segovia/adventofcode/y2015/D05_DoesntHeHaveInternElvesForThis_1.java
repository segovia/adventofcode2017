package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D05_DoesntHeHaveInternElvesForThis_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("ugknbfddgicrmopn"), is(1L));
        assertThat(run("aaa"), is(1L));
        assertThat(run("jchzalrnumimnmhp"), is(0L));
        assertThat(run("haegwjzuvuyypxyu"), is(0L));
        assertThat(run("dvszwmarrgswjxmb"), is(0L));
        List<String> fileInputs = Utils.getInputsFromFiles(D05_DoesntHeHaveInternElvesForThis_1.class);
        assertThat(run(fileInputs.get(0)), is(236L));
    }

    private long run(String input) {
        String[] lines = input.split("\\n");
        return Arrays.stream(lines).filter(this::isNice).count();
    }

    private boolean isNice(String s) {
        int vowelCount = 0;
        boolean hasTwiceInRow = false;
        for (int i = 0; i < s.length(); i++) {
            char prev = i == 0 ? '!' : s.charAt(i - 1);
            char cur = s.charAt(i);
            if (prev == 'a' && cur == 'b' ||
                    prev == 'c' && cur == 'd' ||
                    prev == 'p' && cur == 'q' ||
                    prev == 'x' && cur == 'y') return false;

            if (cur == 'a' || cur == 'e' || cur == 'i' || cur == 'o' || cur == 'u') ++vowelCount;
            if (prev == cur) hasTwiceInRow = true;
        }
        return vowelCount >= 3 && hasTwiceInRow;
    }
}
