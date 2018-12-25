package segovia.adventofcode.y2018;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D24_ImmuneSystemSimulator20XX {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0), 0), is(-5216));
        assertThat(run(fileInputs.get(1), 0), is(-19974));
        assertThat(findMinAttackBoost(fileInputs.get(0)), is(51));
        assertThat(findMinAttackBoost(fileInputs.get(1)), is(4606));
    }

    private int findMinAttackBoost(String input) {
        int lo = 1;
        int hi = 5000;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            if (run(input, mid) > 0) hi = mid;
            else lo = mid + 1;
        }
        return run(input, hi);
    }

    private int run(String input, int immAttackBoost) {
        String[] lines = input.split("\\r?\\n");
        boolean infectionType = false;
        List<Group> groups = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("Infection")) infectionType = true;
            Group g = Group.makeGroup(line, infectionType, immAttackBoost);
            if (g != null) groups.add(g);
        }
        boolean damaged = true;
        while (hasType(groups, true) && hasType(groups, false) && damaged) {
            damaged = false;
            Collections.sort(groups);
            Map<Group, Group> targeting = new HashMap<>();
            Set<Group> targeted = new HashSet<>();
            for (Group g : groups) {
                Group target = selectTarget(groups, targeted, g);
                if (target == null || g.possibleDamage(target) == 0) continue;
                targeting.put(g, target);
                targeted.add(target);
            }
            groups.sort((a, b) -> Integer.compare(b.initiative, a.initiative));
            for (Group g : groups) {
                if (g.units <= 0) continue;
                if (g.doDamage(targeting.get(g))) damaged = true;
            }
            groups = groups.stream().filter(g -> g.units > 0).collect(toList());
        }
        if (hasType(groups, true) && hasType(groups, false)) return Integer.MIN_VALUE;
        return groups.stream().mapToInt(g -> g.units).sum() * (hasType(groups, true) ? -1 : 1);
    }

    private boolean hasType(List<Group> groups, boolean infectionType) {
        return groups.stream().anyMatch(g -> g.infectionType == infectionType);
    }

    private Group selectTarget(List<Group> groups, Set<Group> targeted, Group g) {
        return groups.stream()
                .filter(o -> g.infectionType != o.infectionType)
                .filter(o -> !targeted.contains(o))
                .min((a, b) -> {
                    int r = Integer.compare(g.possibleDamage(b), g.possibleDamage(a));
                    if (r != 0) return r;
                    return a.compareTo(b);
                })
                .orElse(null);
    }

    private static final Pattern PATTERN = Pattern.compile("" +
            "(?<units>\\d+) units each with (?<hp>\\d+) hit points (?:\\((?<qualities>.+)?\\) )?" +
            "with an attack that does (?<attackPower>\\d+) (?<attackType>.+) damage at initiative (?<initiative>\\d+)");

    private static class Group implements Comparable<Group> {
        boolean infectionType;
        int units, hp, attackPower, initiative;
        String attackType;
        Set<String> weakTo = new HashSet<>();
        Set<String> immuneTo = new HashSet<>();

        static Group makeGroup(String line, boolean infectionType, int immAttackBoost) {
            Matcher m = PATTERN.matcher(line);
            if (!m.find()) return null;
            Group g = new Group();
            g.infectionType = infectionType;
            g.units = Integer.parseInt(m.group("units"));
            g.hp = Integer.parseInt(m.group("hp"));
            g.attackPower = Integer.parseInt(m.group("attackPower")) + (infectionType ? 0 : immAttackBoost);
            g.initiative = Integer.parseInt(m.group("initiative"));
            g.attackType = m.group("attackType");
            parseQualities(g, m);
            return g;
        }

        private static void parseQualities(Group g, Matcher m) {
            String qualitiesStr = m.group("qualities");
            if (qualitiesStr == null) return;
            String[] qualities = qualitiesStr.split("; ");
            for (String quality : qualities) {
                boolean isWeakness = quality.startsWith("weak to ");
                String[] items = (isWeakness ? quality.substring(8) : quality.substring(10)).split(", ");
                for (String item : items) {
                    if (isWeakness) g.weakTo.add(item);
                    else g.immuneTo.add(item);
                }
            }
        }

        int getEffectivePower() {
            return units * attackPower;
        }

        int possibleDamage(Group o) {
            if (o == null || o.immuneTo.contains(attackType)) return 0;
            int multiplier = o.weakTo.contains(attackType) ? 2 : 1;
            return getEffectivePower() * multiplier;
        }

        boolean doDamage(Group o) {
            if (o == null) return false;
            int damage = possibleDamage(o);
            int lostUnits = damage / o.hp;
            o.units -= lostUnits;
            return lostUnits > 0;
        }

        @Override
        public String toString() {
            return (infectionType ? "Inf" : "Imm") + String.format("  P%6d I%2d U%5d  A%5d  H%5d", getEffectivePower(), initiative, units, attackPower, hp);
        }

        @Override
        public int compareTo(Group o) {
            int r = Integer.compare(o.getEffectivePower(), getEffectivePower());
            if (r != 0) return r;
            return Integer.compare(o.initiative, initiative);
        }
    }
}