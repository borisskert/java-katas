package snail;

import org.junit.Assert;
import org.junit.Test;

public class SnailTest {

    @Test
    public void SnailTest1() {
        int[][] array
                = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        int[] r = {1, 2, 3, 6, 9, 8, 7, 4, 5};
        test(array, r);
    }

    @Test
    public void SnailTest2() {
        int[][] array
                = {{1, 2, 3, 9},
                {4, 5, 6, 4},
                {7, 8, 9, 1},
                {1, 2, 3, 4}
        };
        int[] r = {1, 2, 3, 9, 4, 1, 4, 3, 2, 1, 7, 4, 5, 6, 9, 8};
        test(array, r);
    }

    @Test
    public void SnailTest2a() {
        int[][] array
                = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        int[] r = {1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10};
        test(array, r);
    }

    @Test
    public void SnailTestNonSqaure() {
        int[][] array
                = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        int[] r = {1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7};
        test(array, r);
    }

    public void test(int[][] array, int[] result) {
        Assert.assertArrayEquals(result, Snail.snail(array));
    }
}
