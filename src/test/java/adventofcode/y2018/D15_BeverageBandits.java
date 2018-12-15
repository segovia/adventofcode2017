package adventofcode.y2018;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D15_BeverageBandits {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(-27730));
        assertThat(run(fileInputs.get(1)), is(36334));
        assertThat(run(fileInputs.get(2)), is(39514));
        assertThat(run(fileInputs.get(3)), is(-27755));
        assertThat(run(fileInputs.get(4)), is(-28944));
        assertThat(run(fileInputs.get(5)), is(-18740));
        assertThat(run(fileInputs.get(6)), is(-221754));
    }

    @Test
    public void testFindMin() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(findMinAttack(fileInputs.get(0)), is(4988));
        assertThat(findMinAttack(fileInputs.get(1)), is(29064));
        assertThat(findMinAttack(fileInputs.get(2)), is(31284));
        assertThat(findMinAttack(fileInputs.get(3)), is(3478));
        assertThat(findMinAttack(fileInputs.get(4)), is(6474));
        assertThat(findMinAttack(fileInputs.get(5)), is(1140));
        assertThat(findMinAttack(fileInputs.get(6)), is(41972));
    }

    private static final Element WALL = new Element('#', -1, -1);
    private static final char SPACE_TYPE = '.';
    private static final char ELF_TYPE = 'E';
    private static final char GOBLIN_TYPE = 'G';
    private static final int DEFAULT_ATTACK = 3;

    private int run(String input) {
        return simulate(input, DEFAULT_ATTACK);
    }

    private int findMinAttack(String input) {
        int lo = 4;
        int hi = 200;
        int bestResult = 0;
        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            int result = simulate(input, mid);
            if (result > 0) {
                hi = mid - 1;
                bestResult = result;
            } else lo = mid + 1;
        }
        return bestResult;
    }

    private int simulate(String input, int elfAttack) {
        String[] split = input.split("\\r?\\n");
        Element[][] map = new Element[split.length][split[0].length()];
        int goblinCount = 0;
        int elfCount = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = split[i].charAt(j);
                map[i][j] = c == '#' ? WALL : (c == SPACE_TYPE ? new Element(c, i, j) : new Unit(c, i, j, c == ELF_TYPE ? elfAttack : DEFAULT_ATTACK));
                if (map[i][j].type == ELF_TYPE) ++elfCount;
                if (map[i][j].type == GOBLIN_TYPE) ++goblinCount;
            }
        }

        for (int turn = 0; true; turn++) {
            for (Element[] row : map) {
                for (Element element : row) {
                    if (!(element instanceof Unit)) continue;
                    Unit u = (Unit) element;
                    if (u.turn != turn) continue;
                    ++u.turn;

                    if (goblinCount == 0 || elfCount == 0) return sumHP(map) * turn;

                    Optional<Unit> optionalEnemy = getAdjEnemy(map, u);
                    if (!optionalEnemy.isPresent()) {
                        move(map, u);
                        optionalEnemy = getAdjEnemy(map, u);
                    }
                    if (!optionalEnemy.isPresent()) continue;
                    Unit enemy = optionalEnemy.get();
                    doDamage(map, u, enemy);
                    if (enemy.health > 0) continue;
                    if (enemy.type == ELF_TYPE) {
                        if (elfAttack != DEFAULT_ATTACK) return -1;
                        --elfCount;
                    } else --goblinCount;

                }
            }
        }
    }

    private Integer sumHP(Element[][] map) {
        int sum = 0;
        for (Element[] row : map) {
            for (Element element : row) {
                if (!(element instanceof Unit)) continue;
                Unit u = (Unit) element;
                sum += u.health * (u.type == ELF_TYPE ? 1 : -1);
            }
        }
        return sum;
    }

    private void doDamage(Element[][] map, Unit attacker, Unit enemy) {
        enemy.health -= attacker.attack;
        if (enemy.health <= 0) {
            map[enemy.i][enemy.j] = new Element(SPACE_TYPE, enemy.i, enemy.j);
        }
    }

    private Optional<Unit> getAdjEnemy(Element[][] map, Unit u) {
        return getEnemyAdjToElement(map, u, u.type);
    }

    private Optional<Unit> getEnemyAdjToElement(Element[][] map, Element element, char unitType) {
        return getAdjElements(map, element).stream()
                .filter(e -> e instanceof Unit)
                .map(e -> (Unit) e)
                .filter(u -> u.type != unitType)
                .sorted()
                .findFirst();
    }

    private List<Element> getAdjElements(Element[][] map, Element e) {
        return Arrays.asList(
                map[e.i - 1][e.j],
                map[e.i][e.j - 1],
                map[e.i][e.j + 1],
                map[e.i + 1][e.j]);
    }

    private void move(Element[][] map, Unit u) {
        Optional<Element> targetSpace = findTarget(map, u, e -> getEnemyAdjToElement(map, e, u.type).isPresent());
        if (!targetSpace.isPresent()) return;
        Optional<Element> firstMoveSpace = findTarget(map, targetSpace.get(), e -> getAdjElements(map, e).stream()
                .anyMatch(ce -> ce == u));
        if (!firstMoveSpace.isPresent()) return;
        swapPositions(map, u, firstMoveSpace.get());
    }

    private void swapPositions(Element[][] map, Element a, Element b) {
        int auxI = a.i;
        int auxJ = a.j;
        a.i = b.i;
        a.j = b.j;
        b.i = auxI;
        b.j = auxJ;
        map[a.i][a.j] = a;
        map[b.i][b.j] = b;
    }

    private Optional<Element> findTarget(Element[][] map, Element start, Function<Element, Boolean> findCondition) {
        if (findCondition.apply(start)) return Optional.of(start);
        int[][] movMap = new int[map.length][map[0].length];
        movMap[start.i][start.j] = 1;

        boolean progressed = true;
        TreeSet<Element> found = new TreeSet<>();
        for (int step = 1; progressed && found.isEmpty(); step++) {
            progressed = false;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (movMap[i][j] != step) continue;
                    for (Element e : getAdjElements(map, map[i][j])) {
                        if (e.type != SPACE_TYPE || movMap[e.i][e.j] != 0) continue;
                        movMap[e.i][e.j] = step + 1;
                        progressed = true;
                        if (findCondition.apply(e)) found.add(e);
                    }
                }
            }
        }
        return found.isEmpty() ? Optional.empty() : Optional.of(found.iterator().next());
    }

    private static class Element<T extends Element> implements Comparable<T> {
        char type;
        int i, j;

        Element(char type, int i, int j) {
            this.type = type;
            this.i = i;
            this.j = j;
        }

        public String toString() {
            return type + " (" + i + "," + j + ")";
        }

        @Override
        public int compareTo(T o) {
            int r = Integer.compare(i, o.i);
            if (r != 0) return r;
            return Integer.compare(j, o.j);
        }
    }

    private static class Unit extends Element<Unit> implements Comparable<Unit> {
        int turn = 0;
        int attack;
        int health = 200;

        Unit(char type, int i, int j, int attack) {
            super(type, i, j);
            this.attack = attack;
        }

        @Override
        public int compareTo(Unit o) {
            int r = Integer.compare(health, o.health);
            if (r != 0) return r;
            return super.compareTo(o);
        }
    }
}
