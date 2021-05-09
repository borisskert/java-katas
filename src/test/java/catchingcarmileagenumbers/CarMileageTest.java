package catchingcarmileagenumbers;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CarMileageTest {

    @Test
    public void testTooSmall() {
        assertEquals(0, CarMileage.isInteresting(3, new int[]{1337, 256}));
    }

    @Test
    public void testAlmostAwesome() {
        assertEquals(1, CarMileage.isInteresting(1336, new int[]{1337, 256}));
    }

    @Test
    public void testAwesome() {
        assertEquals(2, CarMileage.isInteresting(1337, new int[]{1337, 256}));
    }

    @Test
    public void testFarNotInteresting() {
        assertEquals(0, CarMileage.isInteresting(11208, new int[]{1337, 256}));
    }

    @Test
    public void testAlmostInteresting() {
        assertEquals(1, CarMileage.isInteresting(11209, new int[]{1337, 256}));
        assertEquals(1, CarMileage.isInteresting(110, new int[]{1337, 256}));
    }

    @Test
    public void testAlmostMonotone() {
        assertEquals(1, CarMileage.isInteresting(109, new int[]{1337, 256}));
    }

    @Test
    public void testInteresting() {
        assertEquals(2, CarMileage.isInteresting(11211, new int[]{1337, 256}));
        assertEquals(2, CarMileage.isInteresting(10000, new int[]{1337, 256}));
        assertEquals(2, CarMileage.isInteresting(11111, new int[]{1337, 256}));
        assertEquals(2, CarMileage.isInteresting(12345, new int[]{1337, 256}));
        assertEquals(2, CarMileage.isInteresting(54321, new int[]{1337, 256}));
        assertEquals(2, CarMileage.isInteresting(67890, new int[]{1337, 256}));
    }
}
