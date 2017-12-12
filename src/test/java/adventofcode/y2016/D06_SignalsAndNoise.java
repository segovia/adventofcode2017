package adventofcode.y2016;

import adventofcode.Utils;
import org.junit.Test;

import java.util.List;
import java.util.function.BiFunction;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D06_SignalsAndNoise {

    @Test
    public void test() throws Exception {
        List<String> fileInputs = Utils.getInputsFromFiles(D06_SignalsAndNoise.class);
        assertThat(run(fileInputs.get(0)), is("easter - advent"));
        assertThat(run(fileInputs.get(1)), is("ikerpcty - uwpfaqrq"));
    }

    private String run(String input) throws Exception {
        String[] lines = input.split("\\n");
        int len = lines[0].length();
        char[] word1 = new char[len];
        char[] word2 = new char[len];
        for (int i = 0; i < len; i++) {
            int[] countMap = countChars(lines, i);
            word1[i] = (char) (getIdx(countMap, (old, cur) -> cur > old) + 'a');
            word2[i] = (char) (getIdx(countMap, (old, cur) -> cur < old && cur != 0) + 'a');
        }
        return new String(word1) + " - " + new String(word2);
    }

    private int[] countChars(String[] lines, int i) {
        int[] countMap = new int[26];
        for (String line : lines) {
            ++countMap[line.charAt(i) - 'a'];
        }
        return countMap;
    }

    private int getIdx(int[] countMap, BiFunction<Integer, Integer, Boolean> updateCurIdx) {
        int curIdx = 0;
        for (int j = 0; j < countMap.length; j++) {
            if (updateCurIdx.apply(countMap[curIdx], countMap[j])) curIdx = j;
        }
        return curIdx;
    }
}