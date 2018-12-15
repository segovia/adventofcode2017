package segovia.adventofcode.y2016;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class D01_NoTimeForATaxiCab_2 {

    @Test
    public void test() throws IOException {
        assertThat(run("R8, R4, R4, R8"), is(4));
        List<String> fileInputs = Utils.getInputsFromFiles(D01_NoTimeForATaxiCab_2.class);
        assertThat(run(fileInputs.get(0)), is(124));
    }

    @Test
    public void testIntersectOK() throws IOException {
        Line hor = new Line(new Pos(0,0), new Pos(8,0));
        Line ver = new Line(new Pos(5,-4), new Pos(5,4));
        Pos intersection = hor.intersects(ver);
        assertThat(intersection, is(notNullValue()));
        assertThat(intersection.x, is(5));
        assertThat(intersection.y, is(0));
    }

    @Test
    public void testIntersectNot() throws IOException {
        Line hor = new Line(new Pos(0,0), new Pos(8,0));
        Line ver = new Line(new Pos(4,-4), new Pos(8,-4));
        Pos intersection = hor.intersects(ver);
        assertThat(intersection, is(nullValue()));
    }

    private int run(String input) {
        Pos cur = new Pos(0, 0);
        int[] dirX = {0, 1, 0, -1};
        int[] dirY = {1, 0, -1, 0};
        int dir = 0;

        Set<Line> paths = new HashSet<>();
        String[] turns = input.split(", ");
        for (String turn : turns) {
            char c = turn.charAt(0);
            int step = Integer.parseInt(turn.substring(1, turn.length()));
            dir = c == 'R' ? (dir + 1) % 4 : (dir + 3) % 4;

            Line newPath = new Line(cur, new Pos(cur.x + dirX[dir] * (step -1), cur.y + dirY[dir] * (step - 1)));
            for (Line path : paths) {
                Pos intersection = path.intersects(newPath);
                if (intersection != null) {
                    return Math.abs(intersection.x) + Math.abs(intersection.y);
                }
            }
            paths.add(newPath);
            cur = new Pos(cur.x + dirX[dir] * step, cur.y + dirY[dir] * step);
        }
        return -1;
    }

    private static class Line {
        Pos a;
        Pos b;

        public Line(Pos a, Pos b) {
            // a will be bottom or left most point
            if (a.x < b.x || a.y < b.y) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
        }

        public boolean isHorizontal() {
            return a.y == b.y;
        }

        public Pos intersects(Line o) {
            if (this.a.x < o.a.x || this.a.y < o.a.y) {
                return intersects(this, o);
            }
            return intersects(o, this);
        }

        public Pos intersectsPerp(Line hor, Line ver) {
            if(ver.a.x >= hor.a.x && ver.a.x <= hor.b.x && ver.a.y <= hor.a.y && ver.b.y >= hor.a.y) {
                return new Pos(ver.a.x, hor.a.y);
            }
            return null;
        }

        public Pos intersects(Line c, Line o) {
            if (c.isHorizontal() == o.isHorizontal()) {
                // parallel
                if (c.isHorizontal()) {
                    return c.a.y == o.a.y && c.b.x >= o.a.x ? c.b : null;
                }
                return c.a.x == o.a.x && c.b.y >= o.a.y ? c.b : null;
            }
            if (c.isHorizontal()) {
                return intersectsPerp(c, o);
            }
            return intersectsPerp(o, c);
        }

        @Override
        public String toString() {
            return "[" + a + " " + b + "]";
        }
    }

    private static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
