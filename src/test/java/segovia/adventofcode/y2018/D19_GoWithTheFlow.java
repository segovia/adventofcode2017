package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D19_GoWithTheFlow {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(7));
        assertThat(run(fileInputs.get(1)), is(2047));
        assertThat(runJava(), is(24033240L));
    }

    private long runJava() {
        int rg2 = 2 * 2 * 19 * 11 + 8 * 22 + 12 + (27 * 28 + 29) * 30 * 14 * 32; // 10551424
        int sum = 0;
        for (int i = 1; i * i <= rg2; i++) {
            int otherFactor = rg2 / i;
            if (i * otherFactor != rg2) continue;
            sum += i;
            if (i != otherFactor) sum += otherFactor;
        }
        return sum;
    }

    private int run(String input) {
        String[] split = input.split("\\r?\\n");
        int ip = Integer.parseInt(split[0].substring(4));
        Instruction[] program = new Instruction[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            program[i - 1] = new Instruction(split[i]);
        }
        int[] reg = new int[6];
        for (; 0 <= reg[ip] && reg[ip] < program.length; reg[ip]++) {
            program[reg[ip]].exec(reg);
        }
        return reg[0];
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

        Op(BiConsumer<Instruction, int[]> method) {
            this.method = method;
        }

        static Op get(String name) {
            return Op.valueOf(name.toUpperCase());
        }
    }


    private static class Instruction {
        Op op;
        int a, b, reg;

        Instruction(String line) {
            String[] s = line.split(" ");
            op = Op.get(s[0]);
            a = Integer.parseInt(s[1]);
            b = Integer.parseInt(s[2]);
            reg = Integer.parseInt(s[3]);
        }

        void exec(int[] reg) {
            op.method.accept(this, reg);
        }
    }
}
