package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D17_Spinlock {

    @Test
    public void test() throws IOException {
        assertThat(run(3), is(638));
        assertThat(run(329), is(725));
    }

    private int run(int steps) {
        CircularListNode cur = new CircularListNode(0);;
        for (int i = 1; i <= 2017; i++) {
            for (int j = 0; j < steps; j++) {
                cur = cur.next;
            }
            CircularListNode.newAfter(cur, i);
            cur = cur.next;
        }
        return cur.next.val;
    }

    static class CircularListNode {
        int val;
        CircularListNode next;

        CircularListNode(int val) {
            this.val = val;
            next = this;
        }

        static CircularListNode newAfter(CircularListNode n, int val) {
            CircularListNode after = new CircularListNode(val);
            CircularListNode aux = n.next;
            n.next = after;
            after.next = aux;
            return after;
        }
    }

}
