package primenumberdecompositions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.LongStream;

/**
 * https://www.codewars.com/kata/53c93982689f84e321000d62/train/java
 */
class PrimeNumberDecomposer {
    /**
     * Return value: List of all prime factors of a given number n
     */
    public static Long[] getAllPrimeFactors(long n) {
        return PrimeFactors.decompose(n).toPrimeFactorsArray();
    }

    /**
     * Return value: List containing two lists, first containg all prime factors without duplicates,
     * second containing the count, how often each prime factor occured.
     * Return code always contains two lists.
     * <p>
     * e.g.: getUniquePrimeFactorsWithCount(100) = {{2,5},{2,2}) // prime 2 occurs 2 times, prime 2 occurs 5 times,
     */
    public static Long[][] getUniquePrimeFactorsWithCount(long n) {
        return PrimeFactors.decompose(n).toUniquePrimeFactorsWithCountArray();
    }

    /**
     * Return value: List containing product of same prime factors,
     * e.g.: 45 = 3*3*5 ==>  {3^2,5^1} == {9,5}
     * e.g.: getUniquePrimeFactorsWithCount(100) = {{2,5},{2,2}) // prime 2 occurs 2 times, prime 2 occurs 5 times,
     */
    public static Long[] getPrimeFactorPotencies(long n) {
        return PrimeFactors.decompose(n).toPrimeFactorPotencies();
    }
}

/**
 * Immutable class which represents an immutable collection of prime factors
 */
class PrimeFactors {
    private final Map<Long, PrimeFactor> primeFactors;

    private PrimeFactors(Map<Long, PrimeFactor> primeFactors) {
        this.primeFactors = primeFactors;
    }

    PrimeFactors merge(PrimeFactors other) {
        Map<Long, PrimeFactor> primeFactors = clone(this.primeFactors);

        for (Map.Entry<Long, PrimeFactor> otherEntry : other.primeFactors.entrySet()) {
            Long prime = otherEntry.getKey();
            PrimeFactor primeFactor = otherEntry.getValue();
            primeFactor = primeFactors.getOrDefault(prime, PrimeFactor.one(prime))
                    .merge(primeFactor);

            primeFactors.put(prime, primeFactor);
        }

        return new PrimeFactors(Map.copyOf(primeFactors));
    }

    Long[] toPrimeFactorsArray() {
        List<Long> collectedPrimeFactors = new ArrayList<>();

        primeFactors.keySet().stream().sorted().forEach(prime -> {
            PrimeFactor primeFactor = primeFactors.get(prime);
            long exponent = primeFactor.exponent();

            LongStream.rangeClosed(1, exponent).forEach(p -> collectedPrimeFactors.add(prime));
        });

        return collectedPrimeFactors
                .toArray(new Long[0]);
    }

    Long[][] toUniquePrimeFactorsWithCountArray() {
        List<Long> collectedPrimes = new ArrayList<>();
        List<Long> collectedExponents = new ArrayList<>();

        primeFactors.keySet().stream().sorted().forEach(prime -> {
            PrimeFactor primeFactor = primeFactors.get(prime);
            collectedPrimes.add(primeFactor.prime());
            collectedExponents.add(primeFactor.exponent());
        });

        return new Long[][]{
                collectedPrimes.toArray(new Long[0]),
                collectedExponents.toArray(new Long[0])
        };
    }

    Long[] toPrimeFactorPotencies() {
        List<Long> collectedProducts = new ArrayList<>();

        primeFactors.keySet().stream().sorted().forEach(prime -> {
            PrimeFactor primeFactor = primeFactors.get(prime);
            collectedProducts.add(primeFactor.product());
        });

        return collectedProducts
                .toArray(new Long[0]);
    }

    boolean isEmpty() {
        return primeFactors.isEmpty();
    }

    @Override
    public String toString() {
        StringJoiner factorialJoiner = new StringJoiner(" * ");

        primeFactors.keySet().stream().sorted().forEach(prime -> {
            PrimeFactor primeFactor = primeFactors.get(prime);
            factorialJoiner.add(primeFactor.toString());
        });

        return factorialJoiner.toString();
    }

    static PrimeFactors empty() {
        return new PrimeFactors(Map.of());
    }

    static PrimeFactors of(long n) {
        return new PrimeFactors(Map.of(n, PrimeFactor.of(n)));
    }

    static PrimeFactors decompose(long n) {
        if (n == 0L) {
            return PrimeFactors.empty();
        }

        PrimeFactors factors = PrimeFactors.empty();

        long currentN = n;
        for (long i = n / 2; i > 1; i--) {
            while (currentN % i == 0) {
                factors = factors.merge(decompose(i));
                currentN = currentN / i;
            }
        }


        if (factors.isEmpty()) {
            return PrimeFactors.of(n);
        }

        return factors;
    }

    private static Map<Long, PrimeFactor> clone(Map<Long, PrimeFactor> other) {
        Map<Long, PrimeFactor> clone = new HashMap<>();

        for (Map.Entry<Long, PrimeFactor> entry : other.entrySet()) {
            clone.put(entry.getKey(), entry.getValue());
        }

        return clone;
    }
}

/**
 * Immutable class which represents a prime factor
 */
class PrimeFactor {
    private final long prime;
    private final long exponent;

    private PrimeFactor(long prime, long exponent) {
        this.prime = prime;
        this.exponent = exponent;
    }

    long prime() {
        return prime;
    }

    long exponent() {
        return exponent;
    }

    PrimeFactor merge(PrimeFactor other) {
        if (other.prime != this.prime) {
            throw new RuntimeException("other.prime not same");
        }

        return new PrimeFactor(prime, exponent + other.exponent);
    }

    long product() {
        long product = 1L;

        for (int counter = 0; counter < exponent; counter++) {
            product *= prime;
        }

        return product;
    }

    @Override
    public String toString() {
        return prime +
                "^" + exponent;
    }

    static PrimeFactor of(long prime) {
        return new PrimeFactor(prime, 1);
    }

    static PrimeFactor one(long prime) {
        return new PrimeFactor(prime, 0);
    }
}
