package segovia.adventofcode.y2015;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D01_NotQuiteLisp_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("(())"), is(0));
        assertThat(run(")())())"), is(-3));
        assertThat(run("))((((("), is(3));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_NotQuiteLisp_1.class);
        assertThat(run(fileInputs.get(0)), is(74));
    }

    private int run(String input) {
        int floor = 0;
        for (int i = 0; i < input.length(); i++) floor += input.charAt(i) == '(' ? 1 : -1;
        return floor;
    }
}
