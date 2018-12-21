package segovia.adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D21_ChronalConversion {

    @Test
    public void test() throws IOException {
        assertThat(run(true), is(5745418));
        assertThat(run(false), is(5090905));
    }

    private int run(boolean stopFirstLoop) {
        Set<Integer> uniqueRg4Vals = new LinkedHashSet<>();
        int rg4 = 0;
        do {
            int start = rg4 | 65536;
            rg4 = 14464005;
            for (int aux = start; aux > 0; aux /= 256) {
                rg4 += aux & 255 & 16777215;
                rg4 *= 65899;
                rg4 &= 16777215;
            }
        } while (uniqueRg4Vals.add(rg4) && !stopFirstLoop); // rg0 != rg4 ?
        return new ArrayList<>(uniqueRg4Vals).get(uniqueRg4Vals.size() - 1);
    }
}
