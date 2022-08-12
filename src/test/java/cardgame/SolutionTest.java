package cardgame;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @Test
    void sampleTests() {
        assertEquals(1, Solution.cardGame(1));
        assertEquals(1, Solution.cardGame(2));
        assertEquals(2, Solution.cardGame(3));
        assertEquals(3, Solution.cardGame(4));
        assertEquals(2, Solution.cardGame(5));
        assertEquals(4, Solution.cardGame(6));
        assertEquals(3, Solution.cardGame(7));
        assertEquals(5, Solution.cardGame(8));
        assertEquals(4, Solution.cardGame(9));
        assertEquals(8, Solution.cardGame(10));
        assertEquals(3, Solution.cardGame(11));
        assertEquals(9, Solution.cardGame(12));
        assertEquals(4, Solution.cardGame(13));
        assertEquals(11, Solution.cardGame(14));
        assertEquals(4, Solution.cardGame(15));
        assertEquals(12, Solution.cardGame(16));
        assertEquals(4, Solution.cardGame(23));
        assertEquals(20, Solution.cardGame(24));
        assertEquals(22, Solution.cardGame(26));
        assertEquals(18L, Solution.cardGame(12829L));
        assertEquals(99999999950L, Solution.cardGame(100000000000L));
        assertEquals(99999999999938L, Solution.cardGame(100000000000000L));
        assertEquals(999999999999999919L, Solution.cardGame(1000000000000000000L));
        assertEquals(9223372036854775744L, Solution.cardGame(Long.MAX_VALUE - 1));
        assertEquals(63, Solution.cardGame(Long.MAX_VALUE));
    }

    @Test
    @Ignore("just a performance test")
    void should() throws Exception {
        for (int i = 0; i < 100000000; i++) {
            Solution.cardGame(Long.MAX_VALUE - 1);
        }
    }
}
