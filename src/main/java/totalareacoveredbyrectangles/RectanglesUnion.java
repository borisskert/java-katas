package totalareacoveredbyrectangles;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/55dcdd2c5a73bdddcb000044/train/java">Total area covered by rectangles</a>
 */
public class RectanglesUnion {
    public static int calculateSpace(int[][] rectangles) {
//        System.out.println(Arrays.stream(rectangles).map(a -> Arrays.stream(a).boxed().toList()).toList());

        return Merging.empty()
                .mergeWith(Rectangle.of(rectangles))
                .space();
    }
}

interface Area {
    int space();
}

class Cropping implements Area {

    private final Rectangle rectangle;
    private final Collection<Cropping> croppings;

    private Cropping(Rectangle rectangle, Collection<Cropping> croppings) {
        this.rectangle = rectangle;
        this.croppings = croppings;
    }

    @Override
    public int space() {
        Integer croppingSpace = croppings.stream()
                .parallel()
                .map(Area::space)
                .reduce(Integer::sum)
                .orElse(0);

        return rectangle.space() - croppingSpace;
    }

    public boolean contains(Rectangle other) {
        return other.isWithin(rectangle) && croppings.stream()
                .noneMatch(cropping -> cropping.contains(other));
    }

    public Optional<Cropping> crop(Rectangle crop) {
        Optional<Rectangle> maybeCropIntersection = crop.intersection(rectangle);

        if (maybeCropIntersection.isEmpty()) {
            return Optional.of(this);
        }

        Rectangle cropIntersection = maybeCropIntersection.get();

        if (cropIntersection.equals(rectangle)) {
            return Optional.empty();
        }

        if (croppings.stream().anyMatch(other -> other.contains(cropIntersection))) {
            return Optional.of(this);
        }

        Stream<Cropping> newCroppings = Stream.concat(
                croppings.stream()
                        .parallel()
                        .map(cropping -> cropping.crop(cropIntersection))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                ,
                Stream.of(Cropping.of(cropIntersection))
        );

        Cropping cropping = new Cropping(
                rectangle,
                newCroppings.toList()
        );

        return Optional.of(cropping);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cropping cropping = (Cropping) o;
        return rectangle.equals(cropping.rectangle) && croppings.equals(cropping.croppings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle, croppings);
    }

    @Override
    public String toString() {
        return "Cropping{" +
                "rectangle=" + rectangle +
                ", croppings=" + croppings +
                '}';
    }

    public static Cropping of(Rectangle rectangle) {
        return new Cropping(rectangle, List.of());
    }

    public static Cropping from(Rectangle rectangle, Collection<Cropping> croppings) {
        return new Cropping(rectangle, croppings);
    }
}

class Merging implements Area {
    private final Collection<Cropping> merged;

    private Merging(Collection<Cropping> merged) {
        this.merged = merged;
    }

    public Merging mergeWith(Rectangle other) {
        if (merged.stream().anyMatch(cropping -> cropping.contains(other))) {
            return this;
        }

        Stream<Cropping> cropped = merged.stream()
                .parallel()
                .map(cropping -> cropping.crop(other))
                .filter(Optional::isPresent)
                .map(Optional::get);

        Stream<Cropping> concat = Stream.concat(cropped, Stream.of(Cropping.of(other)));

        return new Merging(concat.toList());
    }

    public Merging mergeWith(Collection<Rectangle> others) {
        return others.stream()
                .parallel()
                .reduce(
                        this,
                        Merging::mergeWith,
                        Merging::merge
                );
    }

    @Override
    public int space() {
        return merged.stream()
                .parallel()
                .map(Area::space)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public Merging merge(Merging other) {
        return other.merged.stream()
                .reduce(
                        this,
                        Merging::mergeWith,
                        Merging::merge);
    }

    private Merging mergeWith(Cropping cropping) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merging merging = (Merging) o;
        return merged.equals(merging.merged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merged);
    }

    @Override
    public String toString() {
        return "Merging{" +
                "merged=" + merged +
                '}';
    }

    public static Merging of(Rectangle rectangle) {
        return new Merging(List.of(Cropping.of(rectangle)));
    }

    public static Merging empty() {
        return new Merging(List.of());
    }

    public static Merging from(Collection<Rectangle> rectangles) {
        Collection<Cropping> croppings = rectangles.stream()
                .map(Cropping::of)
                .toList();

        return new Merging(croppings);
    }
}

class Rectangle implements Area {
    private final Point a;
    private final Point b;

    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    private Rectangle(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.minX = Math.min(a.x(), b.x());
        this.minY = Math.min(a.y(), b.y());
        this.maxX = Math.max(a.x(), b.x());
        this.maxY = Math.max(a.y(), b.y());
    }

    @Override
    public int space() {
        return a.width(b) * a.height(b);
    }

    public boolean isWithin(Rectangle other) {
        return minX() >= other.minX() && maxX() <= other.maxX() &&
                minY() >= other.minY() && maxY() <= other.maxY();
    }

    public int minX() {
        return minX;
    }

    public int maxX() {
        return maxX;
    }

    public int minY() {
        return minY;
    }

    public int maxY() {
        return maxY;
    }

    public Optional<Rectangle> intersection(Rectangle other) {
        int minX = Math.max(minX(), other.minX());
        int minY = Math.max(minY(), other.minY());

        int maxX = Math.min(maxX(), other.maxX());
        int maxY = Math.min(maxY(), other.maxY());

        if (minX >= maxX || minY >= maxY) {
            return Optional.empty();
        }

        return Optional.of(Rectangle.from(new Point(minX, minY), new Point(maxX, maxY)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return a.equals(rectangle.a) && b.equals(rectangle.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    public static Rectangle from(Point a, Point b) {
        return new Rectangle(a, b);
    }

    public static Rectangle of(int[] edges) {
        final Point a = new Point(edges[0], edges[1]);
        final Point b = new Point(edges[2], edges[3]);

        return new Rectangle(a, b);
    }

    static Collection<Rectangle> of(int[][] edges) {
        return Arrays.stream(edges)
                .parallel()
                .map(Rectangle::of)
                .distinct()
                .toList();
    }
}

record Point(int x, int y) {
    int width(Point other) {
        return Math.abs(x - other.x);
    }

    int height(Point other) {
        return Math.abs(y - other.y);
    }
}
