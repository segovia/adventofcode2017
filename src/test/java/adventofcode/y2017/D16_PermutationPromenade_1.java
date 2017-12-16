package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D16_PermutationPromenade_1 {

    @Test
    public void test() throws IOException {
//        assertThat(run("abcde", "s1,x3/4,pe/b"), is("baedc"));
        List<String> fileInputs = Utils.getInputsFromFiles(D16_PermutationPromenade_1.class);
        assertThat(run("abcdefghijklmnop",fileInputs.get(0)), is("jcobhadfnmpkglie"));
    }


    String run(String dancersInput, String input) {
        char[] dancers = dancersInput.toCharArray();
        char[] aux = new char[dancers.length];
        int[] letterLookup = new int[dancers.length];
        for (int i = 0; i < dancers.length; i++) letterLookup[dancers[i] - 'a'] = i;
        String[] moves = input.split(",");
        for (String move : moves) {
            switch (move.charAt(0)) {
                case 's':
                    spin(dancers, aux, letterLookup, Integer.parseInt(move.substring(1)));
                    break;
                case 'p':
                    partner(dancers, letterLookup, move.charAt(1), move.charAt(3));
                    break;
                case 'x':
                    int[] vals = Utils.toIntArray(move.substring(1).split("/"));
                    exchange(dancers, letterLookup, vals[0], vals[1]);
                    break;
            }
        }
        return new String(dancers);
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
