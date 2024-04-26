package gapinprimes;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class GapInPrimesTest {

    @Test
    public void test() {
        assertEquals("[101, 103]", Arrays.toString(GapInPrimes.gap(2, 100, 110)));
        assertEquals("[103, 107]", Arrays.toString(GapInPrimes.gap(4, 100, 110)));
        assertNull(GapInPrimes.gap(6, 100, 110));
        assertEquals("[359, 367]", Arrays.toString(GapInPrimes.gap(8, 300, 400)));
        assertEquals("[337, 347]", Arrays.toString(GapInPrimes.gap(10, 300, 400)));
    }

    @Test
    @Ignore
    public void testPerf() {
        assertEquals("[100049, 100057]", Arrays.toString(GapInPrimes.gap(8, 100000, 110000)));
        assertEquals("[10000799, 10000813]", Arrays.toString(GapInPrimes.gap(14, 10000000, 11000000)));
        assertEquals("[1000004933, 1000004977]", Arrays.toString(GapInPrimes.gap(44, 1000000000, 1100000000)));
        assertEquals("[1000016323, 1000016411]", Arrays.toString(GapInPrimes.gap(88, 1000000000, 1100000000)));
        assertEquals("[1000007941, 1000008041]", Arrays.toString(GapInPrimes.gap(100, 1000000000, 1100000000)));
        assertEquals("[1009855001, 1009855201]", Arrays.toString(GapInPrimes.gap(200, 1000000000, 1100000000)));
    }
}
