package findthesmallest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/573992c724fc289553000e95/train/java">Find the smallest</a>
 */
public class ToSmallest {

    public static long[] smallest(final long n) {

        return Result.possibleResults(n)
                .min(Comparator.naturalOrder())
                .map(Result::toArray)
                .orElseThrow();
    }
}

record Result(long n, IntPair pair) implements Comparable<Result> {

    @Override
    public int compareTo(Result o) {
        return Long.compare(n, o.n);
    }

    public long[] toArray() {
        return new long[]{n, pair.i(), pair.j()};
    }

    public static Stream<Result> possibleResults(long n) {
        int length = Integers.lengthOf(n);

        return IntPair.cartesianProduct(length, length)
                .filter(pair -> pair.i() != pair.j())
                .map(pair -> pair.toResult(n));
    }
}

class Integers {

    public static int lengthOf(long n) {
        return String.valueOf(n).length();
    }

    public static long moveDigit(long n, int source, int destination) {
        int[] digits = digitsOf(n);
        int temp = digits[source];

        if (source < destination) {
            System.arraycopy(
                    digits,
                    source + 1,
                    digits,
                    source,
                    destination - source
            );
        } else {
            System.arraycopy(
                    digits,
                    destination,
                    digits,
                    destination + 1,
                    source - destination
            );
        }

        digits[destination] = temp;

        return digitsToLong(digits);
    }

    public static int[] digitsOf(long n) {
        String longAsString = String.valueOf(n);

        return longAsString.chars()
                .map(character -> character - '0')
                .toArray();
    }

    public static long digitsToLong(int[] digits) {
        return Arrays.stream(digits)
                .asLongStream()
                .reduce((sum, digit) -> sum * 10 + digit)
                .orElseThrow();
    }
}

record IntPair(int i, int j) {

    public static Stream<IntPair> cartesianProduct(int a, int b) {
        return IntStream.range(0, a)
                .mapToObj(i -> IntStream.range(0, b)
                        .mapToObj(j -> new IntPair(i, j))
                )
                .flatMap(Function.identity());
    }

    public Result toResult(long n) {
        long movedDigit = Integers.moveDigit(n, this.i(), this.j());
        return new Result(movedDigit, this);
    }
}
