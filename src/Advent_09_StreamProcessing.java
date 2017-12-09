import java.io.IOException;
import java.util.*;

public class Advent_09_StreamProcessing {


    private static final List<String> INPUTS = Arrays.asList(
            "{}",
            "{{{}}}",
            "{{},{}}",
            "{{{},{},{{}}}}",
            "{<a>,<a>,<a>,<a>}",
            "{{<ab>},{<ab>},{<ab>},{<ab>}}",
            "{{<!!>},{<!!>},{<!!>},{<!!>}}",
            "{{<a!>},{<a!>},{<a!>},{<ab>}}",
            "{<>}",
            "{<random characters>}",
            "{<<<<>}",
            "{<{!>}>}",
            "{<!!>}",
            "{<!!!>>}",
            "{<{o\"i!a,<{i<a>}"
            );

    public static void main(String[] args) throws IOException {
        String className = Advent_09_StreamProcessing.class.getSimpleName();
        List<String> inputs = new ArrayList<>(INPUTS);
        inputs.addAll(Utils.getInputsFromFiles(className.substring(0, className.length() - 1)));
        for (String input : inputs) {
            System.out.println(run(input));
        }
    }

    private static String run(String input) {
        int openCurlies = 0;
        boolean inTrash = false;
        boolean ignoreNext = false;
        long score = 0;
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (inTrash) {
                if (ignoreNext) {
                    ignoreNext = false;
                } else if (c == '>') {
                    inTrash = false;
                } else if (c == '!') {
                    ignoreNext = true;
                } else {
                    ++count;
                }
            } else {
                if (c == '<') {
                    inTrash = true;
                } else if (c == '{') {
                    ++openCurlies;
                } else if (c == '}') {
                    score += openCurlies;
                    --openCurlies;
                }
            }
        }

        return score + "\t\t" + count;
    }
}
