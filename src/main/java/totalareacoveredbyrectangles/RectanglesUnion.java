package totalareacoveredbyrectangles;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://www.codewars.com/kata/55dcdd2c5a73bdddcb000044/train/java">Total area covered by rectangles</a>
 */
public class RectanglesUnion {
    public static int calculateSpace(int[][] rectangles) {
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
    private final int width;
    private final int height;

    private SimpleRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Rectangle of(int[] edges) {
        final int width = Math.abs(edges[0] - edges[2]);
        final int height = Math.abs(edges[1] - edges[3]);

        return new SimpleRectangle(width, height);
    }

    @Override
    public int space() {
        return width * height;
    }

    @Override
    public Rectangle union(Rectangle other) {
        return null;
    }

    @Override
    public Rectangle intercept(Rectangle other) {
        return null;
    }
}
