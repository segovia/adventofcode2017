package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D21_FractalArt {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D21_FractalArt.class);
        assertThat(run(fileInputs.get(0), 2), is(12));
        assertThat(run(fileInputs.get(1), 5), is(171));
        assertThat(run(fileInputs.get(1), 18), is(2498142));
    }

    @Test
    public void testPatternMatch() throws IOException {
        char[][] cur = new char[][]{
                ".#.".toCharArray(),
                "..#".toCharArray(),
                "###".toCharArray()};
        String rest = " => ..../..../..../....";
        assertThat(new Rule(".#./..#/###" + rest).matchesPattern(cur, 0, 0, 3), is(true)); // norm
        assertThat(new Rule(".#./#../###" + rest).matchesPattern(cur, 0, 0, 3), is(true)); // hor
        assertThat(new Rule("###/..#/.#." + rest).matchesPattern(cur, 0, 0, 3), is(true)); // ver
        assertThat(new Rule("###/#../.#." + rest).matchesPattern(cur, 0, 0, 3), is(true)); // hor + ver
        assertThat(new Rule("#../#.#/##." + rest).matchesPattern(cur, 0, 0, 3), is(true)); // rot 1
    }

    private int run(String input, int iterations) {
        Rule[] rules = Arrays.stream(input.split("\\n")).map(Rule::new).toArray(Rule[]::new);
        char[][] cur = new char[][]{
                ".#.".toCharArray(),
                "..#".toCharArray(),
                "###".toCharArray()};
//        print(cur);
        for (int it = 0; it < iterations; it++) {
            int blockSize = cur.length % 2 == 0 ? 2 : 3;
            int nextSize = convertToNextSize(cur.length, cur.length);
            char[][] next = new char[nextSize][nextSize];

            for (int i = 0; i < cur.length; i += blockSize) {
                for (int j = 0; j < cur.length; j += blockSize) {
                    Rule rule = findMatchingRule(rules, cur, i, j, blockSize);
                    rule.writePattern(next, convertToNextSize(cur.length, i), convertToNextSize(cur.length, j));
                }
            }

            cur = next;
//            System.out.println();
//            print(cur);
        }
        return countHash(cur);
    }

    int convertToNextSize(int curSize, int val) {
        return curSize % 2 == 0 ? (val / 2) * 3 : (val / 3) * 4;
    }

    int countHash(char[][] matrix) {
        int count = 0;
        for (char[] line : matrix) {
            for (char c : line) {
                if (c == '#') ++count;
            }
        }
        return count;
    }

    private Rule findMatchingRule(Rule[] rules, char[][] matrix, int mi, int mj, int blockSize) {
        for (Rule r : rules) {
            if (r.matchesPattern(matrix, mi, mj, blockSize)) return r;
        }
        throw new RuntimeException("oops");
    }


    private static class Rule {
        char[][] pattern;
        char[][] output;

        Rule(String input) {
            String[] tokens = input.split(" => ");
            pattern = makeMatrix(tokens[0]);
            output = makeMatrix(tokens[1]);
        }

        private char[][] makeMatrix(String matrixStr) {
            String[] lines = matrixStr.split("/");
            char[][] matrix = new char[lines.length][lines.length];
            for (int i = 0; i < lines.length; i++) matrix[i] = lines[i].toCharArray();
            return matrix;
        }

        boolean matchesPattern(char[][] matrix, int mi, int mj, int blockSize) {
            if (blockSize != pattern.length) return false;
            for (int dir = 0; dir < 4 * 2; dir++) {
                if (matchesPattern(matrix, mi, mj, blockSize, dir)) {
                    return true;
                }
            }
            return false;
        }

        void writePattern(char[][] matrix, int mi, int mj) {
            for (int i = 0; i < output.length; i++) {
                System.arraycopy(output[i], 0, matrix[mi + i], mj, output.length);
            }
        }

        private boolean matchesPattern(char[][] matrix, int mi, int mj, int blockSize, int dir) {
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    int pi = i;
                    int pj = j;
                    if (dir % 4 == 1) { // rotate clockwise once
                        pi = j;
                        pj = blockSize - i - 1;
                    } else if (dir % 4 == 2) { // rotate clockwise twice
                        pi = blockSize - i - 1;
                        pj = blockSize - j - 1;
                    } else if (dir % 4 == 3) { // rotate clockwise thrice
                        pi = blockSize - j - 1;
                        pj = i;
                    }
                    if (dir / 4 == 1) pj = blockSize - pj - 1; // flip horizontally

                    if (pattern[pi][pj] != matrix[mi + i][mj + j]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
