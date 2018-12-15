package segovia.adventofcode.y2015;

import segovia.adventofcode.AssemblyOp;
import segovia.adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static segovia.adventofcode.AssemblyOp.toOps;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D23_OpeningTheTuringLock {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D23_OpeningTheTuringLock.class);
        assertThat(run(fileInputs.get(0), 0), is(0L));
        assertThat(run(fileInputs.get(1), 0), is(307L));
        assertThat(run(fileInputs.get(1), 1), is(160L));
    }

    private long run(String input, int aVal) throws Exception {
        AssemblyOp[] ops = toOps(input.split("\\n"));
        long[] regs = new long[2];
        regs[0] = aVal;
        int opIdx = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            AssemblyOp op = ops[opIdx];
            switch (op.type) {
                case HLF:
                    regs[op.firstReg - 'a'] /= 2;
                    break;
                case TPL:
                    regs[op.firstReg - 'a'] *= 3;
                    break;
                case INC:
                    regs[op.firstReg - 'a']++;
                    break;
                case JMP:
                    opIdx += op.getFirstVal(regs) - 1;
                    break;
                case JIO:
                    if (op.getFirstVal(regs) == 1) opIdx += op.getSecondVal(regs) - 1;
                    break;
                case JIE:
                    if (op.getFirstVal(regs) % 2 == 0) opIdx += op.getSecondVal(regs) - 1;
                    break;
            }
            ++opIdx;
        }
        return regs[1];
    }
}