package adventofcode.y2016;

import adventofcode.y2016.D13_AMazeOfTwistyLittleCubicles_1.Pos;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static adventofcode.y2016.D13_AMazeOfTwistyLittleCubicles_1.addNext;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D13_AMazeOfTwistyLittleCubicles_2 {

    @Test
    public void test() throws Exception {
        assertThat(run(1358, 50), is(141L));
    }

    private long run(int input, int steps) throws Exception {
        List<Pos> nextList = new ArrayList<>();
        nextList.add(new Pos(1, 1));
        Set<Object> visited = new HashSet<>();
        for (int i = 0; i <= steps && !nextList.isEmpty(); i++) {
            List<Pos> curList = nextList;
            nextList = new ArrayList<>();
            for (Pos cur : curList) {
                if (!visited.add(cur)) continue;
                addNext(nextList, cur.x + 1, cur.y, input);
                addNext(nextList, cur.x - 1, cur.y, input);
                addNext(nextList, cur.x, cur.y + 1, input);
                addNext(nextList, cur.x, cur.y - 1, input);
            }
        }
        return visited.size();
    }
}