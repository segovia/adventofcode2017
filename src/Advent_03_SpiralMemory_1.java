import java.util.Arrays;
import java.util.List;

public class Advent_03_SpiralMemory_1 {

    private static final List<String> INPUTS = Arrays.asList(
            "1",
            "2",
            "12",
            "23",
            "1024",
            "277678");


    public static void main(String[] args) {
        for (String input : INPUTS) {
            System.out.println(run(input));
        }
    }

    private static long run(String inputStr) {
        int input = Integer.parseInt(inputStr);
        if (input == 1) return 0;

        int nextSq = 3;
        while (nextSq * nextSq < input) nextSq += 2;

        int curVal = (nextSq - 2) * (nextSq - 2);

        for (; true; curVal += nextSq - 1) {
            if (curVal + nextSq - 1 >= input) {
                return nextSq / 2 + Math.abs(input - curVal - nextSq / 2);
            }
        }
    }
}
