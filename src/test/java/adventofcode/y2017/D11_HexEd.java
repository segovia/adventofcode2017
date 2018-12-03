package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D11_HexEd {

    @Test
    public void test() throws IOException {
        assertThat(run("ne,ne,ne"), is("3 - 3"));
        assertThat(run("ne,ne,sw,sw"), is("0 - 2"));
        assertThat(run("ne,ne,s,s"), is("2 - 2"));
        assertThat(run("se,sw,se,sw,sw"), is("3 - 3"));
        List<String> fileInputs = Utils.getInputsFromFiles(D11_HexEd.class);
        assertThat(run(fileInputs.get(0)), is("715 - 1512"));
    }


    private String run(String input) {
        String[] moves = input.split(",");
        int x = 0, y = 0;
        int largestDist = 0;
        for (String move : moves) {
            switch (move) {
                case "n":
                    y += 2;
                    break;
                case "ne":
                    ++x;
                    ++y;
                    break;
                case "se":
                    ++x;
                    --y;
                    break;
                case "s":
                    y -= 2;
                    break;
                case "sw":
                    --x;
                    --y;
                    break;
                case "nw":
                    --x;
                    ++y;
                    break;
            }
            largestDist = Math.max(largestDist, distFromCenter(x, y));
        }
        return distFromCenter(x, y) + " - " + largestDist;
    }

    private int distFromCenter(int x, int y) {
        int xAbs = Math.abs(x);
        int yAbs = Math.abs(y);

        if (xAbs >= yAbs) {
            return xAbs;
        }
        return xAbs + (yAbs - xAbs) / 2;
    }
}
