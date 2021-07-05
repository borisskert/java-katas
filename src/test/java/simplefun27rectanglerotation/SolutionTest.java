package simplefun27rectanglerotation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static simplefun27rectanglerotation.Solution.rectangleRotation;

public class SolutionTest {
    @Test
    public void BasicTests() {
        assertEquals(23, rectangleRotation(6, 4));
        assertEquals(65, rectangleRotation(30, 2));
        assertEquals(49, rectangleRotation(8, 6));
        assertEquals(333, rectangleRotation(16, 20));
    }

    @Test
    public void shouldCalculateForQuadraticRectangles() throws Exception {
        assertThat(rectangleRotation(1, 1)).isEqualTo(1);
        assertThat(rectangleRotation(2, 2)).isEqualTo(5);
        assertThat(rectangleRotation(3, 3)).isEqualTo(13);
        assertThat(rectangleRotation(4, 4)).isEqualTo(13);
        assertThat(rectangleRotation(5, 5)).isEqualTo(25);
        assertThat(rectangleRotation(6, 7)).isEqualTo(41);
    }
}
