package integersrecreationone;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/55aa075506463dac6600010d/train/java">Integers: Recreation One</a>
 */
public class SumSquaredDivisors {

    public static String listSquared(long m, long n) {
        Stream<Tuple<Long, Long>> foundTuples = LongStream.rangeClosed(m, n)
                .mapToObj(number -> Tuple.of(number, Longs.divisors(number)))
                .map(tuple -> tuple.mapB(Longs::square))
                .map(tuple -> tuple.mapB(LongStream::sum))
                .filter(tuple -> Longs.isSquare(tuple.b()));

        String formatted = foundTuples
                .map(Tuple::toString)
                .collect(Collectors.joining(", "));

        return "[" + formatted + "]";
    }
}

class Longs {
    public static LongStream divisors(long n) {
        return LongStream.rangeClosed(1, n)
                .takeWhile(i -> i * i <= n)
                .filter(i -> n % i == 0)
                .flatMap(
                        i -> i * i == n
                                ? LongStream.of(i)
                                : LongStream.of(i, n / i)
                );
    }

    public static LongStream square(LongStream stream) {
        return stream.map(x -> x * x);
    }

    public static boolean isSquare(long n) {
        return Math.sqrt(n) % 1 == 0;
    }
}

record Tuple<A, B>(A a, B b) {
    public <C> Tuple<A, C> mapB(Function<B, C> f) {
        return new Tuple<>(a, f.apply(b));
    }

    @Override
    public String toString() {
        return "[" + a +
                ", " + b +
                ']';
    }

    public static <A, B> Tuple<A, B> of(A a, B b) {
        return new Tuple<>(a, b);
    }
}
