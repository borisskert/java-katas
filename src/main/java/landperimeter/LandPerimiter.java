package landperimeter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/5839c48f0cf94640a20001d3/train/java">Land perimeter</a>
 */
public class LandPerimiter {

    public static String landPerimeter(String[] arr) {
        int perimeter = Land.parse(arr).perimeter();
        return String.format("Total land perimeter: %d", perimeter);
    }
}

record Indexed<T>(int index, T value) {
    public static <T> Indexed<T> of(int index, T value) {
        return new Indexed<>(index, value);
    }
}

class Land {
    private final Set<Point> points;

    private Land(Set<Point> points) {
        this.points = points;
    }

    public int perimeter() {
        return points.stream()
                .flatMap(Point::neighbors)
                .filter(neighbor -> !points.contains(neighbor))
                .mapToInt(neighbor -> 1)
                .sum();
    }

    public static Land parse(String[] arr) {
        Set<Point> landPoints = IntStream
                .range(0, arr.length)
                .mapToObj(index -> Indexed.of(index, arr[index]))
                .flatMap(Land::toRowOfPoints)
                .collect(Collectors.toSet());

        return new Land(landPoints);
    }

    private static Stream<Point> toRowOfPoints(Indexed<String> indexed) {
        final int y = indexed.index();
        String row = indexed.value();

        return IntStream.range(0, row.length())
                .mapToObj(x -> Indexed.of(x, row.charAt(x)))
                .filter(indexedChar -> indexedChar.value() == 'X')
                .map(indexedChar -> {
                    final int x = indexedChar.index();
                    return Point.of(x, y);
                });
    }
}

record Point(int x, int y) {
    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Stream<Point> neighbors() {
        return Stream.of(
                Point.of(x + 1, y),
                Point.of(x - 1, y),
                Point.of(x, y + 1),
                Point.of(x, y - 1)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
