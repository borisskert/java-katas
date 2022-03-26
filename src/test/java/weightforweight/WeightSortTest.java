package weightforweight;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeightSortTest {

    @Test
    public void BasicTests() {
        assertEquals("2000 103 123 4444 99", WeightSort.orderWeight("103 123 4444 99 2000"));
        assertEquals("11 11 2000 10003 22 123 1234000 44444444 9999", WeightSort.orderWeight("2000 10003 1234000 44444444 9999 11 11 22 123"));
    }
}
