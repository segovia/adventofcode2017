package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D07_TheSumOfItsParts_1 {

    public static final int START = 0;
    public static final int END = 500;

    @Test
    public void test() throws IOException {
        assertThat(run("Step C must be finished before step A can begin.\n" +
                               "Step C must be finished before step F can begin.\n" +
                               "Step A must be finished before step B can begin.\n" +
                               "Step A must be finished before step D can begin.\n" +
                               "Step B must be finished before step E can begin.\n" +
                               "Step D must be finished before step E can begin.\n" +
                               "Step F must be finished before step E can begin."
        ), is("CABDFE"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("LFMNJRTQVZCHIABKPXYEUGWDSO"));
    }

    private String run(String input) {
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
        StringBuilder result = new StringBuilder();
        Set<Node> printed = new HashSet<>();
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (!printed.add(cur)) continue;
            result.append(cur.id);
            for (Node child : cur.children) {
                if (!printed.contains(child) && printed.containsAll(child.preReqs)) {
                    pq.add(child);
                }
            }
        }
        return result.toString();
    }

    private static class Node implements Comparable<Node> {
        char id;
        List<Node> preReqs = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        public Node(char id) {
            this.id = id;
        }

        @Override
        public int compareTo(Node o) {
            return Character.compare(id, o.id);
        }

        public String toString() {
            return id + "";
        }
    }
}