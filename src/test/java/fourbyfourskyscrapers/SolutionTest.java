package fourbyfourskyscrapers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class SolutionTest {

    private static int clues[][] = {
            {2, 2, 1, 3,
                    2, 2, 3, 1,
                    1, 2, 2, 3,
                    3, 2, 1, 3},
            {0, 0, 1, 2,
                    0, 2, 0, 0,
                    0, 3, 0, 0,
                    0, 1, 0, 0}
    };

    private static int outcomes[][][] = {
            {{1, 3, 4, 2},
                    {4, 2, 1, 3},
                    {3, 4, 2, 1},
                    {2, 1, 3, 4}},
            {{2, 1, 4, 3},
                    {3, 4, 1, 2},
                    {4, 2, 3, 1},
                    {1, 3, 2, 4}}
    };

    @Test
    public void testSolvePuzzle1() {
        assertEquals(SkyScrapers.solvePuzzle(clues[0]), outcomes[0]);
    }

    @Test
    public void testSolvePuzzle2() {
        assertEquals(SkyScrapers.solvePuzzle(clues[1]), outcomes[1]);
    }

    @Test
    public void failedTest() throws Exception {
        assertThat(
                SkyScrapers.solvePuzzle(
                        new int[]{1, 2, 4, 2, 2, 1, 3, 2, 3, 1, 2, 3, 3, 2, 2, 1}
                )
        ).isEqualTo(new int[][]{
                {4, 2, 1, 3},
                {3, 1, 2, 4},
                {1, 4, 3, 2},
                {2, 3, 4, 1}
        });
    }
}
