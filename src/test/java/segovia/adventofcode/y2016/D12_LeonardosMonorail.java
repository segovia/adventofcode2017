package segovia.adventofcode.y2016;

import segovia.adventofcode.AssemblyOp;
import segovia.adventofcode.Utils;
import org.junit.Test;

import java.util.List;

import static segovia.adventofcode.AssemblyOp.toOps;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D12_LeonardosMonorail {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D12_LeonardosMonorail.class);
        assertThat(run(fileInputs.get(0), 0), is(42L));
        assertThat(run(fileInputs.get(1), 0), is(318083L));
        assertThat(run(fileInputs.get(1), 1), is(9227737L));
    }

    private long run(String input, int cVal) throws Exception {
        AssemblyOp[] ops = toOps(input.split("\\n"));
        long[] regs = new long[26];
        regs['c'-'a'] = cVal;
        int opIdx = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            AssemblyOp op = ops[opIdx];
            switch (op.type) {
                case CPY:
                    regs[op.secondReg - 'a'] = op.getFirstVal(regs);
                    break;
                case INC:
                    regs[op.firstReg - 'a']++;
                    break;
                case DEC:
                    regs[op.firstReg - 'a']--;
                    break;
                case JNZ:
                    if (op.getFirstVal(regs) != 0) opIdx += op.getSecondVal(regs) - 1;
                    break;
            }
            ++opIdx;
        }
        return regs[0];
    }
}