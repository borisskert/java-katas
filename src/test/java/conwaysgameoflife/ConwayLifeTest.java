package conwaysgameoflife;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertTrue;

public class ConwayLifeTest {

    @Test
    public void testGlider() {
        int[][][] gliders = {
                {{1, 0, 0},
                        {0, 1, 1},
                        {1, 1, 0}},
                {{0, 1, 0},
                        {0, 0, 1},
                        {1, 1, 1}}
        };
        System.out.println("Glider");
        LifeDebug.print(gliders[0]);
        int[][] res = ConwayLife.getGeneration(gliders[0], 1);
        assertTrue("Got \n" + LifeDebug.htmlize(res) + "\ninstead of\n" + LifeDebug.htmlize(gliders[1]), LifeDebug.equals(res, gliders[1]));
    }

    @Test
    public void shouldNotChangeForZeroGenerations() throws Exception {
        int[][] input = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };
        int[][] nextGeneration = ConwayLife.getGeneration(input, 0);

        assertTrue(
                "Got \n" + LifeDebug.htmlize(nextGeneration) + "\ninstead of\n" + LifeDebug.htmlize(input),
                LifeDebug.equals(input, nextGeneration)
        );
    }

    @Test
    public void shouldChangeForFirstGeneration() throws Exception {
        int[][] input = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };
        int[][] nextGeneration = ConwayLife.getGeneration(input, 1);

        int[][] expected = {
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1}
        };

        assertTrue(
                "Got \n" + LifeDebug.htmlize(nextGeneration) + "\ninstead of\n" + LifeDebug.htmlize(expected),
                LifeDebug.equals(expected, nextGeneration)
        );
    }

    @Test
    public void shouldChangeForSecondGeneration() throws Exception {
        int[][] input = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };
        int[][] nextGeneration = ConwayLife.getGeneration(input, 2);

        int[][] expected = {
                {1, 0, 1},
                {0, 1, 1},
                {0, 1, 0}
        };

        assertTrue(
                "Got \n" + LifeDebug.htmlize(nextGeneration) + "\ninstead of\n" + LifeDebug.htmlize(expected),
                LifeDebug.equals(expected, nextGeneration)
        );
    }

    @Test
    public void shouldChangeForThirdGeneration() throws Exception {
        int[][] input = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };
        int[][] nextGeneration = ConwayLife.getGeneration(input, 3);

        int[][] expected = {
                {0, 0, 1},
                {1, 0, 1},
                {0, 1, 1}
        };

        assertTrue(
                "Got \n" + LifeDebug.htmlize(nextGeneration) + "\ninstead of\n" + LifeDebug.htmlize(expected),
                LifeDebug.equals(expected, nextGeneration)
        );
    }

    @Test
    public void shouldChangeForForthGeneration() throws Exception {
        int[][] input = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };
        int[][] nextGeneration = ConwayLife.getGeneration(input, 4);

        int[][] expected = {
                {1, 0, 0},
                {0, 1, 1},
                {1, 1, 0}
        };

        assertTrue(
                "Got \n" + LifeDebug.htmlize(nextGeneration) + "\ninstead of\n" + LifeDebug.htmlize(expected),
                LifeDebug.equals(expected, nextGeneration)
        );
    }


}

class LifeDebug {

    public static void print(int[][] glider) {
        System.out.print(htmlize(glider));
    }

    public static String htmlize(int[][] glider) {
        if (glider == null) {
            return "null";
        }

        StringJoiner newlineJoiner = new StringJoiner("\n");

        for (int[] gliderLine : glider) {
            StringBuilder newline = new StringBuilder();

            for (int gliderValue : gliderLine) {
                if (gliderValue == 1) {
                    newline.append('#');
                } else {
                    newline.append('.');
                }
            }

            newlineJoiner.add(newline.toString());
        }

        return newlineJoiner.toString();
    }

    public static boolean equals(int[][] res, int[][] glider) {
        if (res == glider) {
            return true;
        }

        if (res == null || glider == null) {
            return false;
        }

        if (res.length != glider.length) {
            return false;
        }

        for (int x = 0; x < glider.length; x++) {
            if (res[x].length != glider[x].length) {
                return false;
            }

            for (int y = 0; y < glider[x].length; y++) {
                if (glider[x][y] != res[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }
}