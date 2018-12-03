package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D09_StreamProcessing {

    @Test
    public void test() throws IOException {
        assertThat(run("{}"), is("1,0"));
        assertThat(run("{{{}}}"), is("6,0"));
        assertThat(run("{{},{}}"), is("5,0"));
        assertThat(run("{{{},{},{{}}}}"), is("16,0"));
        assertThat(run("{<a>,<a>,<a>,<a>}"), is("1,4"));
        assertThat(run("{{<ab>},{<ab>},{<ab>},{<ab>}}"), is("9,8"));
        assertThat(run("{{<!!>},{<!!>},{<!!>},{<!!>}}"), is("9,0"));
        assertThat(run("{{<a!>},{<a!>},{<a!>},{<ab>}}"), is("3,17"));
        assertThat(run("{<>}"), is("1,0"));
        assertThat(run("{<random characters>}"), is("1,17"));
        assertThat(run("{<<<<>}"), is("1,3"));
        assertThat(run("{<{!>}>}"), is("1,2"));
        assertThat(run("{<!!>}"), is("1,0"));
        assertThat(run("{<!!!>>}"), is("1,0"));
        assertThat(run("{<{o\"i!a,<{i<a>}"), is("1,10"));
        List<String> fileInputs = Utils.getInputsFromFiles(D09_StreamProcessing.class);
        assertThat(run(fileInputs.get(0)), is("12803,6425"));
    }


    private String run(String input) {
        int openCurlies = 0;
        boolean inTrash = false;
        boolean ignoreNext = false;
        long score = 0;
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (inTrash) {
                if (ignoreNext) {
                    ignoreNext = false;
                } else if (c == '>') {
                    inTrash = false;
                } else if (c == '!') {
                    ignoreNext = true;
                } else {
                    ++count;
                }
            } else {
                if (c == '<') {
                    inTrash = true;
                } else if (c == '{') {
                    ++openCurlies;
                } else if (c == '}') {
                    score += openCurlies;
                    --openCurlies;
                }
            }
        }

        return score + "," + count;
    }
}
