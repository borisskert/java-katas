package interlacedspiralcipher;

import java.util.HashMap;
import java.util.Map;

public class InterlacedSpiralCipher {

    public static String encode(String s) {
        Map<Integer, Integer> map = createMap(s.length());

        char[] result = new char[map.size()];

        for(int index = 0; index < s.length(); index++) {
            Integer cipherIndex = map.get(index);
            result[index] = s.charAt(cipherIndex);
        }

        return String.valueOf(result);
    }

    private static Map<Integer, Integer> createMap(int textLength) {
        int cipherLength = length(textLength);
        int[] spiral = new int[cipherLength];

        int ring = (int)Math.min(1, Math.sqrt(cipherLength) - 2);
        int round = 0;
        int offset = 0;
        int step = 0;
        int index = 0;
        int size = 2 * ring + 1;

        for(; index < textLength; index++) {
            if(round >= 4) {
                round = 0;
                step++;
            }

            if(step >= size) {
                offset = size * 4;
                ring--;
                step = 0;
                size = 2 * ring + 1;
            }

            int position = offset + step + size * round;
            spiral[position] = index;

            round++;
        }

        for(; index < spiral.length; index++) {
            spiral[index] = ' ';
        }

        Snail snail = new Snail(textLength);
        for(index = 0; index < spiral.length; index++) {
            snail.setNext(spiral[index]);
        }

        return snail.getMap();
    }

    public static String decode(String s) {
        // Your code here!
        return "";
    }

    private static int length(int textLength) {
        int squareRoot = (int)Math.sqrt(textLength);
        int power = squareRoot * squareRoot;

        if(power < textLength) {
            return (squareRoot + 1) * (squareRoot + 1);
        }

        return power;
    }

    private static class Snail<T> {
        private final T[][] values;
        private Direction direction = Direction.E;

        private final int size;

        private int maxX;
        private int maxY;
        private int minX;
        private int minY;

        private int x = 0;
        private int y = 0;

        private Snail(int size) {
            this.size = length(size);
            this.values = create(size);

            this.minX = -1;
            this.minY = -1;

            this.maxX = this.size;
            this.maxY = this.size;
        }

        public void setNext(T c) {
            values[x][y] = c;
            move();
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

        private T[][] create(int size) {
            int length = length(size);
            T[][] values = (T[][])new Object[length][];

            for(int i = 0; i < length; i++) {
                values[i] = (T[])new Object[length];
            }

            return values;
        }

        private static int length(int textLength) {
            int squareRoot = (int)Math.sqrt(textLength);
            int power = squareRoot * squareRoot;

            if(power < textLength) {
                return squareRoot + 1;
            }

            return squareRoot;
        }

        public Map<Integer, T> getMap() {
            Map<Integer, T> map = new HashMap<>();

            int index = 0;
            for(int x = 0; x < values.length; x++) {
                for(int y = 0; y < values[x].length; y++) {

                    T object = values[x][y];
                    map.put(index, object);

                    index++;
                }
            }

            return map;
        }
    }
}
