package segovia.adventofcode.y2015;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D21_RPGSimulator20XX {

    @Test
    public void test() throws IOException {
        assertThat(run(new Entity("Boss", 0, 8, 1, 104), 100, true), is(78));
        assertThat(run(new Entity("Boss", 0, 8, 1, 104), 100, false), is(148));
    }

    private int run(Entity boss, int hp, boolean question1) {
        Item[] weapons = new Item[]{
                new Item("Dagger", 8, 4, 0),
                new Item("Shortsword", 10, 5, 0),
                new Item("Warhammer", 25, 6, 0),
                new Item("Longsword", 40, 7, 0),
                new Item("Greataxe", 74, 8, 0)
        };

        Item[] armor = new Item[]{
                new Item("Leather", 13, 0, 1),
                new Item("Chainmail", 31, 0, 2),
                new Item("Splintmail", 53, 0, 3),
                new Item("Bandedmail", 75, 0, 4),
                new Item("Platemail", 102, 0, 5)
        };

        Item[] rings = new Item[]{
                new Item("Damage +1", 25, 1, 0),
                new Item("Damage +2", 50, 2, 0),
                new Item("Damage +3", 100, 3, 0),
                new Item("Defense +1", 20, 0, 1),
                new Item("Defense +2", 40, 0, 2),
                new Item("Defense +3", 80, 0, 3)
        };

        return recurse(weapons, armor, rings, new Entity("Person", 0, 0, 0, hp), boss, 0, question1);
    }

    private int recurse(Item[] weapons, Item[] armor, Item[] rings, Entity person, Entity boss, int step, boolean question1) {
        if (step == 3) {
            if (question1) {
                return isOk(person, boss) ? person.cost : Integer.MAX_VALUE;
            }
            return !isOk(person, boss) ? person.cost : 0;
        }

        Item[] curItems = step == 0 ? weapons : (step == 1 ? armor : rings);

        int bestCost = question1 ? Integer.MAX_VALUE : 0;
        if (step > 0) {
            bestCost = getBest(weapons, armor, rings, person, boss, step, question1, bestCost);
        }

        for (Item item : curItems) {
            addItem(person, item);
            bestCost = getBest(weapons, armor, rings, person, boss, step, question1, bestCost);
            removeItem(person, item);
        }

        if (step == 2) {
            for (int i = 0; i < curItems.length - 1; i++) {
                addItem(person, curItems[i]);
                for (int j = i + 1; j < curItems.length; j++) {
                    addItem(person, curItems[j]);
                    bestCost = getBest(weapons, armor, rings, person, boss, step, question1, bestCost);
                    removeItem(person, curItems[j]);
                }
                removeItem(person, curItems[i]);
            }
        }
        return bestCost;
    }

    private int getBest(Item[] weapons, Item[] armor, Item[] rings, Entity person, Entity boss, int step, boolean question1, int bestCost) {
        int cost = recurse(weapons, armor, rings, person, boss, step + 1, question1);
        return question1 ? Math.min(cost, bestCost) : Math.max(cost, bestCost);
    }

    private boolean isOk(Entity person, Entity boss) {
        int hitsToKillBoss = hitsToKill(person, boss);
        int hitsToKillPerson = hitsToKill(boss, person);
        return hitsToKillBoss <= hitsToKillPerson;
    }

    private int hitsToKill(Entity a, Entity b) {
        int damageOnBPerTurn = Math.max(1, a.damage - b.armor);
        int hitsToKillB = b.hp / damageOnBPerTurn;
        if (hitsToKillB * damageOnBPerTurn < b.hp) ++hitsToKillB;
        return hitsToKillB;
    }


    private void addItem(Item person, Item item) {
        person.cost += item.cost;
        person.damage += item.damage;
        person.armor += item.armor;
    }

    private void removeItem(Item person, Item item) {
        person.armor -= item.armor;
        person.damage -= item.damage;
        person.cost -= item.cost;
    }

    private class Item {
        String name;
        int cost;
        int damage;
        int armor;

        public Item(String name, int cost, int damage, int armor) {
            this.name = name;
            this.cost = cost;
            this.armor = armor;
            this.damage = damage;
        }
    }

    private class Entity extends Item {
        int hp;

        public Entity(String name, int cost, int damage, int armor, int hp) {
            super(name, cost, damage, armor);
            this.hp = hp;
        }
    }
}
