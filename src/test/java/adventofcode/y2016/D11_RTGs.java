package adventofcode.y2016;

import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D11_RTGs {

    @Test
    public void test() throws Exception {
        assertThat(run(Arrays.asList(1, 0, 2, 0, 0)), is(11));
        assertThat(run(Arrays.asList(0, 0, 1, 2, 1, 2, 1, 2, 1, 2, 0)), is(33));
    }

    @Test
    @Ignore("Ignored since this test takes 7s to run")
    public void testLong() throws Exception {
        assertThat(run(Arrays.asList(0, 0, 1, 2, 1, 2, 1, 2, 1, 2, 0, 0, 0, 0, 0)), is(57));
    }

    private int run(List<Integer> input) throws Exception {
        int len = input.size();
        BitSet visited = new BitSet();
        Deque<Integer> nextQueue = new ArrayDeque<>();
        addToNext(toInt(input), nextQueue, visited);
        int steps = 0;
        while (!nextQueue.isEmpty()) {
            Deque<Integer> queue = nextQueue;
            nextQueue = new ArrayDeque<>();
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                if (visited.get(cur)) continue;
                visited.set(cur);
                if (!isValid(cur, len)) continue;
                if (isFinished(cur, len)) return steps;

                int elevatorFloor = getVal(cur, len - 1);
                for (int i = 0; i < len - 1; i++) {
                    // move 1 item
                    int itemFloor = getVal(cur, i);
                    if (itemFloor != elevatorFloor) continue;
                    if (elevatorFloor < 3) {
                        int newVal = addVal(cur, i, 1);
                        newVal = addVal(newVal, len - 1, 1);
                        addToNext(newVal, nextQueue, visited);
                    }
                    if (elevatorFloor > 0) {
                        int newVal = addVal(cur, i, -1);
                        newVal = addVal(newVal, len - 1, -1);
                        addToNext(newVal, nextQueue, visited);
                    }
                    // check for second item
                    for (int j = i + 1; j < len - 1; j++) {
                        int otherItemFloor = getVal(cur, j);
                        if (otherItemFloor != elevatorFloor) continue;
                        if (elevatorFloor < 3) {
                            int newVal = addVal(cur, i, 1);
                            newVal = addVal(newVal, j, 1);
                            newVal = addVal(newVal, len - 1, 1);
                            addToNext(newVal, nextQueue, visited);
                        }
                        if (elevatorFloor > 0) {
                            int newVal = addVal(cur, i, -1);
                            newVal = addVal(newVal, j, -1);
                            newVal = addVal(newVal, len - 1, -1);
                            addToNext(newVal, nextQueue, visited);
                        }
                    }
                }
            }
            ++steps;
//            System.out.println("step: " + steps);
        }
        return 0;
    }

    private void addToNext(int val, Deque<Integer> nextQueue, BitSet visited) {
        if (visited.get(val)) return;
        nextQueue.add(val);
    }

    private int toInt(List<Integer> aux) {
        int val = 0;
        for (int i = aux.size() - 1; i >= 0; i--) {
            val <<= 2;
            val |= aux.get(i);
        }
        return val;
    }

    private int getVal(int cur, int idx) {
        int tmp = cur >>> (idx * 2);
        return (int) (tmp & 3L);
    }

    private int addVal(int cur, int idx, int val) {
        return cur + (val << (idx * 2));
    }

    boolean isValid(int state, int len) {
        for (int i = 0; i < len - 1; i += 2) {
            int genFloor = getVal(state, i);
            for (int j = 1; j < len - 1; j += 2) {
                if (j == i + 1) continue;
                int mcFloor = getVal(state, j);
                if (genFloor != mcFloor) continue;
                int matchingGen = getVal(state, j - 1);
                if (matchingGen != mcFloor) return false;
            }
        }
        return true;
    }

    boolean isFinished(int state, int len) {
        int tmp = state;
        for (int i = 0; i < len; i++) {
            if ((tmp & 3) != 3) return false;
            tmp >>>= 2;
        }
        return true;
    }


}