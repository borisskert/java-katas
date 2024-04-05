package firstnonrepeatingcharacter;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * <a href="https://www.codewars.com/kata/52bc74d4ac05d0945d00054e/train/java">First non-repeating character</a>
 */
public class Kata {
    public static String firstNonRepeatingLetter(String text) {
        var isUniqueIgnoreCase = isUniqueIgnoreCaseWithin(text);

        Optional<Character> found = text
                .chars()
                .mapToObj(Kata::intToChar)
                .filter(isUniqueIgnoreCase)
                .findFirst();

        if (found.isPresent()) {
            return String.valueOf(found.get());
        }

        return "";
    }

    private static Predicate<Character> isUniqueIgnoreCaseWithin(String text) {
        final String lowercasedText = text.toLowerCase();

        return character -> {
            final char lowercasedCharacter = Character.toLowerCase(character);

            final int firstIndex = lowercasedText.indexOf(lowercasedCharacter);
            final int lastIndex = lowercasedText.lastIndexOf(lowercasedCharacter);

            return firstIndex == lastIndex;
        };
    }

    private static Character intToChar(int c) {
        return (char) c;
    }
}
