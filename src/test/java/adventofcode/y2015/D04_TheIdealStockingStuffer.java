package adventofcode.y2015;

import org.junit.Ignore;
import org.junit.Test;

import static adventofcode.Utils.doMD5;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D04_TheIdealStockingStuffer {

    @Test
    public void test() throws Exception {
        assertThat(run("abcdef", "00000"), is(609043));
        assertThat(run("yzbqklnj", "00000"), is(282749));
    }

    @Test
    @Ignore("Ignored since this test takes 7s to run")
    public void testLong() throws Exception {
        assertThat(run("yzbqklnj", "000000"), is(9962624));
    }

    private int run(String input, String startsWith) throws Exception {
        for (int i = 1; true; i++)
            if (doMD5(input + i).startsWith(startsWith))
                return i;
    }
}
