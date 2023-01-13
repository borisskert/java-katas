package poppingballoons;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/633b8be2b5203f003011d79e/train/java">Popping Balloons</a>
 */
public class Balloon {
    public static List<Integer> poppingBalloons(int pops, int[] balloons) {
        GroupedBalloons grouped = PoppingBalloons.from(balloons)
                .grouped();

        List<PoppingBalloon> poppingBalloons = recursively(pops, grouped);

        return poppingBalloons.stream()
                .map(PoppingBalloon::size)
                .toList();
    }

    private static List<PoppingBalloon> recursively(int pops, GroupedBalloons balloons) {
        if (pops <= 0) {
            return List.of();
        }

        PoppingBalloon balloon = balloons.head();

        List<PoppingBalloon> next = recursively(
                pops - 1,
                balloons.drop()
        );

        return Stream.concat(
                Stream.of(balloon),
                next.stream()
        ).toList();
    }
}

class PoppingBalloon implements Comparable<PoppingBalloon> {
    private final int size;
    private final int height;

    private PoppingBalloon(int size, int height) {
        this.size = size;
        this.height = height;
    }

    public Integer size() {
        return size;
    }

    @Override
    public int compareTo(PoppingBalloon other) {
        return Integer.compare(height, other.height);
    }

    public static PoppingBalloon of(Integer size, int height) {
        return new PoppingBalloon(size, height);
    }
}

class PoppingBalloons {
    private final List<PoppingBalloon> poppingBalloons;

    PoppingBalloons(List<PoppingBalloon> poppingBalloons) {
        this.poppingBalloons = poppingBalloons;
    }

    public GroupedBalloons grouped() {
        List<GroupedBalloon> balloons = new Partition<>(poppingBalloons)
                .by(PoppingBalloon::size)
                .stream()
                .map(GroupedBalloon::of)
                .toList();

        return GroupedBalloons.of(balloons);
    }

    public static PoppingBalloons from(int[] rawValues) {
        List<Integer> rawBalloons = Arrays.stream(rawValues).boxed().toList();

        List<PoppingBalloon> poppingBalloons = Zip.of(rawBalloons)
                .withIndex()
                .stream()
                .map(pair -> PoppingBalloon.of(pair.second(), pair.first()))
                .toList();

        return new PoppingBalloons(poppingBalloons);
    }
}

class GroupedBalloon implements Comparable<GroupedBalloon> {
    private final List<PoppingBalloon> balloons;

    private GroupedBalloon(List<PoppingBalloon> balloons) {
        this.balloons = balloons;
    }

    public int amount() {
        return balloons.size();
    }

    public PoppingBalloon head() {
        return balloons.get(0);
    }

    public GroupedBalloon tail() {
        List<PoppingBalloon> tail = balloons.stream()
                .skip(1)
                .toList();

        return new GroupedBalloon(tail);
    }

    public GroupedBalloons concat(GroupedBalloons balloons) {
        List<GroupedBalloon> concatenated = Stream.concat(
                Stream.of(this),
                balloons.stream()
        ).toList();

        return GroupedBalloons.of(concatenated);
    }

    public boolean isEmpty() {
        return balloons.isEmpty();
    }

    @Override
    public int compareTo(GroupedBalloon other) {
        if (this.amount() == other.amount()) {
            return this.head().compareTo(other.head());
        }

        return Integer.compare(this.amount(), other.amount());
    }

    public static GroupedBalloon of(List<PoppingBalloon> balloons) {
        List<PoppingBalloon> sorted = balloons.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        return new GroupedBalloon(sorted);
    }
}

class GroupedBalloons {
    private final List<GroupedBalloon> balloons;

    private GroupedBalloons(List<GroupedBalloon> balloons) {
        this.balloons = balloons;
    }

    public PoppingBalloon head() {
        return balloons.get(0).head();
    }

    public GroupedBalloons tail() {
        List<GroupedBalloon> tail = balloons.stream()
                .skip(1)
                .toList();

        return new GroupedBalloons(tail);
    }

    public GroupedBalloons drop() {
        GroupedBalloon tail = balloons.get(0).tail();

        if (tail.isEmpty()) {
            return this.tail();
        }

        return tail.concat(this.tail());
    }

    public Stream<GroupedBalloon> stream() {
        return balloons.stream();
    }

    public static GroupedBalloons of(List<GroupedBalloon> balloons) {
        List<GroupedBalloon> sorted = balloons.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        return new GroupedBalloons(sorted);
    }
}

class Zip<T> {
    private final List<T> items;

    private Zip(List<T> items) {
        this.items = items;
    }

    List<Pair<Integer, T>> withIndex() {
        return IntStream
                .range(0, items.size())
                .boxed()
                .map(index -> new Pair<>(index, items.get(index)))
                .toList();
    }

    public static <T> Zip<T> of(List<T> items) {
        return new Zip<>(items);
    }
}

record Pair<T, S>(T first, S second) {
    public String format() {
        return "(" + first + " : " + second + ")";
    }
}

class Partition<T> {
    private final List<T> items;

    Partition(List<T> items) {
        this.items = items;
    }

    public <K> Collection<List<T>> by(Function<T, K> toKey) {
        MultiMap<K, T> partitions = new MultiMap<>();

        items.forEach(item -> partitions.put(toKey.apply(item), item));

        return partitions.values();
    }

    private static class MultiMap<K, V> {
        private final Map<K, List<V>> entries = new HashMap<>();

        void put(K key, V value) {
            List<V> values = entries.getOrDefault(key, new ArrayList<>());
            values.add(value);

            entries.put(key, values);
        }

        Collection<List<V>> values() {
            return entries.values();
        }
    }
}
