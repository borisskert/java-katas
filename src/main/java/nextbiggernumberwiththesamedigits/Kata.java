package nextbiggernumberwiththesamedigits;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * https://www.codewars.com/kata/55983863da40caa2c900004e
 */
public class Kata {

    public static long nextBiggerNumber(long n) {
        long nextBiggerNumber = Number.of(n)
                .nextBigger()
                .toLong();

        if(nextBiggerNumber == n) {
            return -1;
        }

        return nextBiggerNumber;
    }
}

class Number {
    private final String number;

    private Number(String number) {
        this.number = number;
    }

    int digitAt(int index) {
        String lastChar = number.substring(index, index + 1);
        return Integer.parseInt(lastChar);
    }

    long toLong() {
        return Long.parseLong(number);
    }

    int lastIndex() {
        return number.length() - 1;
    }

    int length() {
        return number.length();
    }

    Number digitsAt(int beginIndex, int endIndex) {
        String substring = number.substring(beginIndex, endIndex);
        return new Number(substring);
    }

    Number withDigitAt(int index, int digit) {
        String newNumber = number.substring(0, index) + digit + number.substring(index + 1);
        return new Number(newNumber);
    }

    Number lastDigitsIncluding(int index) {
        int length = number.length();
        return digitsAt(index, length);
    }

    Number sortDescending() {
        String sorted = number.chars()
                .sorted()
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                ).toString();

        return new Number(sorted);
    }

    Number append(Number n) {
        return new Number(number + n.number);
    }

    Number append(int digit) {
        return new Number(number + digit);
    }

    Number firstDigitsUntil(int index) {
        return digitsAt(0, index);
    }

    Number nextBigger() {
        for(Digit digit : this.reverseDigits()) {
            if(digit.index() == this.lastIndex()) {
                continue;
            }

            int nextIndex = digit.index() + 1;

            if(digit.value() < this.digitAt(nextIndex)) {
                Number staticPart = this.firstDigitsUntil(digit.index());
                Number secondPart = this.lastDigitsIncluding(nextIndex).sortDescending();

                for(int index = 0; index < secondPart.length(); index++) {
                    int digitToBeSwitchedWith = secondPart.digitAt(index);

                    if(digitToBeSwitchedWith > digit.value()) {
                        return staticPart
                                .append(digitToBeSwitchedWith)
                                .append(secondPart.withDigitAt(index, digit.value()));
                    }
                }
            }
        }

        return this;
    }

    Collection<Digit> reverseDigits() {
        List<Digit> collection = new LinkedList<>();

        for (int index = number.length() - 1; index >= 0; index--) {
            int value = digitAt(index);
            collection.add(new Digit(value, index));
        }

        return collection;
    }

    static Number of(long n) {
        return new Number(Long.valueOf(n).toString());
    }
}

class Digit {
    private final int value;
    private final int index;

    Digit(int value, int index) {
        this.value = value;
        this.index = index;
    }

    int value() {
        return value;
    }

    int index() {
        return index;
    }
}
