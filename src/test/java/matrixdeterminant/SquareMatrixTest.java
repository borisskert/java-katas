package matrixdeterminant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SquareMatrixTest {

    @Test
    public void shouldCreateSquareMatrix() throws Exception {
        SquareMatrix matrix = SquareMatrix.of(new int[][]{{1, 2}, {3, 4}});

        assertThat(matrix.size()).isEqualTo(2);
        assertThat(matrix.get(0, 0)).isEqualTo(1);
        assertThat(matrix.get(0, 1)).isEqualTo(2);
        assertThat(matrix.get(1, 0)).isEqualTo(3);
        assertThat(matrix.get(1, 1)).isEqualTo(4);
    }

    @Test
    public void shouldCreateMinorMatrixOfTwoTwo() throws Exception {
        SquareMatrix matrix = SquareMatrix.of(new int[][]{{1, 2}, {3, 4}}).minorOf(0, 0);
        assertThat(matrix.size()).isEqualTo(1);
    }

    @Test
    public void shouldCreateMinorMatrixOfThreeThree() throws Exception {
        SquareMatrix matrix = SquareMatrix.of(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).minorOf(0, 0);

        assertThat(matrix.size()).isEqualTo(2);
        assertThat(matrix.get(0, 0)).isEqualTo(5);
        assertThat(matrix.get(0, 1)).isEqualTo(6);
        assertThat(matrix.get(1, 0)).isEqualTo(8);
        assertThat(matrix.get(1, 1)).isEqualTo(9);
    }

    @Test
    public void shouldCreateMinorMatrixOfThreeThreeAtOneOne() throws Exception {
        SquareMatrix matrix = SquareMatrix.of(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).minorOf(1, 1);

        assertThat(matrix.size()).isEqualTo(2);
        assertThat(matrix.get(0, 0)).isEqualTo(1);
        assertThat(matrix.get(0, 1)).isEqualTo(3);
        assertThat(matrix.get(1, 0)).isEqualTo(7);
        assertThat(matrix.get(1, 1)).isEqualTo(9);
    }
}
