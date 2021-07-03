package simplefun27rectanglerotation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/5886e082a836a691340000c3/train/java
 */
class Solution {

    static int rectangleRotation(final int a, final int b) {
        Rectangle rectangle = Rectangle.createRotated(a, b);

        int counter = 0;
        for (long x = (long) rectangle.left().x(); x <= rectangle.right().x(); x++) {
            for (long y = (long) rectangle.bottom().y(); y <= rectangle.top().y(); y++) {
                Point point = Point.of(x, y);

                if(rectangle.isWithin(point)) {
                    counter++;
                }
            }
        }

        return counter;
    }
}

class Rectangle {
    private final Line topRightBorder;
    private final Line bottomRightBorder;
    private final Line topLeftBorder;
    private final Line bottomLeftBorder;

    private Rectangle(Line topRightBorder, Line bottomRightBorder, Line topLeftBorder, Line bottomLeftBorder) {
        this.topRightBorder = topRightBorder;
        this.bottomRightBorder = bottomRightBorder;
        this.topLeftBorder = topLeftBorder;
        this.bottomLeftBorder = bottomLeftBorder;
    }

    Point left() {
        return topLeftBorder.cross(bottomLeftBorder);
    }

    Point right() {
        return bottomRightBorder.cross(topRightBorder);
    }

    Point top() {
        return topLeftBorder.cross(topRightBorder);
    }

    Point bottom() {
        return bottomRightBorder.cross(bottomLeftBorder);
    }

    boolean isWithin(Point point) {
        return topLeftBorder.isTopLeftOf(point)
                && topRightBorder.isTopRightOf(point)
                && bottomRightBorder.isBelowRightOf(point)
                && bottomLeftBorder.isBelowLeftOf(point);
    }

    Stream<Point> testablePoints() {
        List<Point> points = new ArrayList<>();

        for (long x = (long) left().x(); x <= right().x(); x++) {
            for (long y = (long) bottom().y(); y <= top().y(); y++) {
                Point point = Point.of(x, y);
                points.add(point);
            }
        }

        return points.stream();
    }

    /**
     * Create a rectangle rotated by 45 degrees clockwise
     */
    static Rectangle createRotated(int width, int height) {
        RectangularTriangle first = RectangularTriangle.by(width / 2.0);
        RectangularTriangle second = RectangularTriangle.by(height / 2.0);

        Line topRightBorder = Line.of(-1, first.hypotenuse());
        Line bottomRightBorder = Line.of(1, -second.hypotenuse());
        Line topLeftBorder = Line.of(1, second.hypotenuse());
        Line bottomLeftBorder = Line.of(-1, -first.hypotenuse());

        return new Rectangle(topRightBorder, bottomRightBorder, topLeftBorder, bottomLeftBorder);
    }
}

class RectangularTriangle {
    private final double hypotenuseC;

    private RectangularTriangle(double hypotenuseC) {
        this.hypotenuseC = hypotenuseC;
    }

    public double hypotenuse() {
        return hypotenuseC;
    }

    static RectangularTriangle by(double cathetus) {
        double hypotenuse = Math.sqrt(cathetus * cathetus * 2);

        return new RectangularTriangle(hypotenuse);
    }
}

class Point {
    private final double x;
    private final double y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double x() {
        return x;
    }

    double y() {
        return y;
    }

    static Point of(double x, double y) {
        return new Point(x, y);
    }
}

class Line {
    private final double m;
    private final double t;

    private Line(double m, double t) {
        this.m = m;
        this.t = t;
    }

    boolean isTopLeftOf(Point p) {
        Point a = atX(p.x());
        Point b = atY(p.y());

        return a.y() > p.y() && b.x() < p.x();
    }

    boolean isBelowRightOf(Point p) {
        Point a = atX(p.x());
        Point b = atY(p.y());

        return a.y() < p.y() && b.x() > p.x();
    }

    boolean isTopRightOf(Point p) {
        Point a = atX(p.x());
        Point b = atY(p.y());

        return a.y() > p.y() && b.x() > p.x();
    }

    boolean isBelowLeftOf(Point p) {
        Point a = atX(p.x());
        Point b = atY(p.y());

        return a.y() < p.y() && b.x() < p.x();
    }

    Point atX(double x) {
        double y = m * x + t;
        return Point.of(x, y);
    }

    Point atY(double y) {
        double x = (y - t) / m;
        return Point.of(x, y);
    }

    Point cross(Line other) {
        double x = (other.t - t) / (m - other.m);
        double y = m * x + t;

        return Point.of(x, y);
    }

    static Line of(double m, double t) {
        return new Line(m, t);
    }
}
