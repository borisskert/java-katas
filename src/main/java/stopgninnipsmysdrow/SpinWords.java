package stopgninnipsmysdrow;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/5264d2b162488dc400000001/train/java
 */
public class SpinWords {

    public String spinWords(String sentence) {
        return Words.words(sentence).map(word -> {
            if (word.hasToBeReversed()) {
                return word.reverse();
            }
            return word;
        }).unwords();
    }

    private static class Words {
        private final List<Word> words;

        private Words(List<Word> words) {
            this.words = words;
        }

        public String unwords() {
            StringJoiner bySpaces = new StringJoiner(" ");

            words.stream().map(Word::word).forEach(bySpaces::add);

            return bySpaces.toString();
        }

        public Words map(Function<Word, Word> map) {
            List<Word> mappedWords = words.stream()
                    .map(map)
                    .collect(Collectors.toList());

            return new Words(mappedWords);
        }

        public static Words words(String sentence) {
            String[] words = sentence.split("[ ]+");

            List<Word> collectedWords = Arrays.stream(words)
                    .map(Word::new)
                    .collect(Collectors.toList());

            return new Words(collectedWords);
        }
    }

    private static class Word {
        private final String word;

        private Word(String word) {
            this.word = word;
        }

        public boolean hasToBeReversed() {
            return word.length() >= 5;
        }

        public Word reverse() {
            StringBuffer reversed = new StringBuffer(word).reverse();
            return new Word(reversed.toString());
        }

        public String word() {
            return word;
        }
    }
}
