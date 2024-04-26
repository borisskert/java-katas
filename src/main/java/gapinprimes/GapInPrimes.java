package gapinprimes;

import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.google.common.collect.Streams.zip;

/**
 * <a href="https://www.codewars.com/kata/561e9c843a2ef5a40c0000a4/train/java">Gap in Primes</a>
 */
class GapInPrimes {

    public static long[] gap(int g, long m, long n) {
        Supplier<Stream<Long>> primeNumbers = () -> LongStream.range(m, n)
                .filter(GapInPrimes::isPrime)
                .boxed();

        Stream<Pair<Long, Long>> primePairs = Streams.zipWithNext(primeNumbers);

        return primePairs
                .filter(pair -> pair.second() - pair.first() == g)
                .findFirst()
                .map(pair -> new long[]{pair.first(), pair.second()})
                .orElse(null);
    }

    private static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }

        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
}

record Pair<A, B>(A first, B second) {
    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }
}

class Streams {
    public static <A> Stream<Pair<A, A>> zipWithNext(Supplier<Stream<A>> stream) {
        return zipWith(stream.get(), stream.get().skip(1));
    }

    public static <A, B> Stream<Pair<A, B>> zipWith(Stream<? extends A> a, Stream<? extends B> b) {
        return zip(a, b, Pair::of);
    }
}
