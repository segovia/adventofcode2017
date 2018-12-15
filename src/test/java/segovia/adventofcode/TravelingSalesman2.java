package segovia.adventofcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static segovia.adventofcode.Utils.swap;
import static org.hamcrest.core.Is.is;

public class TravelingSalesman2 {

    public static final int PUSH = 24;


    /**
     * Playing around with how fast we can calculate traveling salesman dist. Cur record 21 in 40s
     */
    @Test
    public void test() throws IOException {
        int size = 15;
        int[][] distMap = makeDistMap(size);
        Map<Integer, Integer> cache = new HashMap<>();

        long start = System.currentTimeMillis();
        int[] path = new int[size / 2 + 1];
        int minDist = minDistPreparePath(distMap, path, 0, cache, size);
        System.out.println(System.currentTimeMillis() - start);
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

    private int minDistPreparePath(int[][] distMap, int[] path, int depth, Map<Integer, Integer> cache, int size) {
        if (depth == path.length) {
            minDist(distMap, path, 0, cache);

            int key = 0;
            for (int i : path) key |= 1 << i;
            int complement = ~key & ((1 << size) - 1);

            int best = Integer.MAX_VALUE;
            for (int i : path) {
                int b = i << PUSH | complement | (1 << i);
                Integer complCost = cache.get(b);
                if (complCost == null) return Integer.MAX_VALUE;

                int a = i << PUSH | key;
                Integer firstCost = cache.get(a);
                best = Math.min(best, complCost + firstCost);
            }
            return best;
        }
        int start = depth > 0 ? path[depth - 1] + 1 : 0;
        int end = size - (path.length - depth - 1);
        int best = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            path[depth] = i;
            best = Math.min(best, minDistPreparePath(distMap, path, depth + 1, cache, size));
        }
        return best;
    }


    private int minDist(int[][] distMap, int[] path, int depth, Map<Integer, Integer> cache) {
        if (depth == path.length) {
            return 0;
        }
        int key = 0;
        // caching reduces the number of calls to method by 10.000 times when size == 12
        if (cache != null && depth > 0) {
            for (int i = depth - 1; i < path.length; i++) {
                key |= 1 << path[i];
            }
            key |= path[depth - 1] << PUSH;
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