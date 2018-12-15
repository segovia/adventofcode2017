package segovia.adventofcode.y2018;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D08_MemoryManeuver {

    @Test
    public void test() throws IOException {
        assertThat(run("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"), is("138 66" ));
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is("45210 22793"));
    }

    private String run(String input) {
        int[] nums = Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        MetadataInfo metadataInfo = new MetadataInfo();
        countMetadata(nums, 0, metadataInfo);
        return metadataInfo.metadataSum + " " + metadataInfo.lastValue;
    }

    private int countMetadata(int[] nums, int pos, MetadataInfo metadataInfo) {
        int childCount = nums[pos];
        int metadataCount = nums[pos + 1];
        int curPos = pos + 2;
        int[] childrenValues = new int[childCount];
        for (int i = 0; i < childCount; i++) {
            curPos = countMetadata(nums, curPos, metadataInfo);
            childrenValues[i] = metadataInfo.lastValue;
        }
        metadataInfo.lastValue = 0;
        int lastMetadataSum = 0;
        for (int i = 0; i < metadataCount; i++) {
            int metadataIndex = nums[curPos + i];
            lastMetadataSum += metadataIndex;
            boolean metdataIndexIsValid = 1 <= metadataIndex && metadataIndex <= childCount;
            metadataInfo.lastValue += metdataIndexIsValid ? childrenValues[metadataIndex - 1] : 0;
        }
        if (childCount == 0) metadataInfo.lastValue = lastMetadataSum;
        metadataInfo.metadataSum += lastMetadataSum;
        return curPos + metadataCount;
    }

    private static class MetadataInfo {
        private int metadataSum = 0;
        private int lastValue = 0;
    }
}