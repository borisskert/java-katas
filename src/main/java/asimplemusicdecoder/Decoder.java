package asimplemusicdecoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/58de42bab4b74c214d0000e2/train/java">A Simple Music Decoder</a>
 */
public class Decoder {
    public int[] uncompress(final String music) {
        return CompressedStream.from(music)
                .uncompress()
                .toArray();
    }
}

class CompressedStream {
    private final List<String> values;

    private CompressedStream(List<String> values) {
        this.values = values;
    }

    public UncompressedStream uncompress() {
        Stream<Integer> uncompressed = values.stream()
                .map(CompressedValues::parse)
                .flatMap(CompressedValues::uncompress);

        return UncompressedStream.of(uncompressed.toList());
    }

    public static CompressedStream from(String music) {
        List<String> values = Arrays.stream(music.split(","))
                .toList();

        return new CompressedStream(values);
    }
}

class UncompressedStream {
    private final List<Integer> values;

    private UncompressedStream(List<Integer> values) {
        this.values = values;
    }

    public static UncompressedStream of(List<Integer> values) {
        return new UncompressedStream(values);
    }

    public int[] toArray() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}

interface CompressedValues {
    Stream<Integer> uncompress();

    static CompressedValues parse(String compressedValues) {
        return SameInterval.tryParse(compressedValues)
                .or(() -> Identical.tryParse(compressedValues))
                .or(() -> Simple.tryParse(compressedValues))
                .orElseThrow();
    }
}

class Identical implements CompressedValues {
    private static final Pattern IDENTICAL_PATTERN = Pattern.compile("(-?\\d+)\\*(\\d+)");

    private final int value;
    private final int count;

    private Identical(int value, int count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public Stream<Integer> uncompress() {
        return IntStream.iterate(
                        value,
                        operand -> operand
                )
                .limit(count)
                .boxed();
    }

    @Override
    public String toString() {
        return "Identical{" +
                "value=" + value +
                ", count=" + count +
                '}';
    }

    public static Optional<CompressedValues> tryParse(String compressedValue) {
        final Matcher matcher = IDENTICAL_PATTERN.matcher(compressedValue);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1));
            int count = Integer.parseInt(matcher.group(2));

            return Optional.of(new Identical(value, count));
        }

        return Optional.empty();
    }
}

class SameInterval implements CompressedValues {
    private static final Pattern SAME_INTERVAL_PATTERN = Pattern.compile("(-?\\d+)-(-?\\d+)(/(\\d+))?");

    private final int start;
    private final int end;
    private final int step;


    private SameInterval(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public Stream<Integer> uncompress() {
        int min = Math.min(start, end);
        int max = Math.max(start, end);

        return IntStream.iterate(
                start,
                value -> value >= min && value <= max,
                value -> value + step
        ).boxed();
    }

    @Override
    public String toString() {
        return "SameInterval{" +
                "start=" + start +
                ", end=" + end +
                ", step=" + step +
                '}';
    }

    public static Optional<CompressedValues> tryParse(String compressedValue) {
        final Matcher matcher = SAME_INTERVAL_PATTERN.matcher(compressedValue);

        if (matcher.matches()) {
            int start = Integer.parseInt(matcher.group(1));
            int end = Integer.parseInt(matcher.group(2));
            int step = Optional.ofNullable(matcher.group(4))
                    .map(Integer::parseInt)
                    .orElse(1) * Integer.signum(end - start);

            return Optional.of(new SameInterval(start, end, step));
        }

        return Optional.empty();
    }
}

class Simple implements CompressedValues {
    private static final Pattern SIMPLE_PATTERN = Pattern.compile("(-?\\d+)");

    private final Integer value;

    private Simple(Integer value) {
        this.value = value;
    }

    @Override
    public Stream<Integer> uncompress() {
        return Stream.of(value);
    }

    @Override
    public String toString() {
        return "Simple{" +
                value +
                '}';
    }

    public static Optional<CompressedValues> tryParse(String compressedValue) {
        final Matcher matcher = SIMPLE_PATTERN.matcher(compressedValue);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1));
            return Optional.of(new Simple(value));
        }

        return Optional.empty();
    }
}
