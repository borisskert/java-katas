package nextbiggernumberwiththesamedigits;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KataTests {
    @Test
    public void basicTests() {
        assertEquals(21, Kata.nextBiggerNumber(12));
        assertEquals(531, Kata.nextBiggerNumber(513));
        assertEquals(2071, Kata.nextBiggerNumber(2017));
        assertEquals(441, Kata.nextBiggerNumber(414));
        assertEquals(414, Kata.nextBiggerNumber(144));
        assertEquals(19009, Kata.nextBiggerNumber(10990));
        assertEquals(484998236, Kata.nextBiggerNumber(484996832));
        assertEquals(270635057, Kata.nextBiggerNumber(270630755));
        assertEquals(-1, Kata.nextBiggerNumber(9876543210L));
    }

    @Test
    public void failingTests() {
    }
}
