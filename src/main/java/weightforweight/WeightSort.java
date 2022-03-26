package weightforweight;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/55c6126177c9441a570000cc/train/java
 * <p>
 * Changelog:
 * - use {@link Character#getNumericValue} to map {@link Character} to {@link Integer}
 * - use {@link java.util.stream.IntStream#sum} to calculate weight's sum
 * - use {@link Comparator#comparing(Function)} to compare weight's properties
 */
public class WeightSort {
    private static final String WORD_DELIMITER = " ";

    public static String orderWeight(String strng) {
        return Arrays.stream(strng.split(WORD_DELIMITER))
                .map(Weight::from)
                .sorted()
                .map(Weight::value)
                .collect(Collectors.joining(WORD_DELIMITER));
    }
}

class Weight implements Comparable<Weight> {
    private static final Comparator<Weight> COMPARATOR = Comparator
            .comparing(Weight::sum)
            .thenComparing(Weight::value);

    private final String value;
    private final int sum;

    private Weight(String value, int sum) {
        this.value = value;
        this.sum = sum;
    }

    public String value() {
        return value;
    }

    private int sum() {
        return sum;
    }

    @Override
    public int compareTo(Weight other) {
        return COMPARATOR.compare(this, other);
    }

    public static Weight from(String weight) {
        int sum = sumOf(weight);
        return new Weight(weight, sum);
    }

    private static int sumOf(String weight) {
        return weight.chars()
                .map(Character::getNumericValue)
                .sum();
    }
}
