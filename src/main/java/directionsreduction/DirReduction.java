package directionsreduction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/550f22f4d758534c1100025a/train/java
 */
public class DirReduction {

    public static String[] dirReduc(String[] arr) {
        return Directions.from(arr)
                .reduce()
                .toStrings();
    }
}

class Directions {
    private final List<Direction> directions;

    private Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public Directions reduce() {
        Directions reduced = reduceOnce();

        if (reduced.equals(this)) {
            return this;
        }

        return reduced.reduce();
    }

    public String[] toStrings() {
        return directions.stream()
                .map(Direction::name)
                .collect(Collectors.toUnmodifiableList())
                .toArray(new String[]{});
    }

    private Directions reduceOnce() {
        if (directions.size() <= 1) {
            return this;
        }

        Direction first = head();
        Directions tail = tail();

        Direction second = tail.head();

        if (first.isOppositeOf(second)) {
            return tail.tail()
                    .reduce();
        }

        return Directions.of(first)
                .append(tail.reduce());
    }

    private Direction head() {
        return directions.get(0);
    }

    private Directions tail() {
        List<Direction> tail = this.directions.subList(1, this.directions.size());
        return new Directions(tail);
    }

    private Directions append(Directions other) {
        List<Direction> concat = Stream.concat(
                this.directions.stream(),
                other.directions.stream()
        ).collect(Collectors.toUnmodifiableList());

        return new Directions(concat);
    }

    public static Directions from(String[] arr) {
        List<Direction> directions = Arrays.stream(arr)
                .map(Direction::valueOf)
                .collect(Collectors.toUnmodifiableList());

        return new Directions(directions);
    }

    public static Directions of(Direction direction) {
        return new Directions(List.of(direction));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Directions that = (Directions) o;

        return directions.equals(that.directions);
    }

    @Override
    public int hashCode() {
        return directions.hashCode();
    }
}

enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    private static final Map<Direction, Direction> opposites = Map.of(
            NORTH, SOUTH,
            SOUTH, NORTH,
            EAST, WEST,
            WEST, EAST
    );

    public boolean isOppositeOf(Direction other) {
        return opposites.get(this) == other;
    }
}
