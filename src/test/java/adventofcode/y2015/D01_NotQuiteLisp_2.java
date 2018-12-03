package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D01_NotQuiteLisp_2 {

    @Test
    public void test() throws IOException {
        assertThat(run(")"), is(1));
        assertThat(run("()())"), is(5));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_NotQuiteLisp_2.class);
        assertThat(run(fileInputs.get(0)), is(1795));
    }

    private int run(String input) {
        int floor = 0;
        for (int i = 0; i < input.length(); i++) {
            floor += input.charAt(i) == '(' ? 1 : -1;
            if (floor < 0) return i + 1;
        }
        return floor;
    }
}
