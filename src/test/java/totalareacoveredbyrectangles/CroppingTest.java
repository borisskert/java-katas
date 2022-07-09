package totalareacoveredbyrectangles;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CroppingTest {

    @Test
    void shouldCropSimpleRectangleFromAnother() {
        Rectangle originalRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Rectangle cropping = Rectangle.from(new Point(2, 2), new Point(4, 4));

        Cropping actualCrop = Cropping.of(originalRect).crop(cropping);
        Cropping expectedCrop = Cropping.from(originalRect, List.of(Cropping.of(Rectangle.from(new Point(2, 2), new Point(3, 3)))));

        assertThat(actualCrop).isEqualTo(expectedCrop);
        assertThat(actualCrop.space()).isEqualTo(8);
    }

    @Test
    void shouldSecondRectangleFromCropping() {
        Rectangle originalRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Cropping originalCropping = Cropping.from(
                originalRect,
                List.of(Cropping.of(Rectangle.from(new Point(2, 2), new Point(3, 3))))
        );

        Cropping actualCrop = originalCropping.crop(Rectangle.from(new Point(1, 2), new Point(5, 5)));
        Cropping expectedCrop = Cropping.from(originalRect, List.of(Cropping.of(Rectangle.from(new Point(1, 2), new Point(3, 3)))));

        assertThat(actualCrop).isEqualTo(expectedCrop);

    }


}
