package factorialdecomposition;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactDecompTest {

    private static void testing(int n, String expected) {
        System.out.println("N: " + n);
        String actual = FactDecomp.decomp(n);
        assertEquals(expected, actual);
    }

    @Test
    public void test1() {
        testing(17, "2^15 * 3^6 * 5^3 * 7^2 * 11 * 13 * 17");
        testing(5, "2^3 * 3 * 5");
        testing(22, "2^19 * 3^9 * 5^4 * 7^3 * 11^2 * 13 * 17 * 19");
        testing(14, "2^11 * 3^5 * 5^2 * 7^2 * 11 * 13");
        testing(25, "2^22 * 3^10 * 5^6 * 7^3 * 11^2 * 13 * 17 * 19 * 23");
    }

    @Test
    public void singleTest() throws Exception {
        testing(2, "2");
        testing(3, "2 * 3");
        testing(4, "2^3 * 3");
        testing(5, "2^3 * 3 * 5");
        testing(6, "2^4 * 3^2 * 5");
        testing(7, "2^4 * 3^2 * 5 * 7");
        testing(8, "2^7 * 3^2 * 5 * 7");
    }

    @Test
    public void failingTests() throws Exception {
        testing(17, "2^15 * 3^6 * 5^3 * 7^2 * 11 * 13 * 17");
    }
}
