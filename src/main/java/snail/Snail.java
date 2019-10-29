package snail;

import java.util.Iterator;

public class Snail {
    public static int[] snail(int[][] array) {
        int length = array.length;
        int width = array[0].length;

        int count = length * width;
        int index = 0;
        int[] values = new int[count];

        SnailIterator snail = new SnailIterator(array);

        while (snail.hasNext()) {
            values[index] = snail.next();
            index++;
        }

        return values;
    }

    private static class SnailIterator implements Iterator<Integer> {
        private final int[][] values;
        private Direction direction = Direction.E;

        private int maxX;
        private int maxY;
        private int minX;
        private int minY;

        private int x = 0;
        private int y = 0;

        private SnailIterator(int[][] values) {
            this.values = values;

            this.minX = -1;
            this.minY = -1;

            this.maxX = values.length;
            this.maxY = values[0].length;
        }

        public Integer next() {
            int value = values[x][y];

            move();

            return value;
        }

        public boolean hasNext() {
            return y > minY && y < maxY && x > minX && x < maxX;
        }

        private void move() {
            if (direction == Direction.E) {
                tryToMoveEast();
            } else if (direction == Direction.S) {
                tryToMoveSouth();
            } else if (direction == Direction.W) {
                tryToMoveWest();
            } else if (direction == Direction.N) {
                tryToMoveNorth();
            }
        }

        private void tryToMoveNorth() {
            if (x - 1 <= minX) {
                direction = Direction.E;
                minY++;
                y++;
            } else {
                x--;
            }
        }

        private void tryToMoveWest() {
            if (y - 1 <= minY) {
                direction = Direction.N;
                maxX--;
                x--;
            } else {
                y--;
            }
        }

        private void tryToMoveSouth() {
            if (x + 1 >= maxX) {
                direction = Direction.W;
                maxY--;
                y--;
            } else {
                x++;
            }
        }

        private void tryToMoveEast() {
            if (y + 1 >= maxY) {
                direction = Direction.S;
                minX++;
                x++;
            } else {
                y++;
            }
        }

        private enum Direction {
            N, S, E, W
        }
    }
}
