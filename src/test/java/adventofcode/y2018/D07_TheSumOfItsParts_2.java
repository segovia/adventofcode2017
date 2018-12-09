package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D07_TheSumOfItsParts_2 {

    @Test
    public void test() throws IOException {
        String exampleInput = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.";
        assertThat(run(exampleInput, 1, 0), is("CABDFE 21"));
        assertThat(run(exampleInput, 2, 0), is("CABFDE 15"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0), 1, 0), is("LFMNJRTQVZCHIABKPXYEUGWDSO 351"));
        assertThat(run(fileInputs.get(0), 5, 60), is("LNRTFJMQZVCIHABKPXYEUGWDSO 1180"));
    }

    @SuppressWarnings("Duplicates")
    private String run(String input, int workers, int stepTime) {
        List<String> lines = Arrays.stream(input.split("\\r?\\n")).collect(toList());
        Map<Character, Node> nodeMap = new HashMap<>();
        for (String line : lines) {
            Node a = nodeMap.computeIfAbsent(line.charAt(5), Node::new);
            Node b = nodeMap.computeIfAbsent(line.charAt(36), Node::new);
            a.children.add(b);
            b.preReqs.add(a);
        }
        PriorityQueue<Node> pq = nodeMap.values()
                .stream()
                .filter(n -> n.preReqs.isEmpty())
                .collect(java.util.stream.Collectors.toCollection(PriorityQueue::new));
        PriorityQueue<Node> workerQueue = new PriorityQueue<>();
        Set<Node> seen = new HashSet<>();
        Set<Node> done = new HashSet<>();
        StringBuilder order = new StringBuilder();
        int curTime = 0;
        while (!pq.isEmpty() || !workerQueue.isEmpty()) {
            if (!pq.isEmpty() && workerQueue.size() < workers) {
                Node cur = pq.poll();
                if (!seen.add(cur)) continue;
                cur.finishTime = curTime + stepTime + cur.id - 'A' + 1;
                workerQueue.add(cur);
                continue;
            }

            Node curDone = workerQueue.poll();
            done.add(curDone);
            order.append(curDone.id);
            curTime = curDone.finishTime;
            for (Node child : curDone.children) {
                if (!seen.contains(child) && done.containsAll(child.preReqs)) {
                    pq.add(child);
                }
            }
        }
        return order + " " + curTime;
    }

    private static class Node implements Comparable<Node> {
        char id;
        int finishTime;
        List<Node> preReqs = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        public Node(char id) {
            this.id = id;
        }

        @Override
        public int compareTo(Node o) {
            int result = Integer.compare(finishTime, o.finishTime);
            return result != 0 ? result : Character.compare(id, o.id);
        }

        public String toString() {
            return id + "";
        }
    }
}