package scramblies;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/55c04b4cc56a697bb0000048/train/java
 */
public class Scramblies {

    public static boolean scramble(String str1, String str2) {
        return Counter.empty()
                .incrementAll(str1)
                .decrementAll(str2)
                .values()
                .allMatch(integer -> integer >= 0);
    }
}

/**
 * This Counter is not immutable
 */
class Counter {

    private final Map<Character, Integer> counts;

    private Counter(Map<Character, Integer> counts) {
        this.counts = counts;
    }

    public Counter incrementAll(String str) {
        str.chars()
                .mapToObj(c -> (char) c)
                .forEach(this::increment);

        return this;
    }

    public Counter decrementAll(String str) {
        str.chars()
                .mapToObj(c -> (char) c)
                .forEach(this::decrement);

        return this;
    }

    public Stream<Integer> values() {
        return counts.values().stream();
    }

    private void increment(char c) {
        counts.merge(c, 1, Integer::sum);
    }

    private void decrement(char c) {
        counts.merge(c, -1, Integer::sum);
    }

    public static Counter empty() {
        return new Counter(new HashMap<>());
    }
}
