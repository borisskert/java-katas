package asimplemusicencoder;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/58db9545facc51e3db00000a/train/java">A Simple Music Encoder</a>
 * Changelog:
 * - Simplify code, reduce classes
 */
public class Encoder {
    public String compress(int[] raw) {
        return InputSignal.from(raw)
                .compress()
                .asString();
    }
}

class InputSignal {
    private final CoolList<Integer> values;

    private InputSignal(CoolList<Integer> values) {
        this.values = values;
    }

    CompressedStream compress() {
        return CompressedStream.of(values);
    }

    public static InputSignal from(int[] raw) {
        List<Integer> values = Arrays.stream(raw)
                .boxed()
                .toList();

        return new InputSignal(CoolList.from(values));
    }
}

interface Compressible {
    String asString();

    Compressible extend(Integer value);

    boolean accepting(Integer value);

    int size();
}

class Identical implements Compressible {

    private final Integer value;
    private final int count;

    private Identical(Integer value, int count) {
        this.value = value;
        this.count = count;
    }


    @Override
    public String asString() {
        return MessageFormat.format(
                "{0,number,#}*{1,number,#}",
                value,
                count
        );
    }

    @Override
    public Compressible extend(Integer value) {
        if (!this.accepting(value)) {
            throw new IllegalArgumentException();
        }

        return new Identical(value, count + 1);
    }

    @Override
    public boolean accepting(Integer value) {
        return this.value.equals(value);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        return "Identical{" +
                "value=" + value +
                ", count=" + count +
                '}';
    }

    public static Optional<Compressible> tryToCompress3(CoolList<Integer> values) {
        return tryToCompressN(values, 3);
    }

    public static Optional<Compressible> tryToCompress2(CoolList<Integer> values) {
        return tryToCompressN(values, 2);
    }

    public static Optional<Compressible> tryToCompressN(CoolList<Integer> values, int n) {
        CoolList<Integer> taken = values.take(n);

        if (areIdentical(taken)) {
            return Optional.of(new Identical(taken.head(), taken.size()));
        }

        return Optional.empty();
    }

    private static boolean areIdentical(CoolList<Integer> values) {
        if (values.size() < 2) {
            return false;
        }

        CoolList<Integer> uniqueVales = values.nubBy(Integer::compare);

        return uniqueVales.size() == 1;
    }
}

class SameInterval implements Compressible {

    private final Integer start;
    private final Integer end;
    private final Integer step;


    private SameInterval(Integer start, Integer end, Integer step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public String asString() {
        if (Math.abs(step) > 1) {
            return MessageFormat.format(
                    "{0,number,#}-{1,number,#}/{2,number,#}",
                    start,
                    end,
                    Math.abs(step)
            );
        }

        return MessageFormat.format(
                "{0,number,#}-{1,number,#}",
                start,
                end
        );
    }

    @Override
    public Compressible extend(Integer value) {
        if (!this.accepting(value)) {
            throw new IllegalArgumentException();
        }

        return new SameInterval(start, value, step);
    }

    @Override
    public boolean accepting(Integer value) {
        return value.equals(end + step);
    }

    @Override
    public int size() {
        return Math.abs((end - start) / step) + 1;
    }

    @Override
    public String toString() {
        return "SameInterval{" +
                "start=" + start +
                ", end=" + end +
                ", step=" + step +
                '}';
    }

    public static Optional<Compressible> tryToCompress3(CoolList<Integer> values) {
        CoolList<Integer> taken = values.take(3);

        if (areSameInterval(taken)) {
            Integer start = taken.head();
            Integer end = taken.last();
            int step = end - taken.init().last();

            return Optional.of(new SameInterval(start, end, step));
        }

        return Optional.empty();
    }

    private static boolean areSameInterval(CoolList<Integer> values) {
        if (values.size() < 3) {
            return false;
        }

        CoolList<Pair<Integer, Integer>> pairs = values.zipWithNeighbor();

        CoolList<Integer> diffs = pairs.map(pair -> pair.second() - pair.first());

        return diffs.nubBy(Integer::compare).size() == 1;
    }
}

class Simple implements Compressible {

    private final Integer value;

    private Simple(Integer value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return Integer.toString(value);
    }

    @Override
    public Compressible extend(Integer value) {
        throw new IllegalStateException();
    }

    @Override
    public boolean accepting(Integer value) {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return "Simple{" +
                value +
                '}';
    }

    public static Simple of(Integer value) {
        return new Simple(value);
    }
}

/**
 * Represents a collection of {@link Compressible} instances
 */
class CompressedStream {
    private final CoolList<Compressible> compressibles;

    private CompressedStream(CoolList<Compressible> compressibles) {
        this.compressibles = compressibles;
    }

    public static CompressedStream of(CoolList<Integer> values) {
        CoolList<Compressible> compressed = compress(values, CoolList.empty());
        return new CompressedStream(compressed);
    }

