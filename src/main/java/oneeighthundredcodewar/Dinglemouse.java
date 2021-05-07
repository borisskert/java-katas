package oneeighthundredcodewar;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/5a3267b2ee1aaead3d000037/train/java
 */
public class Dinglemouse {
    private static final Pattern numberPattern = Pattern.compile("^1-800-([A-Z]+)-([A-Z]+)$");
    private static final PhoneButtons phoneButtons;
    private static final AllowedWords allowedWords;

    static {
        phoneButtons = PhoneButtons.create();
        allowedWords = AllowedWords.create();
    }

    public static Set<String> check1800(final String str) {
        PhoneWord phoneWord = new PhoneWord(str);
        Number phoneWordNumber = phoneWord.toNumber();

        HashSet<String> phoneNumbers = new HashSet<>();

        phoneWordNumber.possibleFirstWords().forEach(firstWord ->
                phoneWordNumber.secondNumber(firstWord)
                        .toWords()
                        .forEach(secondWord ->
                                phoneNumbers.add(
                                        formatPhoneWord(firstWord, secondWord)
                                )
                        ));

        return phoneNumbers;
    }

    private static String formatPhoneWord(Word firstWord, Word secondWord) {
        return "1-800-" + firstWord.text + "-" + secondWord.text;
    }

    private static class PhoneWord {
        private final List<String> words;

        PhoneWord(String text) {
            this.words = readWords(text);
        }

        Word firstWord() {
            return new Word(words.get(0));
        }

        Word secondWord() {
            return new Word(words.get(1));
        }

        Number toNumber() {
            String firstNumber = firstWord().toNumber().text;
            String secondNumber = secondWord().toNumber().text;

            return new Number(firstNumber + secondNumber);
        }

        private static List<String> readWords(final String str) {
            List<String> words = new ArrayList<>();

            Matcher matcher = numberPattern.matcher(str);

            if (matcher.matches()) {
                for (int index = 1; index <= matcher.groupCount(); index++) {
                    words.add(matcher.group(index));
                }
            }

            return words;
        }
    }

    private static class Number {
        private final String text;

        Number(String text) {
            this.text = text;
        }

        Set<Word> toWords() {
            return allowedWords.getWords(text);
        }

        Set<Word> possibleFirstWords() {
            return allowedWords.possibleFirstWords(text);
        }

        Number secondNumber(Word firstWord) {
            String firstDigits = firstWord.toNumber().text;

            if (this.text.startsWith(firstDigits)) {
                String anotherDigits = this.text.substring(firstDigits.length());
                return new Number(anotherDigits);
            }

            throw new RuntimeException();
        }
    }

    private static class Word {
        private final String text;

        Word(String text) {
            this.text = text;
        }

        Number toNumber() {
            return new Number(phoneButtons.toNumber(text));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word = (Word) o;
            return Objects.equals(text, word.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }

    private static class AllowedWords {
        private final Map<String, String> dictionary;

        private AllowedWords(Map<String, String> dictionary) {
            this.dictionary = dictionary;
        }

        Set<Word> getWords(final String number) {
            return dictionary.entrySet().stream()
                    .filter(e -> e.getValue().equals(number))
                    .map(Map.Entry::getKey)
                    .map(Word::new)
                    .collect(Collectors.toUnmodifiableSet());
        }

        Set<Word> possibleFirstWords(String word) {
            return dictionary.entrySet()
                    .stream()
                    .filter(e -> word.startsWith(e.getValue()))
                    .map(Map.Entry::getKey)
                    .map(Word::new)
                    .collect(Collectors.toUnmodifiableSet());
        }

        static AllowedWords create() {
            return new AllowedWords(createAllowedWordsDictionary());
        }

        private static Map<String, String> createAllowedWordsDictionary() {
            Map<String, String> map = new HashMap<>();

            for (int index = 0; index < Preloaded.WORDS.length; index++) {
                String word = Preloaded.WORDS[index];
                String number = phoneButtons.toNumber(word);

                map.put(word, number);
            }

            return map;
        }
    }

    private static class PhoneButtons {
        private final Map<Character, Character> map;

        private PhoneButtons(Map<Character, Character> map) {
            this.map = map;
        }

        String toNumber(String word) {
            return word.chars()
                    .mapToObj(i -> map.get((char) i))
                    .collect(
                            Collector.of(
                                    StringBuilder::new,
                                    StringBuilder::append,
                                    StringBuilder::append,
                                    StringBuilder::toString
                            )
                    );
        }

        static PhoneButtons create() {
            return new PhoneButtons(createPhoneButtonMapping());
        }

        private static Map<Character, Character> createPhoneButtonMapping() {
            Map<Character, Character> map = new HashMap<>();

            map.put('A', '2');
            map.put('B', '2');
            map.put('C', '2');

            map.put('D', '3');
            map.put('E', '3');
            map.put('F', '3');

            map.put('G', '4');
            map.put('H', '4');
            map.put('I', '4');

            map.put('J', '5');
            map.put('K', '5');
            map.put('L', '5');

            map.put('M', '6');
            map.put('N', '6');
            map.put('O', '6');

            map.put('P', '7');
            map.put('Q', '7');
            map.put('R', '7');
            map.put('S', '7');

            map.put('T', '8');
            map.put('U', '8');
            map.put('V', '8');

            map.put('W', '9');
            map.put('X', '9');
            map.put('Y', '9');
            map.put('Z', '9');

            return map;
        }
    }
}
