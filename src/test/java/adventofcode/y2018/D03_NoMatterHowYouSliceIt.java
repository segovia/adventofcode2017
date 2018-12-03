package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D03_NoMatterHowYouSliceIt {

    @Test
    public void test() throws IOException {
        assertThat(run("" +
                               "#1 @ 1,3: 4x4\n" +
                               "#2 @ 3,1: 4x4\n" +
                               "#3 @ 5,5: 2x2"
        ), is("4 3"));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("119572 775"));
    }

    private String run(String input) {
        List<Rectangle> rects = Arrays.
                stream(input.split("\\n")).
                map(Rectangle::new).
                collect(toList());
        Integer[][] screen = makeScreen(rects);
        long count = 0;
        for (int r = 0; r < rects.size(); r++) {
            Rectangle rect = rects.get(r);
            for (int i = 0; i < rect.height; i++) {
                int curTop = rect.top + i;
                for (int j = 0; j < rect.width; j++) {
                    int curLeft = rect.left + j;
                    if (screen[curTop][curLeft] == null) {
                        screen[curTop][curLeft] = r;
                    } else if (screen[curTop][curLeft] >= 0) {
                        rects.get(screen[curTop][curLeft]).hasOverlap = true;
                        rect.hasOverlap = true;
                        screen[curTop][curLeft] = -1;
                        ++count;
                    } else {
                        rect.hasOverlap = true;
                    }
                }
            }
        }
        //noinspection OptionalGetWithoutIsPresent
        Rectangle noOverlap = rects.stream().filter(r -> !r.hasOverlap).findFirst().get();
        return count + " " + noOverlap.id;
    }

    private Integer[][] makeScreen(List<Rectangle> rects) {
        int maxWidth = 0;
        int maxHeight = 0;
        int maxId = 0;
        for (Rectangle rect : rects) {
            maxWidth = Math.max(maxWidth, rect.left + rect.width);
            maxHeight = Math.max(maxHeight, rect.top + rect.height);
            maxId = Math.max(maxId, rect.id);
        }
        return new Integer[maxHeight][maxWidth];
    }

    private static class Rectangle {
        int id;
        int left;
        int top;
        int width;
        int height;
        boolean hasOverlap = false;

        public Rectangle(String definition) {
            String[] tokens = definition.split("\\s");
            id = Integer.parseInt(tokens[0].substring(1));
            String[] positions = tokens[2].substring(0, tokens[2].length() - 1).split(",");
            left = Integer.parseInt(positions[0]);
            top = Integer.parseInt(positions[1]);
            String[] size = tokens[3].split("x");
            width = Integer.parseInt(size[0]);
            height = Integer.parseInt(size[1]);
        }
    }
}
