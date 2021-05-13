package decodethemorsecodeadvanced;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * https://www.codewars.com/kata/54b72c16cd7f5154e9000457/train/java
 */
public class MorseCodeDecoder {
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("^(0)*((1)+(((0000000)|(000)|(0))(1)+)*)(0)*$");
    private static final Pattern WORD_PATTERN = Pattern.compile("(0000000)*((1)+(((000)|(0))(1)+)*)(0000000)*");
    private static final Pattern LETTER_PATTERN = Pattern.compile("(000)*((1)+((0)(1)+)*)(000)*");
    private static final Pattern BIT_PATTERN = Pattern.compile("(0)*((1)+)(0)*");

    private static final List<Rule> translationRules = new LinkedList<>();

    static {
        translationRules.add(Rule.of("·−·−·−", "."));
        translationRules.add(Rule.of("−·−·−−", "!"));
        
        translationRules.add(Rule.of("·−−−−", "1"));
        translationRules.add(Rule.of("··−−−", "2"));
        translationRules.add(Rule.of("···−−", "3"));
        translationRules.add(Rule.of("····−", "4"));
        translationRules.add(Rule.of("·····", "5"));
        translationRules.add(Rule.of("−····", "6"));
        translationRules.add(Rule.of("−−···", "7"));
        translationRules.add(Rule.of("−−−··", "8"));
        translationRules.add(Rule.of("−−−−·", "9"));
        translationRules.add(Rule.of("−−−−−", "0"));

        translationRules.add(Rule.of("−···", "B"));
        translationRules.add(Rule.of("−·−·", "C"));
        translationRules.add(Rule.of("··−·", "F"));
        translationRules.add(Rule.of("····", "H"));
        translationRules.add(Rule.of("·−−−", "J"));
        translationRules.add(Rule.of("·−··", "L"));
        translationRules.add(Rule.of("·−−·", "P"));
        translationRules.add(Rule.of("−−·−", "Q"));
        translationRules.add(Rule.of("···−", "V"));
        translationRules.add(Rule.of("−··−", "X"));
        translationRules.add(Rule.of("−·−−", "Y"));
        translationRules.add(Rule.of("−−··", "Z"));

        translationRules.add(Rule.of("−··", "D"));
        translationRules.add(Rule.of("−−·", "G"));
        translationRules.add(Rule.of("−·−", "K"));
        translationRules.add(Rule.of("−−−", "O"));
        translationRules.add(Rule.of("·−·", "R"));
        translationRules.add(Rule.of("···", "S"));
        translationRules.add(Rule.of("··−", "U"));
        translationRules.add(Rule.of("·−−", "W"));
        translationRules.add(Rule.of("   ", "_"));

        translationRules.add(Rule.of("·−", "A"));
        translationRules.add(Rule.of("··", "I"));
        translationRules.add(Rule.of("−−", "M"));
        translationRules.add(Rule.of("−·", "N"));

        translationRules.add(Rule.of("·", "E"));
        translationRules.add(Rule.of("−", "T"));

        translationRules.add(Rule.of(" ", ""));
        translationRules.add(Rule.of("_", " "));
    }

    public static String decodeBits(String bits) {
        String normalizedBits = Bits.read(bits).normalized();
        return ParsedSentence.from(normalizedBits).toString();
    }

    public static String decodeMorse(String morseCode) {
        String decoded = morseCode;

        for (Rule rule : translationRules) {
            decoded = decoded.replaceAll(rule.morse, rule.ascii);
        }

        return decoded;
    }

    static class ParsedSentence {
        private final String sentence;

        private ParsedSentence(String sentence) {
            this.sentence = sentence;
        }

        @Override
        public String toString() {
            return ParsedWord.from(sentence)
                    .map(ParsedWord::toString)
                    .collect(Collectors.joining("   "));
        }

