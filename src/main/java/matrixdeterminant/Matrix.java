package matrixdeterminant;

/**
 * https://www.codewars.com/kata/52a382ee44408cea2500074c/train/java
 */
public class Matrix {

    public static int determinant(int[][] matrix) {
        return SquareMatrix.of(matrix).determinant();
    }
}

class SquareMatrix {
    private final int size;
    private final int[][] matrix;

    private SquareMatrix(int size, int[][] matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    int size() {
        return size;
    }

    int get(int x, int y) {
        return matrix[x][y];
    }

    int determinant() {
        if (size == 1) {
            return matrix[0][0];
        }

        if (size == 2) {
            int a = matrix[0][0];
            int b = matrix[0][1];
            int c = matrix[1][0];
            int d = matrix[1][1];

            return a * d - b * c;
        }

        int determinant = 0;
        for (int y = 0; y < size; y++) {
            int det = matrix[0][y] * minorOf(0, y).determinant();

            if (y % 2 == 0) {
                determinant += det;
            } else {
                determinant -= det;
            }
        }


        return determinant;
    }

    SquareMatrix minorOf(int x, int y) {
        int[][] matrix = new int[size - 1][size - 1];

        for (int indexX = 0; indexX < size; indexX++) {
            for (int indexY = 0; indexY < size; indexY++) {
                if (indexX < x && indexY < y) {
                    matrix[indexX][indexY] = this.matrix[indexX][indexY];
                } else if (indexX < x && indexY > y) {
                    matrix[indexX][indexY - 1] = this.matrix[indexX][indexY];
                } else if (indexX > x && indexY < y) {
                    matrix[indexX - 1][indexY] = this.matrix[indexX][indexY];
                } else if (indexX > x && indexY > y) {
                    matrix[indexX - 1][indexY - 1] = this.matrix[indexX][indexY];
                }
            }
        }

        return new SquareMatrix(size - 1, matrix);
    }

    static SquareMatrix of(int[][] matrix) {
        return new SquareMatrix(matrix.length, matrix);
    }
}
