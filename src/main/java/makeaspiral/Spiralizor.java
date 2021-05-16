package makeaspiral;

import java.util.Iterator;
import java.util.Optional;

/**
 * https://www.codewars.com/kata/534e01fbbb17187c7e0000c6/train/java
 */
public class Spiralizor {

    public static int[][] spiralize(int size) {
        int[][] field = createField(size);
        Iterator<Coordinates> spiral = new SpiralIterator(size);

        while (spiral.hasNext()) {
            Coordinates position = spiral.next();
            field[position.y][position.x] = 1;
        }

        return field;
    }

    private static int[][] createField(int size) {
        int[][] field = new int[size][];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            field[rowIndex] = new int[size];
        }

        return field;
    }

    static class Coordinates {
        private final int x;
        private final int y;

        Coordinates() {
            this.x = 0;
            this.y = 0;
        }

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class SpiralIterator implements Iterator<Coordinates> {
        private Coordinates currentPosition = null;
        private Direction currentDirection = Direction.EAST;

        private int minX = -1;
        private int minY = -1;
        private int maxX;
        private int maxY;

        SpiralIterator(int size) {
            this.maxX = size;
            this.maxY = size;
        }

        @Override
        public boolean hasNext() {
            Optional<Coordinates> nextPosition = peekNextPosition();
            boolean hasNext = nextPosition.isPresent();

            if (hasNext) {
                currentPosition = nextPosition.get();
            }

            return hasNext;
        }

        @Override
        public Coordinates next() {
            return currentPosition;
        }

        private Optional<Coordinates> peekNextPosition() {
            if (currentPosition == null) {
                return Optional.of(new Coordinates());
            }

            if (minX >= maxX || minY >= maxY) {
                return Optional.empty();
            }

            if (currentDirection == Direction.EAST) {
                return tryToMoveEast();
            } else if (currentDirection == Direction.SOUTH) {
                return tryToMoveSouth();
            } else if (currentDirection == Direction.WEST) {
                return tryToMoveWest();
            } else if (currentDirection == Direction.NORTH) {
                return tryToMoveNorth();
            }

            return Optional.empty();
        }

        private Optional<Coordinates> tryToMoveNorth() {
            if (currentPosition.x >= maxX) {
                return Optional.empty();
            } else if (currentPosition.y - 1 > minY) {
                return Optional.of(new Coordinates(currentPosition.x, currentPosition.y - 1));
            } else {
                currentDirection = Direction.EAST;
                minX = currentPosition.x + 1;

                return peekNextPosition();
            }
        }

        private Optional<Coordinates> tryToMoveWest() {
            if (currentPosition.y <= minY) {
                return Optional.empty();
            } else if (currentPosition.x - 1 > minX) {
                return Optional.of(new Coordinates(currentPosition.x - 1, currentPosition.y));
            } else {
                currentDirection = Direction.NORTH;
                maxY = currentPosition.y - 1;

                return peekNextPosition();
            }
        }

        private Optional<Coordinates> tryToMoveSouth() {
            if (currentPosition.x >= maxX) {
                return Optional.empty();
            }
            if (currentPosition.y + 1 < maxY) {
                return Optional.of(new Coordinates(currentPosition.x, currentPosition.y + 1));
            } else {
                currentDirection = Direction.WEST;
                maxX = currentPosition.x - 1;

                return peekNextPosition();
            }
        }

        private Optional<Coordinates> tryToMoveEast() {
            if (currentPosition.y >= maxY) {
                return Optional.empty();
            } else if (currentPosition.x + 1 < maxX) {
                return Optional.of(new Coordinates(currentPosition.x + 1, currentPosition.y));
            } else {
                currentDirection = Direction.SOUTH;
                minY = currentPosition.y + 1;

                return peekNextPosition();
            }
        }
    }

    enum Direction {
        NORTH, SOUTH, WEST, EAST
    }
}
