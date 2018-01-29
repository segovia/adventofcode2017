package adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D22_WizardSimulator20XX {

    @Test
    public void test() throws IOException {
        assertThat(run(55, 8, 100), is(78));
    }

    private int run(int bossHp, int bossDamage, int hp) {
        Effect[] effects = new Effect[] {
                new Effect(EffectType.ARMOR, 113,6,7),
                new Effect(EffectType.DAMAGE, 173,6,3),
                new Effect(EffectType.MANA, 229,5,101)
        };
        return 0;
    }

    private void recurse(int bossHp, int bossDamage, int hp, boolean bossTurn, Effect[] effects) {

    }

    private static class Effect {
        EffectType type;
        int cost;
        int lasts;
        int turnsLeft;
        int val;

        public Effect(EffectType type, int cost, int lasts, int val) {
            this.type = type;
            this.cost = cost;
            this.lasts = lasts;
            this.turnsLeft = 0;
            this.val = val;
        }
    }

    private enum EffectType {
        MANA, DAMAGE, ARMOR;
    }
}
