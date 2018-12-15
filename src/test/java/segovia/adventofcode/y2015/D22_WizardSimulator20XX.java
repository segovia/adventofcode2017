package segovia.adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D22_WizardSimulator20XX {

    @Test
    public void test() throws IOException {
        assertThat(run(13, 8, 10, 250, false), is(226));
        assertThat(run(14, 8, 10, 250, false), is(641));
        assertThat(run(55, 8, 50, 500, false), is(953));
        assertThat(run(55, 8, 50, 500, true), is(1289));
    }

    private Effect[] effects;
    private int bossDamage;
    private boolean hardMode;

    private int run(int bossHp, int bossDamage, int hp, int mana, boolean hardMode) {
        effects = new Effect[]{
                new Effect(EffectType.ARMOR, 113, 6, 7),
                new Effect(EffectType.DAMAGE, 173, 6, 3),
                new Effect(EffectType.MANA, 229, 5, 101)
        };
        this.bossDamage = bossDamage;
        this.hardMode = hardMode;
        return recurse(bossHp, hp, mana, false, 0, 0, Integer.MAX_VALUE);
    }

    private int recurse(int bossHp, int hp, int mana, boolean bossTurn, int spentMana, int depth, int curBest) {
        int curHp = !bossTurn && hardMode ? hp - 1 : hp;
        if (curHp <= 0 || spentMana >= curBest) return Integer.MAX_VALUE;

        int curBossHp = bossHp;
        int curMana = mana;
        int curArmor = 0;

        for (Effect e : effects) {
            if (!e.isInEffect(depth)) continue;
            if (e.type == EffectType.MANA) curMana += e.val;
            else if (e.type == EffectType.ARMOR) curArmor = e.val;
            else if (e.type == EffectType.DAMAGE) curBossHp -= e.val;
        }

        if (curBossHp <= 0) return spentMana;

        int bestMana = curBest;
        if (bossTurn) {
            int nextHp = curHp - max(1, bossDamage - curArmor);
            return recurse(curBossHp, nextHp, curMana, false, spentMana, depth + 1, bestMana);
        }

        // missile
        if (curMana >= 53) {
            int curSpent = recurse(curBossHp - 4, curHp, curMana - 53, true, spentMana + 53, depth + 1, bestMana);
            bestMana = min(bestMana, curSpent);
        }
        // drain
        if (curMana >= 73) {
            int curSpent = recurse(curBossHp - 2, curHp + 2, curMana - 73, true, spentMana + 73, depth + 1, bestMana);
            bestMana = min(bestMana, curSpent);
        }
        for (Effect e : effects) {
            if (e.canBeApplied(depth) && e.cost <= curMana) {
                int prevStartedOn = e.startedOn;
                e.startedOn = depth;
                int curSpent = recurse(curBossHp,
                                       curHp,
                                       curMana - e.cost,
                                       true,
                                       spentMana + e.cost,
                                       depth + 1,
                                       bestMana);
                bestMana = min(bestMana, curSpent);
                e.startedOn = prevStartedOn;
            }
        }
        return bestMana;
    }

    private static class Effect {
        EffectType type;
        int cost;
        int lasts;
        int startedOn;
        int val;

        public Effect(EffectType type, int cost, int lasts, int val) {
            this.type = type;
            this.cost = cost;
            this.lasts = lasts;
            this.startedOn = Integer.MIN_VALUE;
            this.val = val;
        }

        private boolean isInEffect(int depth) {
            return depth - lasts <= startedOn;
        }

        private boolean canBeApplied(int depth) {
            return depth - lasts >= startedOn;
        }
    }

    private enum EffectType {
        MANA, DAMAGE, ARMOR;
    }
}
