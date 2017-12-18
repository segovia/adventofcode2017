package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static adventofcode.y2017.D18_Duet_1.toOps;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D18_Duet_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D18_Duet_2.class);
        assertThat(run(fileInputs.get(0)), is(1L));
        assertThat(run(fileInputs.get(1)), is(3L));
        assertThat(run(fileInputs.get(2)), is(7620L));
    }

    private long run(String input) {
        Program program0 = new Program(input, new ArrayDeque<>(), new ArrayDeque<>(), 0);
        Program program1 = new Program(input, program0.otherQueue, program0.thisQueue, 1);

        long prevSendCount0 = -1;
        long prevSendCount1 = -1;
        while (true) {
            long sendCount0 = program0.run();
            long sendCount1 = program1.run();
            if (sendCount0 == prevSendCount0 && sendCount1 == prevSendCount1) break;
            prevSendCount0 = sendCount0;
            prevSendCount1 = sendCount1;
        }
        return prevSendCount1;
    }

    private static class Program {
        long[] regs = new long[26];
        D18_Duet_1.Op[] ops;
        int opIdx = 0;
        Deque<Long> thisQueue;
        Deque<Long> otherQueue;
        long sendCount = 0;

        Program(String input, Deque<Long> thisQueue, Deque<Long> otherQueue, int id) {
            ops = toOps(input.split("\\n"));
            this.thisQueue = thisQueue;
            this.otherQueue = otherQueue;
            regs['p' - 'a'] = id;
        }

        private long run() {
            while (opIdx < ops.length && opIdx >= 0) {
                D18_Duet_1.Op op = ops[opIdx];
                switch (op.opType) {
                    case SND:
                        otherQueue.add(op.getFirstVal(regs));
                        ++sendCount;
                        break;
                    case SET:
                        regs[op.firstReg - 'a'] = op.getSecondVal(regs);
                        break;
                    case ADD:
                        regs[op.firstReg - 'a'] += op.getSecondVal(regs);
                        break;
                    case MUL:
                        regs[op.firstReg - 'a'] *= op.getSecondVal(regs);
                        break;
                    case MOD:
                        regs[op.firstReg - 'a'] %= op.getSecondVal(regs);
                        break;
                    case RCV:
                        if (thisQueue.isEmpty()) return sendCount;
                        regs[op.firstReg - 'a'] = thisQueue.poll();
                        break;
                    case JGZ:
                        if (op.getFirstVal(regs) > 0) opIdx += op.getSecondVal(regs) - 1;
                        break;
                }
                ++opIdx;
            }
            return sendCount;
        }
    }

}
