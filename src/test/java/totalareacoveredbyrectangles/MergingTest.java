package totalareacoveredbyrectangles;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergingTest {

    @Test
    void shouldMergeEmptyWithRectangle() throws Exception {
        Merging merge = Merging.empty().mergeWith(Rectangle.from(new Point(1, 1), new Point(2, 2)));
        assertThat(merge.space()).isEqualTo(1);
    }

    @Test
    void shouldSimplyMergeTwoRectangles() throws Exception {
        Rectangle firstRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Rectangle secondRect = Rectangle.from(new Point(3, 3), new Point(4, 4));

        Merging merged = Merging.of(firstRect).mergeWith(secondRect);

        assertThat(merged.space()).isEqualTo(10);
    }

    @Test
    void shouldMergeTwoIntersectingRectangles() throws Exception {
        Rectangle firstRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Rectangle secondRect = Rectangle.from(new Point(2, 2), new Point(4, 4));

        Merging merged = Merging.of(firstRect).mergeWith(secondRect);

        assertThat(merged.space()).isEqualTo(12);
    }

    @Test
    void shouldThreeIntersectingRectangles() throws Exception {
        Rectangle firstRect = Rectangle.from(new Point(0, 0), new Point(4, 4));
        Rectangle secondRect = Rectangle.from(new Point(1, 2), new Point(5, 3));
        Rectangle thirdRect = Rectangle.from(new Point(2, 1), new Point(3, 5));

        Merging merged = Merging.of(firstRect).mergeWith(secondRect).mergeWith(thirdRect);

        assertThat(merged.space()).isEqualTo(18);
    }
}
