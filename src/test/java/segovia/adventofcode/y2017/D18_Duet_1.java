package segovia.adventofcode.y2017;

import segovia.adventofcode.AssemblyOp;
import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static segovia.adventofcode.AssemblyOp.toOps;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D18_Duet_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D18_Duet_1.class);
        assertThat(run(fileInputs.get(0)), is(4L));
        assertThat(run(fileInputs.get(1)), is(0L));
        assertThat(run(fileInputs.get(2)), is(9423L));
    }

    private long run(String input) {
        AssemblyOp[] ops = toOps(input.split("\\n"));
        long[] regs = new long[26];

        int opIdx = 0;
        long lastSound = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            AssemblyOp op = ops[opIdx];
            switch (op.type) {
                case SND:
                    lastSound = op.getFirstVal(regs);
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
                    if (op.getFirstVal(regs) != 0) return lastSound;
                    break;
                case JGZ:
                    if (op.getFirstVal(regs) > 0) opIdx += op.getSecondVal(regs) - 1;
                    break;
            }
            ++opIdx;
        }
        return lastSound;
    }

}
