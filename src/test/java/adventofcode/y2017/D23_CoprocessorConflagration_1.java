package adventofcode.y2017;

import adventofcode.AssemblyOp;
import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static adventofcode.AssemblyOp.toOps;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D23_CoprocessorConflagration_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D23_CoprocessorConflagration_1.class);
        assertThat(run(fileInputs.get(0)), is(3025L));
    }

    private long run(String input) {
        Program p = new Program(input, 0);
        p.run();
        return p.mulCount;
    }

    private static class Program {
        long[] regs = new long[26];
        AssemblyOp[] ops;
        int opIdx = 0;
        long mulCount = 0;

        Program(String input, long valOfA) {
            ops = toOps(input.split("\\n"));
            regs[0] = valOfA;
        }

        private void run() {
            while (opIdx < ops.length && opIdx >= 0) {
                AssemblyOp op = ops[opIdx];
                switch (op.type) {
                    case SET:
                        regs[op.firstReg - 'a'] = op.getSecondVal(regs);
                        break;
                    case SUB:
                        regs[op.firstReg - 'a'] -= op.getSecondVal(regs);
                        break;
                    case MUL:
                        regs[op.firstReg - 'a'] *= op.getSecondVal(regs);
                        ++mulCount;
                        break;
                    case JNZ:
                        if (op.getFirstVal(regs) != 0) opIdx += op.getSecondVal(regs) - 1;
                        break;
                }
                ++opIdx;
            }
        }
    }

}
