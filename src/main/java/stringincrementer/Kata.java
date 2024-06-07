package stringincrementer;

import java.math.BigInteger;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="https://www.codewars.com/kata/54a91a4883a7de5d7800009c/train/java">String incrementer</a>
 */
public class Kata {

    public static String incrementString(String str) {
        return TextAndCounter.parse(str)
                .increment()
                .formatted();
    }
}

class TextAndCounter {
    private static final Pattern PATTERN = Pattern.compile("(?<text>.+?)(?<counter>[0-9]+)$");

    private final String text;
    private final String format;
    private final BigInteger counter;

    private TextAndCounter(String text, String format, BigInteger counter) {
        this.text = text;
        this.format = format;
        this.counter = counter;
    }

    public TextAndCounter increment() {
        return new TextAndCounter(text, format, counter.add(BigInteger.ONE));
    }

    public String formatted() {
        return text + String.format(format, counter);
    }

    public static TextAndCounter parse(String str) {
        return tryParseNumberOnly(str)
                .orElseGet(() -> parseTextAndCounter(str));
    }
    
    private static Optional<TextAndCounter> tryParseNumberOnly(String str) {
        return Integers.tryParse(str)
                .map(counter -> {
                    final var format = "%0" + str.length() + "d";
                    return new TextAndCounter("", format, counter);
                });
    }

    private static TextAndCounter parseTextAndCounter(String str) {
        Matcher matcher = PATTERN.matcher(str);

        if (matcher.matches()) {
            String counterAsString = matcher.group("counter");
            String text = Optional.ofNullable(matcher.group("text"))
                    .orElse("");

            String format = "%0" + Math.max(counterAsString.length(), 1) + "d";

            BigInteger counter = Integers.tryParse(counterAsString)
                    .orElse(BigInteger.ZERO);

            return new TextAndCounter(text, format, counter);
        }

        return new TextAndCounter(str, "%d", BigInteger.ZERO);
    }
}

class Integers {
    public static Optional<BigInteger> tryParse(String str) {
        try {
            return Optional.of(new BigInteger(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
