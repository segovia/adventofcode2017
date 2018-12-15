package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SuppressWarnings("Duplicates")
public class D13_MineCartMadness {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0), false), is("7,3"));
        assertThat(run(fileInputs.get(2), false), is("136,36"));
        assertThat(run(fileInputs.get(1), true), is("6,4"));
        assertThat(run(fileInputs.get(2), true), is("53,111"));
    }

    private String run(String input, boolean removeCollisions) {
        String[] split = input.split("\\r?\\n");
        char[][] map = new char[split.length][];
        for (int i = 0; i < split.length; i++) {
            map[i] = split[i].toCharArray();
        }

        TreeSet<Cart> carts = new TreeSet<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'v' || map[i][j] == '^' || map[i][j] == '<' || map[i][j] == '>') {
                    carts.add(new Cart(map[i][j], i, j));
                    map[i][j] = map[i][j] == 'v' || map[i][j] == '^' ? '|' : '-';
                }
            }
        }
        while (true) {
            TreeSet<Cart> nonCollided = new TreeSet<>();
            for (Cart cart : carts) {
                boolean collides = nonCollided.contains(cart);
                if (!collides) {
                    char rail = map[cart.posI][cart.posJ];
                    move(cart, rail);
                    collides = !nonCollided.add(cart);
                }
                if (!collides) continue;
                if (!removeCollisions) return cart.toString();
                nonCollided.remove(cart);
            }
            if (nonCollided.size() == 1) {
                Cart cart = nonCollided.iterator().next();
                return cart.toString();
            }
            carts = nonCollided;
        }
    }

    private void move(Cart cart, char rail) {
        if (cart.orientation == '^') {
            if (rail == '|') {
                cart.goNorth();
            } else if (rail == '/') {
                cart.goEast();
            } else if (rail == '\\') {
                cart.goWest();
            } else if (rail == '+') {
                cart.turn();
            }
        } else if (cart.orientation == '>') {
            if (rail == '-') {
                cart.goEast();
            } else if (rail == '/') {
                cart.goNorth();
            } else if (rail == '\\') {
                cart.goSouth();
            } else if (rail == '+') {
                cart.turn();
            }
        } else if (cart.orientation == 'v') {
            if (rail == '|') {
                cart.goSouth();
            } else if (rail == '/') {
                cart.goWest();
            } else if (rail == '\\') {
                cart.goEast();
            } else if (rail == '+') {
                cart.turn();
            }
        } else if (cart.orientation == '<') {
            if (rail == '-') {
                cart.goWest();
            } else if (rail == '/') {
                cart.goSouth();
            } else if (rail == '\\') {
                cart.goNorth();
            } else if (rail == '+') {
                cart.turn();
            }
        }
    }

    private static class Cart implements Comparable<Cart> {
        char orientation; // ^,>,v,<
        int nextTurn = 0; // L,S,R
        int posI, posJ;

        public Cart(char c, int i, int j) {
            orientation = c;
            posI = i;
            posJ = j;
        }

        public void goNorth() {
            posI--;
            orientation = '^';
        }

        public void goEast() {
            posJ++;
            orientation = '>';
        }

        public void goSouth() {
            posI++;
            orientation = 'v';
        }

        public void goWest() {
            posJ--;
            orientation = '<';
        }

        public void turn() {
            if (orientation == '^' && nextTurn == 1 || orientation == '>' && nextTurn == 0 || orientation == '<' && nextTurn == 2) {
                goNorth();
            } else if (orientation == '^' && nextTurn == 2 || orientation == '>' && nextTurn == 1 || orientation == 'v' && nextTurn == 0) {
                goEast();
            } else if (orientation == '>' && nextTurn == 2 || orientation == 'v' && nextTurn == 1 || orientation == '<' && nextTurn == 0) {
                goSouth();
            } else if (orientation == '^' && nextTurn == 0 || orientation == 'v' && nextTurn == 2 || orientation == '<' && nextTurn == 1) {
                goWest();
            }
            nextTurn = (nextTurn + 1) % 3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cart cart = (Cart) o;
            return posI == cart.posI &&
                    posJ == cart.posJ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(posI, posJ);
        }

        @Override
        public int compareTo(Cart o) {
            int compare = Integer.compare(posI, o.posI);
            return compare == 0 ? Integer.compare(posJ, o.posJ) : compare;
        }

        public String toString() {
            return posJ + "," + posI;
        }
    }
}
