package sumbyfactors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SumOfDividedTest {

    @Test
    public void testOne() {
        int[] lst = new int[] {12, 15};
        assertEquals("(2 12)(3 27)(5 15)", SumOfDivided.sumOfDivided(lst));
    }

    @Test
    public void failing() throws Exception {
        int[] lst = new int[] {-29804, -4209, -28265, -72769, -31744};
        assertEquals("(2 -61548)(3 -4209)(5 -28265)(23 -4209)(31 -31744)(53 -72769)(61 -4209)(1373 -72769)(5653 -28265)(7451 -29804)", SumOfDivided.sumOfDivided(lst));
    }
}