    private static CoolList<Compressible> compress(CoolList<Integer> values, CoolList<Compressible> collected) {
        if (values.isEmpty()) {
            return collected;
        }

        if (collected.isEmpty()) {
            return collectNewCompressible(values, collected);
        }

        Integer value = values.head();
        Compressible last = collected.last();

        if (last.accepting(value)) {
            Compressible extended = last.extend(value);
            return compress(values.tail(), collected.init().append(extended));
        }

        return collectNewCompressible(values, collected);
    }

    private static CoolList<Compressible> collectNewCompressible(CoolList<Integer> values, CoolList<Compressible> collected) {
        Compressible compressible = Identical.tryToCompress3(values)
                .or(() -> SameInterval.tryToCompress3(values))
                .or(() -> Identical.tryToCompress2(values))
                .orElseGet(() -> Simple.of(values.head()));

        return compress(values.drop(compressible.size()), collected.append(compressible));
    }

    public String asString() {
        return compressibles.map(Compressible::asString)
                .reduce(
                        new StringJoiner(","),
                        StringJoiner::add,
                        StringJoiner::merge
                ).toString();
    }
}

/**
 * Represents an immutable collection of items with some fancy methods I know from some cool programming languages like Haskell or Kotlin.
 *
 * @param <T> the item type
 */
class CoolList<T> {
    private final List<T> items;

    private CoolList(List<T> items) {
        this.items = items;
    }

    CoolList<T> take(long n) {
        List<T> taken = items.stream()
                .limit(n)
                .toList();

        return new CoolList<>(taken);
    }

    CoolList<T> drop(long n) {
        List<T> dropped = items.stream()
                .skip(n)
                .toList();

        return new CoolList<>(dropped);
    }

    CoolList<Pair<T, T>> zipWithNeighbor() {
        if (items.size() < 2) {
            return empty();
        }

        Pair<T, T> pair = new Pair<>(items.get(0), items.get(1));
        CoolList<T> remaining = drop(1);
        CoolList<Pair<T, T>> furtherPairs = remaining.zipWithNeighbor();

        return CoolList.of(pair).append(furtherPairs);
    }

    CoolList<Pair<Integer, T>> zipWithIndex() {
        List<Pair<Integer, T>> withIndices = IntStream
                .range(0, items.size())
                .boxed()
                .map(index -> new Pair<>(index, items.get(index)))
                .toList();

        return new CoolList<>(withIndices);
    }

    CoolList<T> append(CoolList<T> other) {
        List<T> appended = Stream.concat(
                items.stream(),
                other.items.stream()
        ).toList();

        return new CoolList<>(appended);
    }

    int size() {
        return items.size();
    }

    CoolList<T> sorted(Comparator<? super T> comparator) {
        List<T> sorted = items.stream()
                .sorted(comparator)
                .toList();

        return new CoolList<>(sorted);
    }

    T head() {
        return items.get(0);
    }

    T last() {
        return items.get(items.size() - 1);
    }

    public CoolList<T> tail() {
        return drop(1);
    }

    public CoolList<T> init() {
        List<T> init = items.stream()
                .limit(items.size() - 1)
                .toList();

        return new CoolList<>(init);
    }

    CoolList<T> nubBy(Comparator<T> comparator) {
        List<Pair<Integer, T>> sortedByValues = this
                .zipWithIndex()
                .sorted((o1, o2) -> {
                    T one = o1.second();
                    T two = o2.second();

                    return comparator.compare(one, two);
                })
                .items;

        CoolList<Pair<Integer, T>> uniqueValues = sortedByValues
                .stream()
                .reduce(
                        CoolList.empty(),
                        (collected, pair) -> {
                            if (collected.isEmpty()) {
                                return CoolList.of(pair);
                            }

                            if (collected.last().second().equals(pair.second())) {
                                return collected;
                            }

                            return collected.append(pair);
                        }, CoolList::append
                );

        CoolList<Pair<Integer, T>> sortedByIndex = uniqueValues.sorted(Comparator.comparingInt(Pair::first));

        return sortedByIndex.map(Pair::second);
    }

    CoolList<T> append(T item) {
        List<T> appended = Stream.concat(
                items.stream(),
                Stream.of(item)
        ).toList();

        return new CoolList<>(appended);
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    <R> CoolList<R> map(Function<? super T, R> mapper) {
        List<R> mapped = items.stream()
                .map(mapper)
                .toList();

        return new CoolList<>(mapped);
    }

    public <U> U reduce(
            U identity,
            BiFunction<U, ? super T, U> accumulator,
            BinaryOperator<U> combiner
    ) {
        return items.stream().reduce(identity, accumulator, combiner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoolList<?> coolList = (CoolList<?>) o;

        return items.equals(coolList.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        return "CoolList{" +
                items +
                '}';
    }

    static <T> CoolList<T> from(List<T> items) {
        return new CoolList<>(items.stream().toList());
    }

    static <T> CoolList<T> of(T item) {
        return new CoolList<>(List.of(item));
    }

    static <T> CoolList<T> empty() {
        return new CoolList<>(List.of());
    }
}

record Pair<T, S>(T first, S second) {
}
