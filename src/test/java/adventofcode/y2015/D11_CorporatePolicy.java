package adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D11_CorporatePolicy {

    @Test
    public void test() throws IOException {
        assertThat(run("abcdefgh"), is("abcdffaa"));
        assertThat(run("ghijklmn"), is("ghjaabcc"));
        assertThat(run("cqjxjnds"), is("cqjxxyzz"));
        assertThat(run("cqjxxyzz"), is("cqkaabcc"));
    }

    private String run(String input) {
        char[] cur = input.toCharArray();
        do {
            makeNext(cur);
        } while (!isOk(cur));
        return new String(cur);
    }

    private void makeNext(char[] cur) {
        boolean alreadyJumped = false;
        for (int i = 0; i < cur.length; i++) {
            char c = cur[i];
            if (alreadyJumped) {
                cur[i] = 'a';
            } else if (c == 'i' || c == 'o' || c == 'l') {
                alreadyJumped = true;
                cur[i] = (char) (c + 1);
            }
        }

        if (!alreadyJumped) {
            for (int i = cur.length - 1; i >= 0; i--) {
                char c = cur[i];
                if (c == 'z') cur[i] = 'a';
                else {
                    cur[i] = (char) (c + 1);
                    break;
                }
            }
        }
    }

    private boolean isOk(char[] input) {
        Character pair1 = null;
        Character pair2 = null;
        boolean containsASequence = false;
        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            if (i > 0) {
                char b = input[i - 1];
                if (c == b) {
                    if (pair1 == null) pair1 = c;
                    else if (pair2 == null && pair1 != c) pair2 = c;
                }
                if (i > 1 && (char) (input[i - 2] + 1) == b && (char) (b + 1) == c) containsASequence = true;
            }
        }
        return pair1 != null && pair2 != null && containsASequence;
    }
}