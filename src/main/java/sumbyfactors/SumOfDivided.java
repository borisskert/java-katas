package sumbyfactors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SumOfDivided {
    public static String sumOfDivided(int[] numbers) {
        PrimeSums sums = Arrays.stream(numbers)
                .mapToObj(Number::of)
                .reduce(
                        PrimeSums.empty(),
                        PrimeSums::with,
                        PrimeSums::merge
                );

        return sums.toString();
    }
}

class PrimeSums {
    private final Map<Integer, Integer> sumsByPrime;

    private PrimeSums(Map<Integer, Integer> sumsByPrime) {
        this.sumsByPrime = sumsByPrime;
    }

    PrimeSums with(Number number) {
        Map<Integer, Integer> newSums = new HashMap<>(sumsByPrime);
        Set<Integer> primes = number.primes();

        for (Integer prime : primes) {
            Integer sum = newSums.getOrDefault(prime, 0);
            newSums.put(prime, sum + number.value());
        }

        return new PrimeSums(Map.copyOf(newSums));
    }

    PrimeSums merge(PrimeSums other) {
        Map<Integer, Integer> newSums = new HashMap<>(sumsByPrime);
        newSums.putAll(other.sumsByPrime);

        return new PrimeSums(Map.copyOf(newSums));
    }

    @Override
    public String toString() {
        return sumsByPrime.keySet()
                .stream()
                .sorted()
                .map(prime -> {
                    Integer sum = sumsByPrime.get(prime);
                    return "(" + prime + " " + sum + ")";
                }).collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }

    static PrimeSums empty() {
        return new PrimeSums(Map.of());
    }
}

class Number {
    private final int number;

    private Number(int number) {
        this.number = number;
    }

    Set<Integer> primes() {
        return primesOf(number);
    }

    int value() {
        return number;
    }

    private static Set<Integer> primesOf(int number) {
        Set<Integer> primes = new HashSet<>();
        int n = Math.abs(number);

        for (int i = n / 2; i > 1; i--) {
            while (n % i == 0) {
                n /= i;
                primes.addAll(primesOf(i));
            }
        }

        if (primes.isEmpty()) {
            return Set.of(Math.abs(number));
        }

        return Set.copyOf(primes);
    }

    static Number of(int number) {
        return new Number(number);
    }
}
