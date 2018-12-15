package segovia.adventofcode.y2016;

import org.junit.Ignore;
import org.junit.Test;

import static segovia.adventofcode.Utils.doMD5;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D05_HowAboutANiceGameOfChess_1 {

    @Test
    @Ignore("Ignored since this test takes 8s to run")
    public void test() throws Exception {
        assertThat(run("abc"), is("18f47a30"));
        assertThat(run("ugkcyxxp"), is("d4cd2ee1"));
    }

    private String run(String input) throws Exception {
        StringBuilder out = new StringBuilder();
        for (int i = 0; out.length() < 8; i++) {
            String md5 = doMD5(input + i);
            if (md5.startsWith("00000")) {
                out.append(md5.charAt(5));
                System.out.println("Current code: " + out.toString());
            }
        }
        return out.toString();
    }
}
