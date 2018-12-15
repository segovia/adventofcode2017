package segovia.adventofcode.y2018;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D09_MarbleMania {

    @Test
    public void test() throws IOException {
        assertThat(run("9 players; last marble is worth 25 points"), is(32L));
        assertThat(run("10 players; last marble is worth 1618 points"), is(8317L));
        assertThat(run("13 players; last marble is worth 7999 points"), is(146373L));
        assertThat(run("17 players; last marble is worth 1104 points"), is(2764L));
        assertThat(run("21 players; last marble is worth 6111 points"), is(54718L));
        assertThat(run("30 players; last marble is worth 5807 points"), is(37305L));
        assertThat(run("448 players; last marble is worth 71628 points"), is(394486L));
    }

    @Test
    public void testLong() throws Exception {
        assertThat(run("448 players; last marble is worth 7162800 points"), is(3276488008L));
    }

    private long run(String input) {
        String[] split = input.split(" ");
        int players = Integer.parseInt(split[0]);
        int points = Integer.parseInt(split[6]);
        long[] scores = new long[players];
        CircularListNode node = new CircularListNode(0);
        for (int i = 1; i <= points; i++) {
            if (i % 23 == 0) {
                for (int j = 0; j < 7; j++) {
                    node = node.prev;
                }
                scores[i % players] += i + node.val;
                node = node.remove();
            } else {
                node = node.next.insert(i);
            }
        }

        return Arrays.stream(scores).max().getAsLong();
    }
    public static class CircularListNode {

        CircularListNode prev;
        CircularListNode next;
        long val;

        public CircularListNode(long val) {
            this.val = val;
            prev = this;
            next = this;
        }

        public CircularListNode insert(long val) {
            CircularListNode newNode = new CircularListNode(val);
            newNode.next = next;
            next.prev = newNode;
            next = newNode;
            newNode.prev = this;
            return newNode;
        }

        public CircularListNode remove() {
            prev.next = next;
            next.prev = prev;
            return next;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }
}