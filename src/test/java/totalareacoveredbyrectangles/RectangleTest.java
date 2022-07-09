package totalareacoveredbyrectangles;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static totalareacoveredbyrectangles.Rectangle.from;

class RectangleTest {
    @Test
    void shouldCreateIntersection() throws Exception {
        Rectangle rectA = from(new Point(0, 0), new Point(2, 2));
        Rectangle rectB = from(new Point(1, 1), new Point(3, 3));
        Rectangle expected = from(new Point(1, 1), new Point(2, 2));

        assertThat(rectA.intersection(rectB).get()).isEqualTo(expected);
    }

    @Test
    void shouldNotCreateIntersection() throws Exception {
        Rectangle rectA = from(new Point(0, 0), new Point(2, 2));
        Rectangle rectB = from(new Point(2, 2), new Point(3, 3));

        assertThat(rectA.intersection(rectB).isPresent()).isEqualTo(false);
    }
}
