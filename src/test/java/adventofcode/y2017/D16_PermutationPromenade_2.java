package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D16_PermutationPromenade_2 {

    @Test
    public void test() throws IOException {
        assertThat(runInLoop("abcde", "s1,x3/4,pe/b"), is("abcde"));
        List<String> fileInputs = Utils.getInputsFromFiles(D16_PermutationPromenade_2.class);
        assertThat(runInLoop("abcdefghijklmnop", fileInputs.get(0)), is("pclhmengojfdkaib"));
    }

    private String runInLoop(String dancersInput, String input) {
        int reps = 1_000_000_000;
        int[][] ops = readOps(input);
        char[] dancers = dancersInput.toCharArray();
        char[] original = Arrays.copyOf(dancers, dancers.length);
        int loop = 0;
        for (int i = 0; i < reps; i++) {
            run(dancers, ops);
            if (Arrays.equals(dancers, original)) {
                // we looped
                loop = i + 1;
                break;
            }
        }
        System.out.println(dancers);
        System.out.println(loop);
        int actualNeeded = reps % loop;
        System.out.println(actualNeeded);
        for (int i = 0; i < actualNeeded; i++) {
            run(dancers, ops);
        }
        return new String(dancers);
    }

    private int[][] readOps(String input) {
        String[] moves = input.split(",");
        int[][] ops = new int[moves.length][3];
        for (int i = 0; i < moves.length; i++) {
            switch (moves[i].charAt(0)) {
                case 's':
                    ops[i] = new int[]{0 , Integer.parseInt(moves[i].substring(1)), 0};
                    break;
                case 'p':
                    ops[i] = new int[]{1 , moves[i].charAt(1), moves[i].charAt(3)};
                    break;
                case 'x':
                    int[] vals = Utils.toIntArray(moves[i].substring(1).split("/"));
                    ops[i] = new int[]{2 , vals[0], vals[1]};
                    break;
            }
        }
        return ops;
    }


    private void run(char[] dancers, int[][] ops) {
        char[] aux = new char[dancers.length];
        int[] letterLookup = new int[dancers.length];
        for (int i = 0; i < dancers.length; i++) letterLookup[dancers[i] - 'a'] = i;
        for (int[] op : ops) {
            switch (op[0]) {
                case 0:
                    spin(dancers, aux, letterLookup, op[1]);
                    break;
                case 1:
                    partner(dancers, letterLookup,(char) op[1], (char)op[2]);
                    break;
                case 2:
                    exchange(dancers, letterLookup, op[1], op[2]);
                    break;
            }
        }
    }

    private void partner(char[] dancers, int[] letterLookup, char a, char b) {
        exchange(dancers, letterLookup, letterLookup[a - 'a'], letterLookup[b - 'a']);
    }

    private void exchange(char[] dancers, int[] letterLookup, int i, int j) {
        char aux = dancers[i];
        dancers[i] = dancers[j];
        dancers[j] = aux;
        letterLookup[dancers[i] - 'a'] = i;
        letterLookup[dancers[j] - 'a'] = j;
    }

    private void spin(char[] dancers, char[] aux, int[] letterLookup, int n) {
        for (int i = 0; i < dancers.length; i++) aux[(i + n) % dancers.length] = dancers[i];
        for (int i = 0; i < dancers.length; i++) {
            dancers[i] = aux[i];
            letterLookup[dancers[i] - 'a'] = i;
        }
    }

}
