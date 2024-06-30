package decodetheqrcode;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;

/**
 * <a href="https://www.codewars.com/kata/5ef9c85dc41b4e000f9a645f/train/java">Decode the QR-Code</a>
 */
public class CodeWars {
    public static String scanner(int[][] qrcode) {
        QrCode qrCode = QrCode.of(qrcode);
        BitSequence bits = qrCode.readSequence();

        return bits.decodedWord();
    }
}

class QrCode {
    private final int[][] qrcode;

    private QrCode(int[][] qrcode) {
        this.qrcode = qrcode;
    }

    public BitSequence readSequence() {
        List<Integer> bits = Path.stream()
                .map(point -> point.read(this))
                .toList();

        return BitSequence.of(bits);
    }

    public Integer read(int x, int y) {
        return qrcode[y][x];
    }

    public static QrCode of(int[][] qrcode) {
        return new QrCode(qrcode);
    }
}

class BitSequence {
    private static final int ENCODED_BYTE_SIZE = 8;
    private static final BinaryOperator<Integer> byteToInteger = (acc, bit) -> acc * 2 + bit;

    private final List<Integer> sequence;

    private BitSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    public String decodedWord() {
        return word().chunks()
                .stream()
                .map(BitSequence::decodedChar)
                .map(String::valueOf)
                .collect(joining());
    }

    private BitSequence word() {
        Integer length = wordLength();
        var bits = sequence.stream()
                .skip(12)
                .limit(length * ENCODED_BYTE_SIZE)
                .toList();

        return BitSequence.of(bits);
    }

    private Integer wordLength() {
        return sequence.stream()
                .skip(4)
                .limit(ENCODED_BYTE_SIZE)
                .reduce(0, byteToInteger);
    }

    private List<BitSequence> chunks() {
        return Chunks.of(sequence)
                .ofSize(ENCODED_BYTE_SIZE)
                .map(BitSequence::of)
                .toList();
    }

    private Character decodedChar() {
        return (char) sequence.stream()
                .reduce(0, byteToInteger)
                .intValue();
    }


    public static BitSequence of(List<Integer> sequence) {
        return new BitSequence(sequence);
    }
}

record Point(int x, int y) {
    public Integer read(QrCode qrcode) {
        int value = qrcode.read(x, y);

        if ((x + y) % 2 == 0) {
            return 1 - value;
        }

        return value;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}

class Chunks<T> {
    private final List<T> items;

    private Chunks(List<T> items) {
        this.items = items;
    }

    public Stream<List<T>> ofSize(int size) {
        return range(0, items.size())
                .boxed()
                .collect(groupingBy(index -> index / size))
                .values()
                .stream()
                .map(indices -> indices
                        .stream()
                        .map(items::get)
                        .collect(toList()));
    }

