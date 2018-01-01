package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static adventofcode.y2016.D21_ScrambledLettersAndHash_1.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D21_ScrambledLettersAndHash_2 {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D21_ScrambledLettersAndHash_2.class);
        assertThat(run("fbdecgha", fileInputs.get(0)), is("abcdefgh"));
        assertThat(run("gcedfahb", fileInputs.get(1)), is("abcdefgh"));
        assertThat(run("fbgdceah", fileInputs.get(1)), is("hegbdcfa"));
    }

    private String run(String array, String input) throws Exception {
        String[] lines = input.split("\\n");
        char[] letters = array.toCharArray();
        char[] aux = new char[letters.length];
        int[] letterLookup = new int[letters.length];
        for (int i = 0; i < letters.length; i++) letterLookup[letters[i] - 'a'] = i;
        for (int i = lines.length - 1; i >= 0; i--) {
            String[] tokens = lines[i].split("\\s");
            switch (tokens[0]) {
                case "rotate":
                    if ("left".equals(tokens[1]))
                        rotateRight(letters, aux, letterLookup, Integer.parseInt(tokens[2]));
                    else if ("right".equals(tokens[1]))
                        rotateLeft(letters, aux, letterLookup, Integer.parseInt(tokens[2]));
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
                    move(letters, letterLookup, Integer.parseInt(tokens[5]), Integer.parseInt(tokens[2]));
                    break;
                default:
                    throw new RuntimeException("whoops");
            }
        }
        return new String(letters);
    }

    // based on input 8 letters
    // 0 -> 1 (1 move )
    // 1 -> 3 (2 moves)
    // 2 -> 5 (3 moves)
    // 3 -> 7 (4 moves)
    // 4 -> 2 (6 moves)
    // 5 -> 4 (7 moves)
    // 6 -> 6 (8 moves)
    // 7 -> 0 (9 moves)
    private static final int[] reverseMoves = new int[]{9, 1, 6, 2, 7, 3, 8, 4};

    private static void rotateBasedOnLetter(char[] letters, char[] aux, int[] letterLookup, char a) {
        int n = letterLookup[a - 'a'];
        rotateLeft(letters, aux, letterLookup, reverseMoves[n]);
    }
}