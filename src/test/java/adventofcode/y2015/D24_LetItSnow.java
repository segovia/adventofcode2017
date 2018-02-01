package adventofcode.y2015;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D24_LetItSnow {

    @Test
    public void test() throws Exception {
        assertThat(run(6, 6), is(27995004L));
        assertThat(run(5, 6), is(31663883L));
        assertThat(run(2981, 3075), is(9132360L));
    }

    private long run(int row, int col) throws Exception {
        int size = col + row - 1;
        long code = 20151125;
        for (int diagonal = 0; diagonal < size; diagonal++) {
            for (int j = 0; j <= diagonal; j++) {
                if (j == col - 1 && diagonal - j == row - 1) return code;
                code = (code * 252533) % 33554393;
            }
        }
        return -1;
    }
}