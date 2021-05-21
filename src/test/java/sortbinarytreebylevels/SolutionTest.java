package sortbinarytreebylevels;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void nullTest() {
        assertEquals(Arrays.asList(), Kata.treeByLevels(null));
    }

    @Test
    public void basicTest() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        new Node(null, null, 4),
                                        2
                                ),
                                new Node(
                                        new Node(null, null, 5),
                                        new Node(null, null, 6),
                                        3
                                ), 1
                        )
                )
        );
    }

    @Test
    public void shouldSortSimplestTree() {
        assertEquals(
                Arrays.asList(1),
                Kata.treeByLevels(
                        new Node(
                                null,
                                null,
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortTwoValuesTree() {
        assertEquals(
                Arrays.asList(1, 2),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        null,
                                        2
                                ),
                                null,
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortThreeValuesTree() {
        assertEquals(
                Arrays.asList(1, 2, 3),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        null,
                                        2
                                ),
                                new Node(
                                        null,
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortFourValuesTree() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        null,
                                        2
                                ),
                                new Node(
                                        null,
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortFourValuesTree2() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        2
                                ),
                                new Node(
                                        null,
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortFourValuesTree3() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        null,
                                        2
                                ),
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortFourValuesTree4() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        null,
                                        2
                                ),
                                new Node(
                                        null,
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortFiveValuesTree() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        new Node(
                                                null,
                                                null,
                                                5
                                        ),
                                        2
                                ),
                                new Node(
                                        null,
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortSixValuesTree() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        new Node(
                                                null,
                                                null,
                                                5
                                        ),
                                        2
                                ),
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                6
                                        ),
                                        null,
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortSevenValuesTree() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                4
                                        ),
                                        new Node(
                                                null,
                                                null,
                                                5
                                        ),
                                        2
                                ),
                                new Node(
                                        new Node(
                                                null,
                                                null,
                                                6
                                        ),
                                        new Node(
                                                null,
                                                null,
                                                7
                                        ),
                                        3
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortExample2() {
        assertEquals(
                Arrays.asList(1, 8, 4, 3, 5, 7),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        new Node(
                                                null,
                                                null,
                                                3
                                        ),
                                        8
                                ),
                                new Node(
                                        null,
                                        new Node(
                                                null,
                                                new Node(
                                                        null,
                                                        null,
                                                        7
                                                ),
                                                5
                                        ),
                                        4
                                ),
                                1
                        )
                )
        );
    }

    @Test
    public void shouldSortRandom() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
                Kata.treeByLevels(
                        new Node(
                                new Node(
                                        null,
                                        new Node(
                                                new Node(
                                                        new Node(
                                                                new Node(
                                                                        null,
                                                                        null,
                                                                        12
                                                                ),
                                                                new Node(
                                                                        null,
                                                                        null,
                                                                        13
                                                                ),
                                                                8
                                                        ),
                                                        new Node(
                                                                new Node(
                                                                        null,
                                                                        null,
                                                                        14
                                                                ),
                                                                new Node(
                                                                        null,
                                                                        null,
                                                                        15
                                                                ),
                                                                9
                                                        ),
                                                        6
                                                ),
                                                null,
                                                4
                                        ),
                                        2
                                ),
                                new Node(
                                        null,
                                        new Node(
                                                null,
                                                new Node(
                                                        new Node(
                                                                new Node(
                                                                        null,
                                                                        null,
                                                                        16
                                                                ),
                                                                new Node(
                                                                        null,
                                                                        new Node(
                                                                                new Node(
                                                                                        null,
                                                                                        null,
                                                                                        19
                                                                                ),
                                                                                null,
                                                                                18
                                                                        ),
                                                                        17
                                                                ),
                                                                10
                                                        ),
                                                        new Node(
                                                                null,
                                                                null,
                                                                11
                                                        ),
                                                        7
                                                ),
                                                5
                                        ),
                                        3
                                ),
                                1
                        )
                )
        );
    }
}
