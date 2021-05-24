package themillionthfibonaccikata;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class FibonacciTest {

    @Test
    public void testFib0() {
        testFib("0", "0");
    }

    @Test
    public void testFib1() {
        testFib("1", "1");
    }

    @Test
    public void testFib2() {
        testFib("1", "2");
    }

    @Test
    public void testFib3() {
        testFib("2", "3");
    }

    @Test
    public void testFib4() {
        testFib("3", "4");
    }

    @Test
    public void testFib5() {
        testFib("5", "5");
    }

    @Test
    public void testFib6() {
        testFib("8", "6");
    }

    @Test
    public void testFib7() {
        testFib("13", "7");
    }

    @Test
    public void testFib8() {
        testFib("21", "8");
    }

    @Test
    public void testFib9() {
        testFib("34", "9");
    }

    @Test
    public void shouldReturnForMinus6() throws Exception {
        testFib("-8", "-6");
    }

    @Test
    public void shouldReturnForMinus63() throws Exception {
        testFib("6557470319842", "-63");
    }

    @Test
    public void shouldReturnForMinus96() throws Exception {
        testFib("-51680708854858323072", "-96");
    }

    @Test
    public void testHugeFib() {
        System.out.println(Fibonacci.fib(new BigInteger("2000000")));
    }

    private static void testFib(String expected, String input) {
        BigInteger found;
        try {
            found = Fibonacci.fib(new BigInteger(input));
        } catch (Throwable e) {
            // see https://github.com/Codewars/codewars.com/issues/21
            throw new AssertionError("exception during test: " + e, e);
        }
        assertEquals(new BigInteger(expected), found);
    }
}
