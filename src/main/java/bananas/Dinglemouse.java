package bananas;

import com.google.common.collect.Streams;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/5917fbed9f4056205a00001e/train/java">Bananas</a>
 */
public class Dinglemouse {

    static Set<String> bananas(final String s) {
        return search("banana", s);
    }

    private static Set<String> search(String search, String text) {
        if (search.isEmpty()) {
            return maskRemaining(text);
        }

        if (text.isEmpty()) {
            return Set.of();
        }

        if (headOf(search) == headOf(text)) {
            return takeAndOmit(search, text);
        }

        return omitLetter(search, text)
                .collect(Collectors.toSet());
    }

    private static Set<String> maskRemaining(String text) {
        String masked = text.chars()
                .map(c -> '-')
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();

        return Set.of(masked);
    }

    private static Set<String> takeAndOmit(String search, String text) {
        Stream<String> taken = takeLetter(search, text);
        Stream<String> omitted = omitLetter(search, text);

        return Streams.concat(taken, omitted)
                .collect(Collectors.toSet());
    }


    private static Stream<String> takeLetter(String search, String text) {
        return search(tailOf(search), tailOf(text))
                .stream()
                .map(remainingLetters -> headOf(search) + remainingLetters);
    }

    private static Stream<String> omitLetter(String search, String text) {
        return search(search, tailOf(text))
                .stream()
                .map(remainingLetters -> '-' + remainingLetters);
    }

    private static char headOf(String search) {
        return search.charAt(0);
    }

    private static String tailOf(String text) {
        return text.substring(1);
    }
}
