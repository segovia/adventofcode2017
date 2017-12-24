package adventofcode.y2017;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D18_Duet_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D18_Duet_1.class);
        assertThat(run(fileInputs.get(0)), is(4L));
        assertThat(run(fileInputs.get(1)), is(0L));
        assertThat(run(fileInputs.get(2)), is(9423L));
    }

    private long run(String input) {
        Op[] ops = toOps(input.split("\\n"));
        long[] regs = new long[26];

        int opIdx = 0;
        long lastSound = 0;
        while (opIdx < ops.length && opIdx >= 0) {
            Op op = ops[opIdx];
            switch (op.opType) {
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

    static Op[] toOps(String[] opStr) {
        Op[] ops = new Op[opStr.length];
        for (int i = 0; i < opStr.length; i++) {
            String[] tokens = opStr[i].split("\\s");
            ops[i] = new Op(tokens[0], tokens[1], tokens.length > 2 ? tokens[2] : null);
        }
        return ops;
    }

    static class Op {
        OpType opType;
        Character firstReg;
        Long firstVal;
        Character secondReg;
        Long secondVal;

        Op(String opType, String first, String second) {
            this.opType = OpType.get(opType);
            if (isReg(first)) firstReg = first.charAt(0);
            else firstVal = Long.parseLong(first);
            if (second == null) return;
            if (isReg(second)) secondReg = second.charAt(0);
            else secondVal = Long.parseLong(second);
        }

        boolean isReg(String str) {
            return str.length() == 1 && str.charAt(0) >= 'a' && str.charAt(0) <= 'z';
        }

        long getFirstVal(long[] regs) {
            return firstVal != null ? firstVal : regs[firstReg - 'a'];
        }

        long getSecondVal(long[] regs) {
            return secondVal != null ? secondVal : regs[secondReg - 'a'];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(opType.name().toLowerCase());
            sb.append(" ");
            if (firstVal != null) sb.append(firstVal);
            else sb.append(firstReg);
            if (secondVal != null || secondReg != null) {
                sb.append(" ");
                if (secondVal != null) sb.append(secondVal);
                else sb.append(secondReg);
            }
            return sb.toString();
        }
    }

    enum OpType {
        SND, SET, ADD, MUL, MOD, RCV, JGZ, SUB, JNZ;

        static OpType get(String op) {
            for (OpType opType : OpType.values()) if (opType.name().equalsIgnoreCase(op)) return opType;
            return null;
        }
    }
}
