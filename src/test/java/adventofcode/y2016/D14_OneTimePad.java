package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D14_OneTimePad {

    @Test
    public void test() throws Exception {
        assertThat(run("abc", false, 64), is(22728));
        assertThat(run("ahsbgdzn", false, 64), is(23890));
        assertThat(run("abc", true, 1), is(10));
    }

    @Test
    @Ignore("Ignored since this test takes 25s to run")
    public void testLong1() throws Exception {
        assertThat(run("abc", true, 64), is(22551));
    }

    @Test
    @Ignore("Ignored since this test takes 25s to run")
    public void testLong2() throws Exception {
        assertThat(run("ahsbgdzn", true, 64), is(22696));
    }

    private Integer run(String input, boolean stretched, int keyCount) throws Exception {
        Map<Character, Deque<Integer>> tripletMap = new HashMap<>();

        PriorityQueue<Integer> keys = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; keys.size() < keyCount || keys.peek() + 1000 >= i; i++) {
            String md5 = doMD5(input + i, stretched);
            int inARow = 0;
            char prev = ' ';
            int len = md5.length();
            boolean foundATriplet = false;
            for (int j = 0; j < len; j++) {
                char c = md5.charAt(j);
                if (c != prev) {
                    inARow = 1;
                    prev = c;
                    continue;
                }
                ++inARow;
                if (!foundATriplet && inARow == 3) {
                    tripletMap.computeIfAbsent(c, k -> new ArrayDeque<>()).add(i);
                    foundATriplet = true;
                }
                if (inARow == 5) {
                    Deque<Integer> indexes = tripletMap.get(c);
                    if (indexes != null) {
                        while (!indexes.isEmpty() && indexes.peek() + 1000 < i) indexes.poll();
                        while (!indexes.isEmpty() && indexes.peek() < i) keys.add(indexes.poll());
                        while (keys.size() > keyCount) keys.poll();
                    }
                }
            }
        }
        return keys.peek();
    }

    private String doMD5(String input, boolean stretch) throws Exception {
        String cur = input;
        for (int i = 0; i < (stretch ? 2017 : 1); i++) {
            cur = Utils.doMD5(cur);
        }
        return cur;
    }
}