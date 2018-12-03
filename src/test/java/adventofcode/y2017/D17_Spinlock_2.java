package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D17_Spinlock_2 {

    @Test
    public void test() throws IOException {
        assertThat(run(3), is(1222153));
        assertThat(run(329), is(27361412));
    }

    private int run(int steps) {
        int cur = 0;
        int valAfterZero = -1;
        for (int len = 1; len <= 50_000_000; len++) {
            int next = (cur + steps) % len;
            if (next == 0) valAfterZero = len;
            cur = (next + 1) % (len + 1);
        }
        return valAfterZero;
    }


}
