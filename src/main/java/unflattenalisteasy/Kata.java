package unflattenalisteasy;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/57e2dd0bec7d247e5600013a/train/java">Unflatten a list (Easy)</a>
 * Changes:
 * - Overload Unflat#append method for better readability
 */
public class Kata {
    public static Object[] unflatten(int[] flatArray) {
        return Unflat.empty()
                .append(Ints.of(flatArray))
                .toArray();
    }
}

class Ints {
    private final List<Integer> values;

    private Ints(List<Integer> values) {
        this.values = values;
    }

    public Integer head() {
        return values.stream()
                .findFirst()
                .orElseThrow();
    }

    public Ints tail() {
        return drop(1);
    }

    public Ints take(long n) {
        List<Integer> taken = values.stream()
                .limit(n)
                .toList();

        return new Ints(taken);
    }

    public Ints drop(long n) {
        List<Integer> dropped = values.stream()
                .skip(n)
                .toList();

        return new Ints(dropped);
    }

    public int[] toArray() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public Stream<Integer> stream() {
        return values.stream();
    }

    public static Ints of(int[] array) {
        List<Integer> values = IntStream.of(array)
                .boxed()
                .toList();

        return new Ints(values);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}

class Unflat {
    private final List<Object> unflatten;

    private Unflat(List<Object> unflatten) {
        this.unflatten = unflatten;
    }

    public Unflat append(Integer value) {
        List<Object> appended = Stream.concat(
                unflatten.stream(),
                Stream.of(value)
        ).toList();

        return new Unflat(appended);
    }

    public Unflat append(int[] array) {
        List<Object> appended = Stream.concat(
                unflatten.stream(),
                Stream.of(array)
        ).toList();

        return new Unflat(appended);
    }

    public Unflat append(Ints ints) {
        if (ints.isEmpty()) {
            return this;
        }

        Integer head = ints.head();

        if (head < 3) {
            return this.append(head)
                    .append(ints.tail());
        }

        Ints next = ints.take(head.longValue());

        return this.append(next.toArray())
                .append(ints.drop(head));
    }

    public Object[] toArray() {
        return unflatten.toArray(new Object[0]);
    }

    public static Unflat empty() {
        return new Unflat(List.of());
    }
}
