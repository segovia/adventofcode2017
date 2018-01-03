package adventofcode;

public class AssemblyOp {
    public enum Type {
        SND, SET, ADD, MUL, MOD, RCV, JGZ, SUB, JNZ, CPY, INC, DEC, TGL, OUT;

        static Type get(String op) {
            for (Type type : Type.values()) if (type.name().equalsIgnoreCase(op)) return type;
            return null;
        }
    }

    public Type type;
    public Character firstReg;
    Long firstVal;
    public Character secondReg;
    Long secondVal;

    AssemblyOp(String opType, String first, String second) {
        this.type = Type.get(opType);
        if (isReg(first)) firstReg = first.charAt(0);
        else firstVal = Long.parseLong(first);
        if (second == null) return;
        if (isReg(second)) secondReg = second.charAt(0);
        else secondVal = Long.parseLong(second);
    }

    boolean isReg(String str) {
        return str.length() == 1 && str.charAt(0) >= 'a' && str.charAt(0) <= 'z';
    }

    public long getFirstVal(long[] regs) {
        return firstVal != null ? firstVal : regs[firstReg - 'a'];
    }

    public long getSecondVal(long[] regs) {
        return secondVal != null ? secondVal : regs[secondReg - 'a'];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name().toLowerCase());
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

    public static AssemblyOp[] toOps(String[] opStr) {
        AssemblyOp[] ops = new AssemblyOp[opStr.length];
        for (int i = 0; i < opStr.length; i++) {
            String[] tokens = opStr[i].split("\\s");
            ops[i] = new AssemblyOp(tokens[0], tokens[1], tokens.length > 2 ? tokens[2] : null);
        }
        return ops;
    }
}
