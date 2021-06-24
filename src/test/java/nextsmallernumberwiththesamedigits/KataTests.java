package nextsmallernumberwiththesamedigits;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KataTests {
    @Test
    public void basicTests() {
        assertEquals(12, Kata.nextSmaller(21));
        assertEquals(513, Kata.nextSmaller(531));
        assertEquals(414, Kata.nextSmaller(441));
        assertEquals(790, Kata.nextSmaller(907));
        assertEquals(1144, Kata.nextSmaller(1414));
        assertEquals(-1, Kata.nextSmaller(1027));
        assertEquals(351, Kata.nextSmaller(513));
        assertEquals(123456789, Kata.nextSmaller(123456798));
    }

    @Test
    public void failingTests() throws Exception {
        assertEquals(7369761, Kata.nextSmaller(7371669));
    }
}
