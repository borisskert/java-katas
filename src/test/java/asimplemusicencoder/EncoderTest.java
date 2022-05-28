package asimplemusicencoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EncoderTest {
    @Test
    public void basicTests() {
        test("2 identical numbers", new int[]{1, 2, 2, 3}, "1,2*2,3");
        test("3 consecutive numbers, ascending", new int[]{1, 3, 4, 5, 7}, "1,3-5,7");
        test("3 consecutive numbers, descending", new int[]{1, 5, 4, 3, 7}, "1,5-3,7");
        test("3 numbers with same interval, descending", new int[]{1, 10, 8, 6, 7}, "1,10-6/2,7");
        test("failing test #1", new int[]{25, 170, 170, 170, 170, 170, 149, 149, 161, 2, 4, 6}, "25,170*5,149*2,161,2-6/2");
        test("failing test #2", new int[]{74, 108, 5, 5, 164, 163, 162, 161, 53, 6, 9, 12, 15, 18, 72, 70, 68, 66, 64}, "74,108,5*2,164-161,53,6-18/3,72-64/2");
        test("failing test #3", new int[]{-2, -2}, "-2*2");
    }

    private void test(String description, int[] raw, String encoded) {
        assertEquals(description, encoded, new Encoder().compress(raw));
    }
}
