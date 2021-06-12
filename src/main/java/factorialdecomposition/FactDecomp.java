package factorialdecomposition;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

class FactDecomp {

    public static String decomp(int n) {
        PrimeFactors factors = PrimeFactors.empty();

        for (int factor = n; factor > 1; factor--) {
            factors.add(PrimeFactors.decompose(factor));
        }

        return factors.toString();
    }
}

class PrimeFactors {
    private final Map<Integer, PrimeFactor> primeFactors = new HashMap<>();

    private PrimeFactors() {
    }

    PrimeFactors add(PrimeFactor factor) {
        int base = factor.base();

        PrimeFactor primeFactor = primeFactors.getOrDefault(
                base,
                PrimeFactor.one(base)
        );
        primeFactors.put(base, primeFactor.merge(factor));

        return this;
    }

    void add(PrimeFactors other) {
        other.primeFactors.keySet()
                .stream()
                .sorted()
                .map(other.primeFactors::get)
                .forEach(this::add);
    }

    boolean isEmpty() {
        return primeFactors.isEmpty();
    }

    @Override
    public String toString() {
        StringJoiner multiplicationJoiner = new StringJoiner(" * ");

        primeFactors.keySet()
                .stream()
                .sorted()
                .map(primeFactors::get)
                .map(PrimeFactor::toString)
                .forEach(multiplicationJoiner::add);

        return multiplicationJoiner.toString();
    }

    static PrimeFactors decompose(int n) {
        PrimeFactors factors = PrimeFactors.empty();

        int currentN = n;
        for (int i = n / 2; i > 1; i--) {
            while (currentN % i == 0) {
                factors.add(decompose(i));
                currentN = currentN / i;
            }
        }

        if (factors.isEmpty()) {
            return PrimeFactors.of(n);
        }

        return factors;
    }

    static PrimeFactors empty() {
        return new PrimeFactors();
    }

    static PrimeFactors of(int n) {
        return PrimeFactors.empty()
                .add(PrimeFactor.of(n));
    }
}

class PrimeFactor {
    private final int base;
    private final int exponent;

    private PrimeFactor(int base, int exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    int base() {
        return base;
    }

    PrimeFactor merge(PrimeFactor other) {
        if (base != other.base) {
            throw new RuntimeException("other.base not same");
        }

        return new PrimeFactor(base, exponent + other.exponent);
    }

    @Override
    public String toString() {
        if (exponent == 1) {
            return base + "";
        }

        return base + "^" + exponent;
    }

    static PrimeFactor of(int base) {
        return new PrimeFactor(base, 1);
    }

    static PrimeFactor one(int base) {
        return new PrimeFactor(base, 0);
    }
}
