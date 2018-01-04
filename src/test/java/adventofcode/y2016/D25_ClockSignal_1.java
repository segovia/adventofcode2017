package adventofcode.y2016;

import adventofcode.AssemblyOp;
import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static adventofcode.AssemblyOp.toOps;
import static adventofcode.y2016.D23_SafeCracking.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D25_ClockSignal_1 {

    @Test
    public void test() throws Exception {
        // this is basically used to double check the reducing done in D25_ClockSignal_2
        List<String> fileInputs = Utils.getInputsFromFiles(D25_ClockSignal_1.class);
        assertThat(run(fileInputs.get(0), 1), is("101011111001"));
        assertThat(run(fileInputs.get(0), 2), is("011011111001"));
        assertThat(run(fileInputs.get(0), 3), is("111011111001"));

        // answer found by reducing the code to what is found in D25_ClockSignal_2
        assertThat(run(fileInputs.get(0), 0xaaa - 2548 /* 182 */), is("010101010101"));
    }

    private String run(String input, int aVal) throws Exception {
        StringBuilder sb = new StringBuilder();
        AssemblyOp[] ops = toOps(input.split("\\n"));
        long[] regs = new long[4];
        regs[0] = aVal;
        int opIdx = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            AssemblyOp op = ops[opIdx];
            switch (op.type) {
                case OUT:
                    sb.append(op.getFirstVal(regs));
                    break;
                case ADD:
                    doAdd(regs, op);
                    break;
                case MUL:
                    doMul(regs, op);
                    break;
                case CPY:
                    doCpy(regs, op);
                    break;
                case INC:
                    doInc(regs, op);
                    break;
                case DEC:
                    doDec(regs, op);
                    break;
                case JNZ:
                    opIdx = doJnz(regs, opIdx, op);
                    break;
            }
            ++opIdx;
        }
        return sb.toString();
    }
}