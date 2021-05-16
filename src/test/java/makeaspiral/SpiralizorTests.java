package makeaspiral;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpiralizorTests {

    @Test
    public void test5() {
        print(Spiralizor.spiralize(5));

        assertArrayEquals(
                new int[][] {
                        { 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1 }
                },
                Spiralizor.spiralize(5));
    }

    @Test
    public void test8() {
        int[][] spiralize = Spiralizor.spiralize(8);
        print(spiralize);
        assertArrayEquals(
                new int[][] {
                        { 1, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 0, 1, 0, 1 },
                        { 1, 0, 1, 0, 0, 1, 0, 1 },
                        { 1, 0, 1, 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1, 1, 1 },
                },
                spiralize);
    }

    @Test
    public void test6() {
        int[][] spiralize = Spiralizor.spiralize(6);
        print(spiralize);
        assertArrayEquals(
                new int[][] {
                        { 1, 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 1, 0, 1 },
                        { 1, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1 },
                },
                spiralize);
    }

    private void print(int[][] spiral) {
        for(int rowIndex = 0; rowIndex < spiral.length; rowIndex++) {
            for(int columnIndex = 0; columnIndex < spiral.length; columnIndex++) {
                System.out.print(spiral[rowIndex][columnIndex]);
            }
            System.out.println();
        }
    }
}
