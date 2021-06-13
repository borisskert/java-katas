package stringsmix;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * https://www.codewars.com/kata/5629db57620258aa9d000014/train/java
 */
public class Mixing {

    public static String mix(String s1, String s2) {
        Letters letters = Letters.empty();

        for (char letter : s1.toCharArray()) {
            letters.addLetterForStringOne(letter);
        }

        for (char letter : s2.toCharArray()) {
            letters.addLetterForStringTwo(letter);
        }

        return letters.toString();
    }
}

class Letters {
    private final Map<Character, Letter> letters;

    private Letters(Map<Character, Letter> letters) {
        this.letters = letters;
    }

    void addLetterForStringOne(char letterAsChar) {
        Letter letter = letters.getOrDefault(letterAsChar, Letter.of(letterAsChar));
        letter.increaseForStringOne();

        letters.put(letterAsChar, letter);
    }

    void addLetterForStringTwo(char letterAsChar) {
        Letter letter = letters.getOrDefault(letterAsChar, Letter.of(letterAsChar));
        letter.increaseForStringTwo();

        letters.put(letterAsChar, letter);
    }

    @Override
    public String toString() {
        return letters.values()
                .stream()
                .filter(Letter::isRelevant)
                .sorted()
                .map(Letter::toString)
                .collect(
                        () -> new StringJoiner("/"),
                        StringJoiner::add,
                        StringJoiner::merge)
                .toString();
    }

    static Letters empty() {
        return new Letters(new HashMap<>());
    }
}

class Letter implements Comparable<Letter> {
    private static final int LOWER_CASE_A_ASCII = 97;
    private static final int LOWER_CASE_Z_ASCII = 122;

    private final char letter;
    private int countOne = 0;
    private int countTwo = 0;

    private Letter(char letter) {
        this.letter = letter;
    }

    void increaseForStringOne() {
        countOne++;
    }

    void increaseForStringTwo() {
        countTwo++;
    }

    boolean isRelevant() {
        return count() > 1
                && LOWER_CASE_A_ASCII <= letter
                && letter <= LOWER_CASE_Z_ASCII;
    }

    private int count() {
        return Math.max(countOne, countTwo);
    }

    private int stringNr() {
        if (countOne > countTwo) {
            return 1;
        } else if (countTwo > countOne) {
            return 2;
        }

        return 3;
    }

    static Letter of(char letter) {
        return new Letter(letter);
    }

    @Override
    public String toString() {
        if (countOne > countTwo) {
            return "1:" + String.valueOf(letter).repeat(countOne);
        } else if (countTwo > countOne) {
            return "2:" + String.valueOf(letter).repeat(countTwo);
        }

        return "=:" + String.valueOf(letter).repeat(countOne);
    }

    @Override
    public int compareTo(Letter o) {
        int count = Integer.compare(o.count(), count());
        if (count != 0) {
            return count;
        }

        int stringNr = Integer.compare(stringNr(), o.stringNr());
        if (stringNr != 0) {
            return stringNr;
        }

        return Character.compare(letter, o.letter);
    }
}
