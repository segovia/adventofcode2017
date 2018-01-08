package adventofcode.y2015;

import adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D15_ScienceForHungryPeople {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D15_ScienceForHungryPeople.class);
        assertThat(run(fileInputs.get(0), false), is(62842880L));
        assertThat(run(fileInputs.get(1), false), is(21367368L));
        assertThat(run(fileInputs.get(0), true), is(57600000L));
        assertThat(run(fileInputs.get(1), true), is(1766400L));
    }

    private long run(String input, boolean calorieTarget) {
        String[] lines = input.split("\\n");
        Ingredient[] ings = Arrays.stream(lines).map(Ingredient::new).toArray(Ingredient[]::new);
        return findBest(ings, new int[ings.length], 0, 0, calorieTarget);
    }

    private long findBest(Ingredient[] ings, int[] usage, int depth, int curUsed, boolean calorieTarget) {
        if (depth == usage.length - 1) {
            usage[depth] = 100 - curUsed;
            long score = 1;
            int propsMax = calorieTarget ? 5 : 4;
            for (int i = 0; i < propsMax; i++) {
                int propValSum = 0;
                for (int j = 0; j < ings.length; j++) {
                    propValSum += ings[j].props[i] * usage[j];
                }
                if (propValSum < 0) propValSum = 0;
                if (i < 4) score *= propValSum;
                else if (propValSum != 500) return 0;
            }
            return score;
        }
        long bestScore = 0;
        for (int i = 0; i <= 100 - curUsed; i++) {
            usage[depth] = i;
            bestScore = Math.max(bestScore, findBest(ings, usage, depth + 1, curUsed + i, calorieTarget));
        }
        return bestScore;
    }

    private static class Ingredient {
        int[] props;

        Ingredient(String line) {
            String[] tokens = line.split("\\s");
            props = new int[]{
                    parseProp(tokens[2]),
                    parseProp(tokens[4]),
                    parseProp(tokens[6]),
                    parseProp(tokens[8]),
                    parseProp(tokens[10])
            };
        }

        private int parseProp(String token) {
            if (!Character.isDigit(token.charAt(token.length() - 1)))
                token = token.substring(0, token.length() - 1);
            return Integer.parseInt(token);
        }
    }
}