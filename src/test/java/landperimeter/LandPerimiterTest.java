package landperimeter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @Test
    void testBasic() {
        assertEquals("Total land perimeter: 60",
                LandPerimiter.landPerimeter(new String[]{"OXOOOX", "OXOXOO", "XXOOOX", "OXXXOO", "OOXOOX", "OXOOOO", "OOXOOX", "OOXOOO", "OXOOOO", "OXOOXX"}));
        assertEquals("Total land perimeter: 52", LandPerimiter.landPerimeter(new String[]{"OXOOO", "OOXXX", "OXXOO", "XOOOO", "XOOOO", "XXXOO", "XOXOO", "OOOXO", "OXOOX", "XOOOO", "OOOXO"}));
        assertEquals("Total land perimeter: 40", LandPerimiter.landPerimeter(new String[]{"XXXXXOOO", "OOXOOOOO", "OOOOOOXO", "XXXOOOXO", "OXOXXOOX"}));
        assertEquals("Total land perimeter: 54", LandPerimiter.landPerimeter(new String[]{"XOOOXOO", "OXOOOOO", "XOXOXOO", "OXOXXOO", "OOOOOXX", "OOOXOXX", "XXXXOXO"}));
        assertEquals("Total land perimeter: 40", LandPerimiter.landPerimeter(new String[]{"OOOOXO", "XOXOOX", "XXOXOX", "XOXOOO", "OOOOOO", "OOOXOO", "OOXXOO"}));
        assertEquals("Total land perimeter: 4", LandPerimiter.landPerimeter(new String[]{"X"}));
    }
}
