package segovia.adventofcode.y2016;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D18_LikeARogue {

    @Test
    public void test() throws Exception {
        assertThat(run(".^^.^.^^^^", 10), is(38));
        assertThat(run("^^.^..^.....^..^..^^...^^.^....^^^.^.^^....^.^^^...^^^^.^^^^.^..^^^^.^^.^.^.^.^.^^..." +
                "^^..^^^..^.^^^^", 40), is(1961));
        assertThat(run("^^.^..^.....^..^..^^...^^.^....^^^.^.^^....^.^^^...^^^^.^^^^.^..^^^^.^^.^.^.^.^.^^..." +
                "^^..^^^..^.^^^^", 400000), is(20000795));

    }

    private int run(String input, int rows) throws Exception {
        int count = 0;
        int len = input.length();
        boolean[] prevLine = new boolean[input.length()];
        for (int i = 0; i < input.length(); i++) {
            prevLine[i] = input.charAt(i) == '.';
            if (prevLine[i]) ++count;
        }

        boolean[] nextLine = new boolean[prevLine.length];
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < len; j++) {
                boolean left = j == 0 || prevLine[j - 1];
                boolean right = j == len - 1 || prevLine[j + 1];
                nextLine[j] = left == right;
                if (nextLine[j]) ++count;
            }
            boolean[] aux = prevLine;
            prevLine = nextLine;
            nextLine = aux;
        }
        return count;
    }
}