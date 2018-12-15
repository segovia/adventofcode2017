package segovia.adventofcode.y2016;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D19_AnElephantNamedJoseph_1 {

    @Test
    public void test() throws Exception {
        assertThat(run(5), is(3));
        assertThat(run(3004953), is(1815603));
    }

    private int run(int input) throws Exception {
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= input; i++) q.add(i);
        while (q.size() > 1) {
            q.add(q.poll());
            q.poll();
        }
        return q.poll();
    }
}