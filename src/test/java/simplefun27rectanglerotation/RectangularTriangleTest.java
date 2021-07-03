package simplefun27rectanglerotation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RectangularTriangleTest {

    @Test
    void shouldCreateRectangularTriangle() throws Exception {
        RectangularTriangle triangle = RectangularTriangle.by(1.5);

        assertThat(triangle.hypotenuse()).isEqualTo(2.1213203435596424);
    }
}
