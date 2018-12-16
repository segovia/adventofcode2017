package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D16_ChronalClassification_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("612 485"));
    }

    private String run(String input) {
        String[] split = input.split("\\r?\\n");
        int matchingSamples = 0;
        int lastSampleLine;
        for (int i = 0; true; i++) {
            if (split[i].isEmpty()) {
                lastSampleLine = i;
                break;
            }
            int[] beforeReg = toReg(split[i++]);
            Instruction instruction = new Instruction(split[i++]);
            int[] afterReg = toReg(split[i++]);

            int curMatches = 0;
            for (Op op : Op.values()) {
                if (matches(instruction, beforeReg, afterReg, op)) ++curMatches;
                else op.removePossibleCode(instruction.code);
            }
            if (curMatches >= 3) ++matchingSamples;
        }

        int[] reg = new int[4];
        for (int i = lastSampleLine; i < split.length; i++) {
            if (split[i].isEmpty()) continue;
            Instruction instruction = new Instruction(split[i]);
            Op.get(instruction.code).exec(instruction, reg);
        }
        return matchingSamples + " " + reg[0];

    }

    private boolean matches(Instruction instruction, int[] before, int[] after, Op op) {
        int[] reg = Arrays.copyOf(before, before.length);
        op.exec(instruction, reg);
        return Arrays.equals(reg, after);
    }

    private int[] toReg(String line) {
        String[] split = line.substring(9, line.length() - 1).split(", ");
        int[] reg = new int[4];
        for (int i = 0; i < reg.length; i++) {
            reg[i] = Integer.parseInt(split[i]);
        }
        return reg;
    }

    private enum Op {
        ADDR((i, r) -> r[i.reg] = r[i.a] + r[i.b]),
        ADDI((i, r) -> r[i.reg] = r[i.a] + i.b),
        MULR((i, r) -> r[i.reg] = r[i.a] * r[i.b]),
        MULI((i, r) -> r[i.reg] = r[i.a] * i.b),
        BANR((i, r) -> r[i.reg] = r[i.a] & r[i.b]),
        BANI((i, r) -> r[i.reg] = r[i.a] & i.b),
        BORR((i, r) -> r[i.reg] = r[i.a] | r[i.b]),
        BORI((i, r) -> r[i.reg] = r[i.a] | i.b),
        SETR((i, r) -> r[i.reg] = r[i.a]),
        SETI((i, r) -> r[i.reg] = i.a),
        GTIR((i, r) -> r[i.reg] = i.a > r[i.b] ? 1 : 0),
        GTRI((i, r) -> r[i.reg] = r[i.a] > i.b ? 1 : 0),
        GTRR((i, r) -> r[i.reg] = r[i.a] > r[i.b] ? 1 : 0),
        EQIR((i, r) -> r[i.reg] = i.a == r[i.b] ? 1 : 0),
        EQRI((i, r) -> r[i.reg] = r[i.a] == i.b ? 1 : 0),
        EQRR((i, r) -> r[i.reg] = r[i.a] == r[i.b] ? 1 : 0);

        BiConsumer<Instruction, int[]> method;
        Integer code;
        Set<Integer> possibleCodes = new HashSet<>();

        Op(BiConsumer<Instruction, int[]> method) {
            this.method = method;
            for (int i = 0; i < 16; i++) {
                possibleCodes.add(i);
            }
        }

        void removePossibleCode(int code) {
            if (!possibleCodes.remove(code) || possibleCodes.size() != 1) return;
            this.code = possibleCodes.iterator().next();
            for (Op op : Op.values()) {
                op.removePossibleCode(this.code);
            }
        }

        void exec(Instruction instruction, int[] reg) {
            method.accept(instruction, reg);
        }

        static Op get(int code) {
            for (Op c : Op.values()) {
                if (c.code == code) return c;
            }
            throw new RuntimeException("Oops");
        }
    }


    private static class Instruction {
        int code, a, b, reg;

        Instruction(String line) {
            String[] s = line.split(" ");
            code = Integer.parseInt(s[0]);
            a = Integer.parseInt(s[1]);
            b = Integer.parseInt(s[2]);
            reg = Integer.parseInt(s[3]);
        }
    }
}
