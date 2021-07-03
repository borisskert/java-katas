package simplefun27rectanglerotation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LineTest {
    private static final Point A = Point.of(2, 1);
    private static final Point B = Point.of(3, 5);

    @Test
    void shouldCalculateCrossPoint() throws Exception {
        Line g = Line.of(-0.5, 1);
        Line h = Line.of(1, -2);

        Point cross = g.cross(h);

        assertThat(cross).isEqualTo(Point.of(2, 0));
    }
}
