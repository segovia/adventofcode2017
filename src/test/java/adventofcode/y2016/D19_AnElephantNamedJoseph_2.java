package adventofcode.y2016;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D19_AnElephantNamedJoseph_2 {

    @Test
    public void test() throws Exception {
        assertThat(run(5), is(2));
        assertThat(run(6), is(3));
        assertThat(run(3004953), is(1410630));
    }

    private int run(int input) throws Exception {
        Deque<Integer> q = new ArrayDeque<>();
        int half = (input - 1) / 2;
        for (int i = 0; i < input; i++) q.add(i > half ? i - half : half + i);
        if (q.size() % 2 == 0) q.poll();
        while (q.size() > 1) {
            q.poll();
            q.add(q.poll());
            q.poll();
        }
        return q.poll();
    }
}