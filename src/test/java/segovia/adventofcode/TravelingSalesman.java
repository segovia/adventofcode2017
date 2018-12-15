package segovia.adventofcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static segovia.adventofcode.Utils.swap;
import static org.hamcrest.core.Is.is;

public class TravelingSalesman {

    /**
     * Playing around with how fast we can calculate traveling salesman dist.
     */
    @Test
    public void test() throws IOException {
        int size = 15;
        int[][] distMap = makeDistMap(size);

        int[] path = new int[size];
        for (int i = 0; i < path.length; i++) path[i] = i;
        long start = System.currentTimeMillis();
        int minDist = minDist(distMap, path, 0, new HashMap<>());
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(count);
        Assert.assertThat(minDist, is(3215));
    }

    private int[][] makeDistMap(int size) {
        Random random = new Random(1223);
        int[][] distMap = new int[size][size];
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                distMap[i][j] = random.nextInt(2000);
                distMap[j][i] = distMap[i][j];
            }
        }
        return distMap;
    }

    long count = 0;

    private int minDist(int[][] distMap, int[] path, int depth, Map<Integer, Integer> cache) {
        ++count;
        if (depth == path.length) {
            return 0;
        }
        int key = 0;
        // caching reduces the number of calls to method by 10.000 times when size == 12
        if (cache != null && depth > 0) {
            for (int i = depth - 1; i < path.length; i++) {
                key |= 1 << path[i];
            }
            key |= path[depth - 1] << 24;
            Integer cached = cache.get(key);
            if (cached != null) return cached;
        }


        int minDist = Integer.MAX_VALUE;
        for (int i = depth; i < path.length; i++) {
            Utils.swap(path, depth, i);
            int dist = depth > 0 ? distMap[path[depth]][path[depth - 1]] : 0;
            dist += minDist(distMap, path, depth + 1, cache);
            minDist = Math.min(minDist, dist);
            Utils.swap(path, depth, i);
        }
        if (cache != null && depth > 0) cache.put(key, minDist);
        return minDist;
    }

}