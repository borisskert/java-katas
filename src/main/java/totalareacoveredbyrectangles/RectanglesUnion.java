package totalareacoveredbyrectangles;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://www.codewars.com/kata/55dcdd2c5a73bdddcb000044/train/java">Total area covered by rectangles</a>
 */
public class RectanglesUnion {
    public static int calculateSpace(int[][] rectangles) {
        System.out.println(Arrays.stream(rectangles).map(a -> Arrays.stream(a).boxed().toList()).toList());

        return Rectangle.of(rectangles)
                .stream()
                .map(Rectangle::space)
                .reduce(Integer::sum)
                .orElse(0);
    }
}

interface Rectangle {
    int space();

    Rectangle union(Rectangle other);

    Rectangle intercept(Rectangle other);

    static List<Rectangle> of(int[][] edges) {
        return Arrays.stream(edges)
                .map(SimpleRectangle::of)
                .toList();
    }
}

class SimpleRectangle implements Rectangle {
    private final Point a;
    private final Point b;

    private SimpleRectangle(Point a, Point b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public int space() {
        return a.width(b) * a.height(b);
    }

    @Override
    public Rectangle union(Rectangle other) {
        return null;
    }

    @Override
    public Rectangle intercept(Rectangle other) {
        return null;
    }

    public static Rectangle of(int[] edges) {
        final Point a = new Point(edges[0], edges[1]);
        final Point b = new Point(edges[2], edges[3]);

        return new SimpleRectangle(a, b);
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
