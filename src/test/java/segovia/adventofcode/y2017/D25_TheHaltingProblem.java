package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D25_TheHaltingProblem {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D25_TheHaltingProblem.class);
        assertThat(run(fileInputs.get(0)), is(3));
        assertThat(run(fileInputs.get(1)), is(2794));
    }

    private int run(String input) {
        String[] lines = input.split("\\n");
        int steps = Integer.parseInt(lines[1].split("\\s")[5]);
        List<State> states = new ArrayList<>();
        for (int lineIndex = 3; lineIndex < lines.length; lineIndex += 10) {
            states.add(new State(lines, lineIndex));
        }

        Program p = new Program(states);
        return p.run(steps);
    }

    private static class Program {
        int curState;
        int curPos;
        Tape tape = new Tape();
        List<State> states;

        public Program(List<State> states) {
            this.states = states;
        }

        public int run (int steps) {
            for (int i = 0; i < steps; i++) {
                State s = states.get(curState);
                int val = tape.readVal(curPos);

                tape.writeVal(curPos, s.actions[val][0]);
                curPos += s.actions[val][1];
                curState = s.actions[val][2];
            }
            return tape.oneVals.size();
        }
    }

    private static class State {
        int id;
        int actions[][] = new int[2][];

        State(String[] lines, int lineIndex) {
            id = lines[lineIndex].charAt(9) - 'A';
            for (int i = 0; i < 2; i++) {
                actions[i] = new int[]{
                        lines[lineIndex + 2 + i * 4].charAt(22) - '0', // new val
                        lines[lineIndex + 3 + i * 4].charAt(27) == 'r' ? 1 : -1, // move dir
                        lines[lineIndex + 4 + i * 4].charAt(26) - 'A', // next state
                };
            }
        }
    }

    private static class Tape {
        Set<Integer> oneVals = new HashSet<>();

        void writeVal(int pos, int val) {
            if (val == 1) oneVals.add(pos);
            else oneVals.remove(pos);
        }

        int readVal(int pos) {
            return oneVals.contains(pos) ? 1 : 0;
        }
    }
}
