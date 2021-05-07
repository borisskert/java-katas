package romannumeralshelper;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://www.codewars.com/kata/51b66044bce5799a7f000003
 */
public class RomanNumerals {
    private static final TwoWayMap<String, Integer> digits;

    static {
        digits = new TwoWayMap<>();

        digits.put("I", 1);
        digits.put("II", 2);
        digits.put("III", 3);
        digits.put("IV", 4);
        digits.put("V", 5);
        digits.put("IX", 9);
        digits.put("X", 10);
        digits.put("XX", 20);
        digits.put("XL", 40);
        digits.put("L", 50);
        digits.put("XC", 90);
        digits.put("C", 100);
        digits.put("CD", 400);
        digits.put("D", 500);
        digits.put("CM", 900);
        digits.put("M", 1000);
    }

    public static String toRoman(int n) {
        if(digits.hasValue(n)) {
            return digits.getKey(n);
        }

        Integer base = digits.values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .dropWhile(v -> v > n)
                .findFirst()
                .orElseThrow();

        return digits.getKey(base) + toRoman(n - base);
    }

    public static int fromRoman(String romanNumeral) {
        if(digits.hasKey(romanNumeral)) {
            return digits.getValue(romanNumeral);
        }

        String base = findNextBase(romanNumeral);
        String remainder = romanNumeral.substring(base.length());

        return digits.getValue(base) + fromRoman(remainder);
    }


    private static String findNextBase(String romanNumeral) {
        int count = 1;
        String foundBase = null;
        String text = romanNumeral.substring(0, count);

        while(digits.hasKey(text)) {
            foundBase = text;
            count++;
            text = romanNumeral.substring(0, count);
        }

        return foundBase;
    }


    private static class TwoWayMap<T, S> {
        private final Map<T, S> keyToValue = new HashMap<>();
        private final Map<S, T> valueToKey = new HashMap<>();

        public TwoWayMap<T, S> put(T key, S value) {
            keyToValue.put(key, value);
            valueToKey.put(value, key);

            return this;
        }

        public boolean hasValue(S value) {
            return valueToKey.containsKey(value);
        }

        public T getKey(S value) {
            return valueToKey.get(value);
        }

        public Set<S> values() {
            return valueToKey.keySet();
        }

        public S getValue(T key) {
            return keyToValue.get(key);
        }

        public boolean hasKey(T key) {
            return keyToValue.containsKey(key);
        }
    }
}
