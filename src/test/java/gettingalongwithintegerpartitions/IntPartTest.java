package gettingalongwithintegerpartitions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntPartTest {

    @Test
    public void Numbers_Small() {
        assertEquals("Range: 1 Average: 1.50 Median: 1.50", IntPart.part(2));
        assertEquals("Range: 2 Average: 2.00 Median: 2.00", IntPart.part(3));
        assertEquals("Range: 3 Average: 2.50 Median: 2.50", IntPart.part(4));
        assertEquals("Range: 5 Average: 3.50 Median: 3.50", IntPart.part(5));
    }
}
