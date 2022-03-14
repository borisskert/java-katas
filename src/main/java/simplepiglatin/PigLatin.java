package simplepiglatin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/520b9d2ad5c005041100000f/train/java
 */
public class PigLatin {
    private static final Function<Word, Word> toPigLatin = word -> {
        if (word.isPunctuation()) {
            return word;
        }

        char head = word.head();
        String tail = word.tail();

        return Word.from(tail + head + "ay");
    };

    public static String pigIt(String str) {
        return Words.of(str)
                .map(toPigLatin)
                .unwords();
    }
}

class Words {
    private final List<Word> words;

    private Words(List<Word> words) {
        this.words = words;
    }

    public Words map(Function<? super Word, ? extends Word> mapper) {
        List<Word> mapped = words.stream()
                .map(mapper)
                .collect(Collectors.toUnmodifiableList());

        return new Words(mapped);
    }

    public String unwords() {
        return words.stream()
                .map(Word::value)
                .collect(Collectors.joining(" "));
    }

    public static Words of(String sentence) {
        String[] splitSentence = sentence.split("[ ]+");
        List<Word> collect = Arrays.stream(splitSentence)
                .map(Word::from)
                .collect(Collectors.toUnmodifiableList());

        return new Words(collect);
    }
}

class Word {
    private static final Pattern WORD_PATTERN = Pattern.compile("^[A-Za-z]+$");
    private final String value;

    private Word(String value) {
        this.value = value;
    }

    public char head() {
        return value.charAt(0);
    }

    public String tail() {
        return value.substring(1);
    }

    public boolean isPunctuation() {
        return !WORD_PATTERN.matcher(value)
                .matches();
    }

    public String value() {
        return value;
    }

    public static Word from(String str) {
        return new Word(str);
    }
}
