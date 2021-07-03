package simplefun27rectanglerotation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {
    @Test
    public void BasicTests() {
        assertEquals(23, Solution.rectangleRotation(6, 4));
        assertEquals(65, Solution.rectangleRotation(30, 2));
        assertEquals(49, Solution.rectangleRotation(8, 6));
        assertEquals(333, Solution.rectangleRotation(16, 20));
    }
}
