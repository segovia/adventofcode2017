package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D12_JSAbacusFrameworkio_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("[1,2,3]"), is(6));
        assertThat(run("{\"a\":2,\"b\":4}"), is(6));
        assertThat(run("[[[3]]]"), is(3));
        assertThat(run("{\"a\":{\"b\":4},\"c\":-1}"), is(3));
        assertThat(run("{\"a\":[-1,1]}"), is(0));
        assertThat(run("[-1,{\"a\":1}]"), is(0));
        assertThat(run("[1,{\"c\":\"red\",\"b\":2},3]"), is(4));
        assertThat(run("[1,{\"b\":2,\"c\":\"red\"},3]"), is(4));
        assertThat(run("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"), is(0));
        assertThat(run("{\"e\":[1,2,3,4],\"f\":5,\"d\":\"red\"}"), is(0));
        assertThat(run("[1,\"red\",5]"), is(6));
        assertThat(run("{\"e\":{\"e\":,\"f\":5,\"d\":\"red\"},\"f\":5,\"d\":\"red\"}"), is(0));

        List<String> fileInputs = Utils.getInputsFromFiles(D12_JSAbacusFrameworkio_2.class);
        assertThat(run(fileInputs.get(0)), is(65402));
    }

    private int run(String input) {
        int sum = 0;
        int sumWithRed = 0;
        int num = 0;
        Deque<Integer> sumInObject = new ArrayDeque<>();
        sumInObject.push(0);
        Integer objectWithRed = null;
        boolean negative = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '{') {
                sumInObject.push(0);
            } else if (c == '"' && i >= 5 && objectWithRed == null && input.substring(i - 5, i).equals(":\"red")) {
                objectWithRed = sumInObject.size();
            }
            if (Character.isDigit(c)) {
                num *= 10;
                num += c - '0';
                if (i > 0 && input.charAt(i - 1) == '-') negative = true;
            } else if (i > 0 && Character.isDigit(input.charAt(i - 1))) {
                int toAdd = num * (negative ? -1 : 1);
                sum += toAdd;
                sumInObject.push(sumInObject.pop() + toAdd);
                num = 0;
                negative = false;
            }
            if (c == '}') {
                if (objectWithRed != null && objectWithRed <= sumInObject.size()) {
                    if (objectWithRed == sumInObject.size()) objectWithRed = null;
                    // count the non red, and pop it off so it is not accumulated and counted twice
                    sumWithRed += sumInObject.pop();
                } else {
                    // accumulate all non red, it may be negated later
                    sumInObject.push(sumInObject.pop() + sumInObject.pop());
                }
            }
        }
        return sum - sumWithRed;
    }
}