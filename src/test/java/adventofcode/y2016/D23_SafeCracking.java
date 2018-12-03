package adventofcode.y2016;

import adventofcode.AssemblyOp;
import adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static adventofcode.AssemblyOp.Type.*;
import static adventofcode.AssemblyOp.toOps;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D23_SafeCracking {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D23_SafeCracking.class);
        assertThat(run(fileInputs.get(0), 0), is(3L));
        assertThat(run(fileInputs.get(1), 7), is(11736L));
        assertThat(run(fileInputs.get(2), 7), is(11736L));
        assertThat(run(fileInputs.get(2), 12), is(479008296L));
    }

    private long run(String input, long aVal) throws Exception {
        AssemblyOp[] ops = toOps(input.split("\\n"));
        long[] regs = new long[4];
        regs[0] = aVal;
        int opIdx = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            AssemblyOp op = ops[opIdx];
            switch (op.type) {
                case ADD:
                    doAdd(regs, op);
                    break;
                case MUL:
                    doMul(regs, op);
                    break;
                case TGL:
                    doTgl(regs, opIdx, ops);
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
        return regs[0];
    }

    static void doMul(long[] regs, AssemblyOp op) {
        regs[op.firstReg - 'a'] *= op.getSecondVal(regs);
    }

    static void doAdd(long[] regs, AssemblyOp op) {
        regs[op.firstReg - 'a'] += op.getSecondVal(regs);
    }

    static void doDec(long[] regs, AssemblyOp op) {
        regs[op.firstReg - 'a']--;
    }

    static void doTgl(long[] regs, int opIdx, AssemblyOp[] ops) {
        AssemblyOp tglOp = ops[opIdx];
        int val = (int) tglOp.getFirstVal(regs) + opIdx;
        if (val >= ops.length) return;
        AssemblyOp op = ops[val];
        switch (op.type) {
            case TGL:
                op.type = INC;
                break;
            case CPY:
                op.type = JNZ;
                break;
            case INC:
                op.type = DEC;
                break;
            case DEC:
                op.type = INC;
                break;
            case JNZ:
                op.type = CPY;
                break;
            default:
                throw new RuntimeException("whoops, dont know how to toggle");
        }
    }

    static void doInc(long[] regs, AssemblyOp op) {
        regs[op.firstReg - 'a']++;
    }

    static void doCpy(long[] regs, AssemblyOp op) {
        if (op.secondReg == null) return;
        regs[op.secondReg - 'a'] = op.getFirstVal(regs);
    }

    static int doJnz(long[] regs, int opIdx, AssemblyOp op) {
        if (op.getFirstVal(regs) != 0) return opIdx + (int) op.getSecondVal(regs) - 1;
        return opIdx;
    }
}