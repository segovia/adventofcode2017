package segovia.adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D10_ElvesLookElvesSay {

    @Test
    public void test() throws IOException {
        assertThat(lookAndSay("1"), is("11"));
        assertThat(lookAndSay("11"), is("21"));
        assertThat(lookAndSay("21"), is("1211"));
        assertThat(lookAndSay("1211"), is("111221"));
        assertThat(lookAndSay("111221"), is("312211"));
        assertThat(run("1", 5), is(6));
        assertThat(run("1113122113", 40), is(360154));
        assertThat(run("1113122113", 50), is(5103798));
    }

    private int run(String input, int times) {
        String s = input;
        for (int i = 0; i < times; i++) {
            s = lookAndSay(s);
        }
        return s.length();
    }

    private String lookAndSay(String input) {
        StringBuilder sb = new StringBuilder();
        int matchCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i == 0 || input.charAt(i - 1) == input.charAt(i))
                matchCount++;
            else {
                sb.append(matchCount).append(input.charAt(i - 1));
                matchCount = 1;
            }
        }
        sb.append(matchCount).append(input.charAt(input.length() - 1));
        return sb.toString();
    }
}