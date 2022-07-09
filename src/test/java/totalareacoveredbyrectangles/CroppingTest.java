package totalareacoveredbyrectangles;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CroppingTest {

    @Test
    void shouldCropSimpleRectangleFromAnother() {
        Rectangle originalRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Rectangle cropping = Rectangle.from(new Point(2, 2), new Point(4, 4));

        Optional<Cropping> actualCrop = Cropping.of(originalRect).crop(cropping);
        Cropping expectedCrop = Cropping.from(originalRect, List.of(Cropping.of(Rectangle.from(new Point(2, 2), new Point(3, 3)))));

        assertThat(actualCrop).isEqualTo(Optional.of(expectedCrop));
        assertThat(actualCrop.get().space()).isEqualTo(8);
    }

    @Test
    void shouldSecondRectangleFromCropping() {
        Rectangle originalRect = Rectangle.from(new Point(0, 0), new Point(3, 3));
        Cropping originalCropping = Cropping.from(
                originalRect,
                List.of(Cropping.of(Rectangle.from(new Point(2, 2), new Point(3, 3))))
        );

        Optional<Cropping> actualCrop = originalCropping.crop(Rectangle.from(new Point(1, 2), new Point(5, 5)));
        Cropping expectedCrop = Cropping.from(originalRect, List.of(Cropping.of(Rectangle.from(new Point(1, 2), new Point(3, 3)))));

        assertThat(actualCrop).isEqualTo(Optional.of(expectedCrop));
        assertThat(actualCrop.get().space()).isEqualTo(7);
    }

    @Test
    void shouldDisappearWhenCropEverything() throws Exception {
        Rectangle originalRect = Rectangle.from(new Point(1, 1), new Point(2, 2));
        Rectangle largerRect = Rectangle.from(new Point(0, 0), new Point(3, 3));

        Optional<Cropping> actualCrop = Cropping.of(originalRect).crop(largerRect);

        assertThat(actualCrop).isEqualTo(Optional.empty());
    }

    @Test
    void shouldCropMoreComplexCropFromAnother() throws Exception {
        Rectangle originalRect = Rectangle.from(new Point(0, 0), new Point(4, 4));
        Rectangle cropRectOne = Rectangle.from(new Point(1, 2), new Point(5, 3));
        Rectangle cropRectTwo = Rectangle.from(new Point(2, 1), new Point(3, 5));

        Cropping actualCrop = Cropping.of(originalRect).crop(cropRectOne).get().crop(cropRectTwo).get();

        assertThat(actualCrop.space()).isEqualTo(16 - 3 - 2);
    }
}
