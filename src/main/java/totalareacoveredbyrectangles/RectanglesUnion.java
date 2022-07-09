package totalareacoveredbyrectangles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/55dcdd2c5a73bdddcb000044/train/java">Total area covered by rectangles</a>
 */
public class RectanglesUnion {
    public static int calculateSpace(int[][] rectangles) {
        System.out.println(Arrays.stream(rectangles).map(a -> Arrays.stream(a).boxed().toList()).toList());

        return Area.of(rectangles)
                .stream()
                .map(Area::space)
                .reduce(Integer::sum)
                .orElse(0);
    }
}

interface Area {
    int space();

    static List<Area> of(int[][] edges) {
        return Arrays.stream(edges)
                .map(Rectangle::of)
                .toList();
    }
}

class Cropping implements Area {

    private final Rectangle rectangle;
    private final List<Cropping> croppings;

    private Cropping(Rectangle rectangle, List<Cropping> croppings) {
        this.rectangle = rectangle;
        this.croppings = croppings;
    }

    @Override
    public int space() {
        Integer croppingSpace = croppings.stream()
                .map(Area::space)
                .reduce(Integer::sum)
                .orElse(0);

        return rectangle.space() - croppingSpace;
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

        Stream<Cropping> newCroppings = Stream.concat(
                croppings.stream().map(cropping -> cropping.crop(cropIntersection)).filter(Optional::isPresent).map(Optional::get),
                Stream.of(Cropping.of(cropIntersection))
        );

        Cropping cropping = new Cropping(rectangle, newCroppings.toList());

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

    public static Cropping from(Rectangle rectangle, List<Cropping> croppings) {
        return new Cropping(rectangle, croppings);
    }
}

class Rectangle implements Area {
    private final Point a;
    private final Point b;

    private Rectangle(Point a, Point b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public int space() {
        return a.width(b) * a.height(b);
    }

    public Optional<Rectangle> intersection(Rectangle other) {
        int minX = Math.max(Math.min(a.x(), b.x()), Math.min(other.a.x(), other.b.x()));
        int minY = Math.max(Math.min(a.y(), b.y()), Math.min(other.a.y(), other.b.y()));

        int maxX = Math.min(Math.max(a.x(), b.x()), Math.max(other.a.x(), other.b.x()));
        int maxY = Math.min(Math.max(a.y(), b.y()), Math.max(other.a.y(), other.b.y()));

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

    public static Area of(int[] edges) {
        final Point a = new Point(edges[0], edges[1]);
        final Point b = new Point(edges[2], edges[3]);

        return new Rectangle(a, b);
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
