package countipaddresses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountIPAddressesTest {
    private void doTest(long expected, String start, String end) {
        long actual = CountIPAddresses.ipsBetween(start, end);
        String message = String.format("for start = \"%s\" and end = \"%s\"", start, end);
        assertEquals(expected, actual, message);
    }

    @Test
    public void sampleTests() {
//        doTest(50, "10.0.0.0", "10.0.0.50");
//        doTest(246, "20.0.0.10", "20.0.1.0");
        doTest((1L << 32L) - 1L, "0.0.0.0", "255.255.255.255");
        doTest(1, "150.0.0.0", "150.0.0.1");
        doTest(50, "10.0.0.0", "10.0.0.50");
        doTest(246, "20.0.0.10", "20.0.1.0");
    }
}