    public static <T> Chunks<T> of(List<T> items) {
        return new Chunks<>(items);
    }
}

class Path {
    private static final List<Point> path = List.of(
            // Enc
            Point.of(20, 20), Point.of(19, 20),
            Point.of(20, 19), Point.of(19, 19),

            // Len
            Point.of(20, 18), Point.of(19, 18),
            Point.of(20, 17), Point.of(19, 17),
            Point.of(20, 16), Point.of(19, 16),
            Point.of(20, 15), Point.of(19, 15),

            // w
            Point.of(20, 14), Point.of(19, 14),
            Point.of(20, 13), Point.of(19, 13),
            Point.of(20, 12), Point.of(19, 12),
            Point.of(20, 11), Point.of(19, 11),

            // w
            Point.of(20, 10), Point.of(19, 10),
            Point.of(20, 9), Point.of(19, 9),
            Point.of(18, 9), Point.of(17, 9),
            Point.of(18, 10), Point.of(17, 10),

            // w
            Point.of(18, 11), Point.of(17, 11),
            Point.of(18, 12), Point.of(17, 12),
            Point.of(18, 13), Point.of(17, 13),
            Point.of(18, 14), Point.of(17, 14),

            // i
            Point.of(18, 15), Point.of(17, 15),
            Point.of(18, 16), Point.of(17, 16),
            Point.of(18, 17), Point.of(17, 17),
            Point.of(18, 18), Point.of(17, 18),

            // w
            Point.of(18, 19), Point.of(17, 19),
            Point.of(18, 20), Point.of(17, 20),
            Point.of(16, 20), Point.of(15, 20),
            Point.of(16, 19), Point.of(15, 19),

            // i
            Point.of(16, 18), Point.of(15, 18),
            Point.of(16, 17), Point.of(15, 17),
            Point.of(16, 16), Point.of(15, 16),
            Point.of(16, 15), Point.of(15, 15),

            // k
            Point.of(16, 14), Point.of(15, 14),
            Point.of(16, 13), Point.of(15, 13),
            Point.of(16, 12), Point.of(15, 12),
            Point.of(16, 11), Point.of(15, 11),

            // i
            Point.of(16, 10), Point.of(15, 10),
            Point.of(16, 9), Point.of(15, 9),
            Point.of(14, 9), Point.of(13, 9),
            Point.of(14, 10), Point.of(13, 10),

            // p
            Point.of(14, 11), Point.of(13, 11),
            Point.of(14, 12), Point.of(13, 12),
            Point.of(14, 13), Point.of(13, 13),
            Point.of(14, 14), Point.of(13, 14),

            // e
            Point.of(14, 15), Point.of(13, 15),
            Point.of(14, 16), Point.of(13, 16),
            Point.of(14, 17), Point.of(13, 17),
            Point.of(14, 18), Point.of(13, 18),

            // d
            Point.of(14, 19), Point.of(13, 19),
            Point.of(14, 20), Point.of(13, 20),
            Point.of(12, 20), Point.of(11, 20),
            Point.of(12, 19), Point.of(11, 19),

            // i
            Point.of(12, 18), Point.of(11, 18),
            Point.of(12, 17), Point.of(11, 17),
            Point.of(12, 16), Point.of(11, 16),
            Point.of(12, 15), Point.of(11, 15),

            // a
            Point.of(12, 14), Point.of(11, 14),
            Point.of(12, 13), Point.of(11, 13),
            Point.of(12, 12), Point.of(11, 12),
            Point.of(12, 11), Point.of(11, 11),

            // i
            Point.of(12, 10), Point.of(11, 10),
            Point.of(12, 9), Point.of(11, 9),
            Point.of(12, 8), Point.of(11, 8),
            Point.of(12, 7), Point.of(11, 7),

            // o
            Point.of(12, 5), Point.of(11, 5),
            Point.of(12, 4), Point.of(11, 4),
            Point.of(12, 3), Point.of(11, 3),
            Point.of(12, 2), Point.of(11, 2),

            // r
            Point.of(12, 1), Point.of(11, 1),
            Point.of(12, 0), Point.of(11, 0),
            Point.of(10, 0), Point.of(9, 0),
            Point.of(10, 1), Point.of(9, 1),

            // g
            Point.of(10, 2), Point.of(9, 2),
            Point.of(10, 3), Point.of(9, 3),
            Point.of(10, 4), Point.of(9, 4),
            Point.of(10, 5), Point.of(9, 5),

            // End
            Point.of(10, 7), Point.of(9, 7),
            Point.of(10, 8), Point.of(9, 8),

            // E1
            Point.of(10, 9), Point.of(9, 9),
            Point.of(10, 10), Point.of(9, 10),
            Point.of(10, 11), Point.of(9, 11),
            Point.of(10, 12), Point.of(9, 12),

            // E2
            Point.of(10, 13), Point.of(9, 13),
            Point.of(10, 14), Point.of(9, 14),
            Point.of(10, 15), Point.of(9, 15),
            Point.of(10, 16), Point.of(9, 16),

            // E3
            Point.of(10, 17), Point.of(9, 17),
            Point.of(10, 18), Point.of(9, 18),
            Point.of(10, 19), Point.of(9, 19),
            Point.of(10, 20), Point.of(9, 20),

            // E4
            Point.of(8, 12), Point.of(7, 12),
            Point.of(8, 11), Point.of(7, 11),
            Point.of(8, 10), Point.of(7, 10),
            Point.of(8, 9), Point.of(7, 9),

            // E5
            Point.of(5, 9), Point.of(4, 9),
            Point.of(5, 10), Point.of(4, 10),
            Point.of(5, 11), Point.of(4, 11),
            Point.of(5, 12), Point.of(4, 12),

            // E6
            Point.of(3, 12), Point.of(2, 12),
            Point.of(3, 11), Point.of(2, 11),
            Point.of(3, 10), Point.of(2, 10),
            Point.of(3, 9), Point.of(2, 9),

            // E7
            Point.of(1, 9), Point.of(0, 9),
            Point.of(1, 10), Point.of(0, 10),
            Point.of(1, 11), Point.of(0, 11),
            Point.of(1, 12), Point.of(0, 12)
    );

    public static Stream<Point> stream() {
        return path.stream();
    }
}
