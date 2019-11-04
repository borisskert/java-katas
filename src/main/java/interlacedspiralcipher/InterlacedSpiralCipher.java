package interlacedspiralcipher;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.codewars.com/kata/interlaced-spiral-cipher/java
 */
public class InterlacedSpiralCipher {

    public static String encode(String s) {
        TwoWayHashMap<Integer, Integer> map = createSpiralCipherMap(s.length());

        char[] result = new char[map.size()];

        for (int index = 0; index < map.size(); index++) {
            Integer encodeIndex = map.getForward(index);

            if (encodeIndex >= s.length()) {
                result[index] = ' ';
            } else {
                result[index] = s.charAt(encodeIndex);
            }
        }

        return String.valueOf(result);
    }

    public static String decode(String s) {
        TwoWayHashMap<Integer, Integer> map = createSpiralCipherMap(s.length());

        char[] result = new char[map.size()];

        for (int index = 0; index < s.length(); index++) {
            Integer decodeIndex = map.getBackward(index);
            result[index] = s.charAt(decodeIndex);
        }

        return rightTrim(String.valueOf(result));
    }

    private static String rightTrim(String s) {
        int i = s.length() - 1;

        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }

        return s.substring(0, i + 1);
    }

    private static TwoWayHashMap<Integer, Integer> createSpiralCipherMap(int textLength) {
        int cipherLength = length(textLength);
        int[] spiral = new int[cipherLength];

        int round = 0;
        int offset = 0;
        int step = 0;
        int index = 0;
        int size = (int) Math.sqrt(cipherLength) - 1;

        for (; index < cipherLength; index++) {
            if (round >= 4) {
                round = 0;
                step++;
            }

            if (step >= size) {
                offset += size * 4;
                step = 0;
                size -= 2;
            }

            int position = offset + step + size * round;
            spiral[position] = index;

            round++;
        }

        Snail snail = new Snail(textLength);
        for (index = 0; index < spiral.length; index++) {
            snail.setNext(spiral[index]);
        }

        return snail.getMap();
    }

    private static int length(int textLength) {
        int squareRoot = (int) Math.sqrt(textLength);
        int power = squareRoot * squareRoot;

        if (power < textLength) {
            return (squareRoot + 1) * (squareRoot + 1);
        }

        return power;
    }

    /**
     * Snail to navigate through the 2-dim array
     */
    private static class Snail<T> {
        private final T[][] values;
        private Direction direction = Direction.E;

        private int maxX;
        private int maxY;
        private int minX;
        private int minY;

        private int x = 0;
        private int y = 0;

        private Snail(int textSize) {
            int size = snailSize(textSize);
            this.values = create(textSize);

            this.minX = -1;
            this.minY = -1;

            this.maxX = size;
            this.maxY = size;
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
            int length = snailSize(size);
            T[][] values = (T[][]) new Object[length][];

            for (int i = 0; i < length; i++) {
                values[i] = (T[]) new Object[length];
            }

            return values;
        }

        private static int snailSize(int textLength) {
            int squareRoot = (int) Math.sqrt(textLength);
            int power = squareRoot * squareRoot;

            if (power < textLength) {
                return squareRoot + 1;
            }

            return squareRoot;
        }

        public TwoWayHashMap<Integer, T> getMap() {
            TwoWayHashMap<Integer, T> map = new TwoWayHashMap<>();

            int index = 0;
            for (int x = 0; x < values.length; x++) {
                for (int y = 0; y < values[x].length; y++) {

                    T object = values[x][y];
                    map.add(index, object);

                    index++;
                }
            }

            return map;
        }
    }

    /**
     * Two-way map to get value for key and key for value
     */
    public static class TwoWayHashMap<K extends Object, V extends Object> {

        private Map<K, V> forward = new HashMap<>();
        private Map<V, K> backward = new HashMap<>();

        public synchronized void add(K key, V value) {
            forward.put(key, value);
            backward.put(value, key);
        }

        public synchronized V getForward(K key) {
            return forward.get(key);
        }

        public synchronized K getBackward(V key) {
            return backward.get(key);
        }

        public int size() {
            return forward.size();
        }
    }
}
