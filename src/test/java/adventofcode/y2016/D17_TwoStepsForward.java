package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D17_TwoStepsForward {

    @Test
    public void test() throws Exception {
        assertThat(run("ihgpwlah", false), is("DDRRRD"));
        assertThat(run("kglvqrro", false), is("DDUDRLRRUDRD"));
        assertThat(run("ulqzkmiv", false), is("DRURDRUDDLLDLUURRDULRLDUUDDDRR"));
        assertThat(run("awrkjxxr", false), is("RDURRDDLRD"));

        assertThat(run("ihgpwlah", true), is("370"));
        assertThat(run("kglvqrro", true), is("492"));
        assertThat(run("ulqzkmiv", true), is("830"));
        assertThat(run("awrkjxxr", true), is("526"));
    }

    private String run(String input, boolean longest) throws Exception {
        int width = 4;
        int height = 4;

        Deque<String> paths = new ArrayDeque<>();
        paths.add("");
        int longestPathLen = 0;
        while (!paths.isEmpty()) {
            String path = paths.poll();
            int posX = 0;
            int posY = 0;
            for (int i = 0; i < path.length(); i++) {
                switch (path.charAt(i)) {
                    case 'U':
                        --posY;
                        break;
                    case 'D':
                        ++posY;
                        break;
                    case 'L':
                        --posX;
                        break;
                    case 'R':
                        ++posX;
                        break;
                }
            }
            if (posX == 3 && posY == 3) {
                if (!longest) return path;
                longestPathLen = path.length();
                continue;
            }

            String md5 = Utils.doMD5(input + path);
            if (posY > 0 && md5.charAt(0) >= 'b' && md5.charAt(0) <= 'f') paths.add(path + 'U');
            if (posY < height - 1 && md5.charAt(1) >= 'b' && md5.charAt(1) <= 'f') paths.add(path + 'D');
            if (posX > 0 && md5.charAt(2) >= 'b' && md5.charAt(2) <= 'f') paths.add(path + 'L');
            if (posX < width - 1 && md5.charAt(3) >= 'b' && md5.charAt(3) <= 'f') paths.add(path + 'R');
        }
        return String.valueOf(longestPathLen);
    }
}