package helpyourgranny;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/5536a85b6ed4ee5a78000035/train/java
 */
public class Tour {

    public static int tour(String[] fArray, String[][] fTowns, Map<String, Double> distTable) {
        Friends friends = Friends.from(fArray);
        Towns towns = FriendTowns.from(fTowns)
                .filterBy(friends);

        Distances distances = TownDistances.from(distTable)
                .filterBy(towns);

        Double sum = distances.pairs()
                .stream()
                .map(pair -> Pythagoras.oppositeLeg(pair.fst(), pair.snd()))
                .reduce(Double::sum).orElse(0.0);

        double totalDistance = distances.head() + sum + distances.last();

        return (int) Math.floor(totalDistance);
    }
}

class Friends {
    private final String[] array;

    private Friends(String[] array) {
        this.array = array;
    }

    public static Friends from(String[] array) {
        return new Friends(array);
    }

    public Stream<String> stream() {
        return Arrays.stream(array);
    }
}

class FriendTowns {
    private final Map<String, String> townMap;

    private FriendTowns(Map<String, String> townMap) {
        this.townMap = townMap;
    }

    public Towns filterBy(Friends friends) {
        List<String> towns = friends.stream()
                .map(townMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());

        return Towns.from(towns);
    }

    public static FriendTowns from(String[][] array) {
        Map<String, String> collectedMap = Arrays.stream(array)
                .map(Pair::from)
                .collect(Collectors.toUnmodifiableMap(Pair::fst, Pair::snd));

        return new FriendTowns(collectedMap);
    }
}

class Towns {
    private final List<String> towns;

    private Towns(List<String> towns) {
        this.towns = towns;
    }

    public static Towns from(List<String> towns) {
        return new Towns(towns);
    }

    public Stream<String> stream() {
        return towns.stream();
    }
}

class Pair<T> {
    private final T fst;
    private final T snd;

    private Pair(T a, T b) {
        this.fst = a;
        this.snd = b;
    }

    public static <T> Pair<T> of(T a, T b) {
        return new Pair<>(a, b);
    }

    public T fst() {
        return fst;
    }

    public T snd() {
        return snd;
    }

    public static <T> Pair<T> from(T[] array) {
        return new Pair<>(array[0], array[1]);
    }
}

class TownDistances {
    private final Map<String, Double> distTable;

    private TownDistances(Map<String, Double> distTable) {
        this.distTable = distTable;
    }

    public Distances filterBy(Towns towns) {
        List<Double> distances = towns.stream()
                .map(distTable::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());

        return Distances.from(distances);
    }

    public static TownDistances from(Map<String, Double> distTable) {
        return new TownDistances(distTable);
    }
}

class Distances {
    private final List<Double> distances;

    private Distances(List<Double> distances) {
        this.distances = distances;
    }

    public static Distances from(List<Double> distances) {
        return new Distances(distances);
    }

    public Double head() {
        return distances.get(0);
    }

    public Double last() {
        return distances.get(distances.size() - 1);
    }

    public Pairs<Double> pairs() {
        List<Pair<Double>> pairs = createPairsFrom(this.distances, List.of());
        return Pairs.from(pairs);
    }

    private static List<Pair<Double>> createPairsFrom(List<Double> values, List<Pair<Double>> pairs) {
        if (values.size() < 2) {
            return pairs;
        }

        Pair<Double> pair = Pair.of(values.get(0), values.get(1));

        List<Pair<Double>> newPairs = Stream.concat(
                pairs.stream(),
                Stream.of(pair)
        ).collect(Collectors.toUnmodifiableList());

        return createPairsFrom(values.subList(1, values.size()), newPairs);
    }
}

class Pairs<T> {
    private final List<Pair<T>> pairs;

    private Pairs(List<Pair<T>> pairs) {
        this.pairs = pairs;
    }

    public static <T> Pairs<T> from(List<Pair<T>> pairs) {
        return new Pairs<>(pairs);
    }

    public Stream<Pair<T>> stream() {
        return pairs.stream();
    }
}

class Pythagoras {
    public static Double oppositeLeg(double a, double c) {
        return Math.sqrt(c * c - a * a);
    }
}
