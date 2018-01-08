package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D12_JSAbacusFrameworkio_1 {

    @Test
    public void test() throws IOException {
        assertThat(run("[1,2,3]"), is(6));
        assertThat(run("{\"a\":2,\"b\":4}"), is(6));
        assertThat(run("[[[3]]]"), is(3));
        assertThat(run("{\"a\":{\"b\":4},\"c\":-1}"), is(3));
        assertThat(run("{\"a\":[-1,1]}"), is(0));
        assertThat(run("[-1,{\"a\":1}]"), is(0));
        assertThat(run("[]"), is(0));
        assertThat(run("{}"), is(0));
        List<String> fileInputs = Utils.getInputsFromFiles(D12_JSAbacusFrameworkio_1.class);
        assertThat(run(fileInputs.get(0)), is(111754));
    }

    private int run(String input) {
        return Arrays.stream(input.split("([\\[,\\]{}:]|\"\\w+\")+"))
                .filter(s -> !Utils.isBlank(s))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}