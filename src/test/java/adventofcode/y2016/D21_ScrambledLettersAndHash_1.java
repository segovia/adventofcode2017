package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D21_ScrambledLettersAndHash_1 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D21_ScrambledLettersAndHash_1.class);
        assertThat(run("abcde", fileInputs.get(0)), is("decab"));
        assertThat(run("abcdefgh", fileInputs.get(0)), is("fbdecgha"));
        assertThat(run("abcdefgh", fileInputs.get(1)), is("gcedfahb"));
    }

    String run(String array, String input) throws Exception {
        String[] lines = input.split("\\n");
        char[] letters = array.toCharArray();
        char[] aux = new char[letters.length];
        int[] letterLookup = new int[letters.length];
        for (int i = 0; i < letters.length; i++) letterLookup[letters[i] - 'a'] = i;
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            switch (tokens[0]) {
                case "rotate":
                    if ("left".equals(tokens[1]))
                        rotateLeft(letters, aux, letterLookup, Integer.parseInt(tokens[2]));
                    else if ("right".equals(tokens[1]))
                        rotateRight(letters, aux, letterLookup, Integer.parseInt(tokens[2]));
                    else
                        rotateBasedOnLetter(letters, aux, letterLookup, tokens[6].charAt(0));
                    break;
                case "swap":
                    if ("position".equals(tokens[1]))
                        swapPosition(letters, letterLookup, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[5]));
                    else
                        swapLetter(letters, letterLookup, tokens[2].charAt(0), tokens[5].charAt(0));
                    break;
                case "reverse":
                    reverse(letters, aux, letterLookup, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]));
                    break;
                case "move":
                    move(letters, letterLookup, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[5]));
                    break;
                default:
                    throw new RuntimeException("whoops");
            }
        }
        return new String(letters);
    }

    static void swapLetter(char[] letters, int[] letterLookup, char a, char b) {
        swapPosition(letters, letterLookup, letterLookup[a - 'a'], letterLookup[b - 'a']);
    }

    static void swapPosition(char[] letters, int[] letterLookup, int a, int b) {
        char aux = letters[a];
        letters[a] = letters[b];
        letters[b] = aux;
        updateLookup(letters, letterLookup, a);
        updateLookup(letters, letterLookup, b);
    }

    static void rotateLeft(char[] letters, char[] aux, int[] letterLookup, int n) {
        rotateRight(letters, aux, letterLookup, letters.length - n % letters.length);
    }

    static void rotateRight(char[] letters, char[] aux, int[] letterLookup, int n) {
        for (int i = 0; i < letters.length; i++) aux[(i + n) % letters.length] = letters[i];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = aux[i];
            updateLookup(letters, letterLookup, i);
        }
    }

    private static void rotateBasedOnLetter(char[] letters, char[] aux, int[] letterLookup, char a) {
        int n = letterLookup[a - 'a'];
        if (n >= 4) ++n;
        rotateRight(letters, aux, letterLookup, n + 1);
    }

    static void reverse(char[] letters, char[] aux, int[] letterLookup, int a, int b) {
        for (int k = 0; k <= b - a; k++) aux[a + k] = letters[b - k];
        for (int i = a; i <= b; i++) {
            letters[i] = aux[i];
            updateLookup(letters, letterLookup, i);
        }
    }

    static void move(char[] letters, int[] letterLookup, int a, int b) {
        if (a == b) return;
        int inc = b > a ? 1 : -1;
        char aux = letters[a];
        for (int i = a; i != b; i += inc) {
            letters[i] = letters[i + inc];
            updateLookup(letters, letterLookup, i);
        }
        letters[b] = aux;
        updateLookup(letters, letterLookup, b);
    }

    static void updateLookup(char[] letters, int[] letterLookup, int idx) {
        letterLookup[letters[idx] - 'a'] = idx;
    }
}