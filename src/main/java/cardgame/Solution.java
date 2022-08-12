package cardgame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <a href="https://www.codewars.com/kata/61fef3a2d8fa98021d38c4e5/train/java">Card Game</a>
 */
class Solution {
    private static final Cache<Long, Long> cache = new Cache<>(Solution::compute)
            .put(0L, 0L)
            .put(1L, 1L)
            .put(2L, 1L)
            .put(3L, 2L)
            .put(4L, 3L);

    public static long cardGame(long n) {
        return cache.get(n);
    }

    private static long compute(long n) {
        long remainder = n % 4;
        long cards;

        if (remainder == 0) {
            cards = 1 + cardGame(n - 2);
        } else if (remainder == 2) {
            long halfN = n / 2;
            cards = halfN + cardGame(halfN - 1);
        } else {
            cards = n - cardGame(n - 1);
        }

        return cards;
    }
}

class Cache<K, V> {
    private final Map<K, V> cache = new HashMap<>();

    private final Function<? super K, ? extends V> computeFunction;

    Cache(Function<? super K, ? extends V> computeFunction) {
        this.computeFunction = computeFunction;
    }

    V get(K key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        V value = computeFunction.apply(key);
        cache.put(key, value);

        return value;
    }

    Cache<K, V> put(K key, V value) {
        cache.put(key, value);
        return this;
    }
}