        static ParsedSentence from(String bits) {
            Matcher matcher = SENTENCE_PATTERN.matcher(bits);

            StringBuilder builder = new StringBuilder();

            while (matcher.find()) {
                builder.append(matcher.group(2));
            }

            return new ParsedSentence(builder.toString());
        }
    }

    static class ParsedWord {
        private final String word;

        private ParsedWord(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return ParsedLetter.from(word)
                    .map(ParsedLetter::toString)
                    .collect(Collectors.joining(" "));
        }

        static Stream<ParsedWord> from(String bits) {
            Matcher matcher = WORD_PATTERN.matcher(bits);

            Iterator<ParsedWord> iterator = new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return matcher.find();
                }

                @Override
                public ParsedWord next() {
                    return new ParsedWord(matcher.group(2));
                }
            };

            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                    false);
        }
    }

    static class ParsedLetter {
        private final String letter;

        private ParsedLetter(String letter) {
            this.letter = letter;
        }

        @Override
        public String toString() {
            return ParsedBit.from(letter)
                    .map(ParsedBit::toString)
                    .collect(Collectors.joining());
        }

        static Stream<ParsedLetter> from(String wordBits) {
            Matcher matcher = LETTER_PATTERN.matcher(wordBits);

            Iterator<ParsedLetter> iterator = new Iterator<>() {

                @Override
                public boolean hasNext() {
                    return matcher.find();
                }

                @Override
                public ParsedLetter next() {
                    return new ParsedLetter(matcher.group(2));
                }
            };

            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                    false);
        }
    }

    static class ParsedBit {
        private final String bit;

        private ParsedBit(String bit) {
            this.bit = bit;
        }

        @Override
        public String toString() {
            if (bit.equals("111")) {
                return "−";
            }

            if (bit.equals("1")) {
                return "·";
            }

            throw new RuntimeException("Illegal bits: " + bit);
        }

        static Stream<ParsedBit> from(String letterBits) {
            Matcher matcher = BIT_PATTERN.matcher(letterBits);

            Iterator<ParsedBit> iterator = new Iterator<>() {

                @Override
                public boolean hasNext() {
                    return matcher.find();
                }

                @Override
                public ParsedBit next() {
                    return new ParsedBit(matcher.group(2));
                }
            };

            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                    false);
        }
    }

    private static class Bits {
        private final String bits;

        private Bits(String bits) {
            this.bits = bits;
        }

        String normalized() {
            int bitLength = determineBitLength(bits);
            String normalizedBits = bits;

            if(bitLength > 1) {
                normalizedBits = normalizeBits(bits, bitLength);
            }

            return normalizedBits;
        }

        static Bits read(String bits) {
            Matcher matcher = SENTENCE_PATTERN.matcher(bits);
            if(matcher.find()) {
                return new Bits(matcher.group(2));
            }

            return new Bits(bits);
        }

        private static String normalizeBits(String bits, int bitLength) {
            return bits
                    .replaceAll("[0]{" + bitLength + "}", "0")
                    .replaceAll("[1]{" + bitLength + "}", "1");
        }

        private static int determineBitLength(String bits) {
            if(bits.length() < 2) {
                return 1;
            }

            int minLength = Integer.MAX_VALUE;
            int currentLength = 0;
            Character lastChar = null;

            for(char index = 0; index < bits.length(); index++) {
                char currentChar = bits.charAt(index);

                if(lastChar == null) {
                    lastChar = currentChar;
                    currentLength = 1;
                } else if(lastChar == currentChar) {
                    currentLength++;
                } else {
                    lastChar = currentChar;
                    minLength = Math.min(minLength, currentLength);
                    currentLength = 1;
                }
            }

            return Math.min(minLength, currentLength);
        }
    }

    private static class Rule {
        final String morse;
        final String ascii;

        private Rule(String morse, String ascii) {
            this.morse = morse;
            this.ascii = ascii;
        }

        static Rule of(String morse, String ascii) {
            return new Rule(morse, ascii);
        }
    }
}
