package segovia.adventofcode.y2016;

import org.junit.Ignore;
import org.junit.Test;

import static segovia.adventofcode.Utils.doMD5;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D05_HowAboutANiceGameOfChess_2 {

    @Test
    @Ignore("Ignored since this test takes 16s to run")
    public void test() throws Exception {
        assertThat(run("abc"), is("05ace8e3"));
        assertThat(run("ugkcyxxp"), is("f2c730e5"));
    }

    private String run(String input) throws Exception {
        Character[] out = new Character[8];
        for (int i = 0; true; i++) {
            String md5 = doMD5(input + i);
            if (md5.startsWith("00000")) {
                char posChar = md5.charAt(5);
                if (posChar < '0' || posChar > '7') continue;
                int pos = posChar - '0';
                if (out[pos] != null) continue;
                out[pos] = md5.charAt(6);
                System.out.println("Current code: " + toString(out));
                if (isComplete(out)) break;
            }
        }
        return toString(out);
    }

    private boolean isComplete(Character[] out) {
        for (Character c : out) if (c == null) return false;
        return true;
    }

    private String toString(Character[] out) {
        StringBuilder sb = new StringBuilder();
        for (Character c : out) sb.append(c == null ? '_' : c);
        return sb.toString();
    }
}
