package totalareacoveredbyrectangles;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static totalareacoveredbyrectangles.Rectangle.from;

public class RectanglesUnionTest {
    @Test
    public void testZeroRectangles() {
        int[][] recs = {};
        Assert.assertEquals("Zero rectangles", 0, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testOneRectangle() {
        int[][] recs = {{0, 4, 11, 6}};
        Assert.assertEquals("One rectangle [0, 4, 11, 6] => 22", 22, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void anotherExample() {
        int[][] recs = new int[][]{{3, 3, 8, 5}, {6, 3, 8, 9}, {11, 6, 14, 12}};
        assertThat(RectanglesUnion.calculateSpace(recs)).isEqualTo(36);
    }

    @Test
    public void shouldCreateIntersection() throws Exception {
        Rectangle rectA = from(new Point(0, 0), new Point(2, 2));
        Rectangle rectB = from(new Point(1, 1), new Point(3, 3));
        Rectangle expected = from(new Point(1, 1), new Point(2, 2));

        assertThat(rectA.intersection(rectB).get()).isEqualTo(expected);
    }

    @Test
    public void shouldNotCreateIntersection() throws Exception {
        Rectangle rectA = from(new Point(0, 0), new Point(2, 2));
        Rectangle rectB = from(new Point(2, 2), new Point(3, 3));

        assertThat(rectA.intersection(rectB).isPresent()).isEqualTo(false);
    }

    @Test
    public void shouldHugeOverlap() throws Exception {
        for (int x = 0; x < 10; x++) {
            List<int[]> rectangles = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                int[] rect = new int[]{i, i, i + 1000, i + 1000};

                rectangles.add(rect);
            }

            assertThat(RectanglesUnion.calculateSpace(rectangles.toArray(new int[][]{}))).isEqualTo(20988001);
        }
    }
}
