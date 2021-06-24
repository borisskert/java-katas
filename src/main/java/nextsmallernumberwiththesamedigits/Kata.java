package nextsmallernumberwiththesamedigits;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.codewars.com/kata/5659c6d896bc135c4c00021e/train/java
 */
public class Kata {
    public static long nextSmaller(long n) {
        long nextSmallerNumber = Number.of(n)
                .nextSmallerNumber()
                .toLong();

        if(nextSmallerNumber == n) {
            return -1;
        }

        return nextSmallerNumber;
    }
}

class Number {
    private final String number;

    private Number(String number) {
        this.number = number;
    }

    Number nextSmallerNumber() {
        for(int index = number.length() - 1; index >= 1; index--) {
            int otherIndex = index - 1;
            long otherDigit = digitAt(otherIndex);
            long digit = digitAt(index);

            if(otherDigit > digit) {
                Number starting = starting(otherIndex);
                Number sorted = sub(index).sorted(Comparator.reverseOrder());
                Number ending = starting.concat(otherDigit).concat(sorted);

                while(otherDigit <= ending.digitAt(index) && index < number.length()) {
                    index++;
                }

                Number nextSmallerNumber = ending.swap(otherIndex, index);

                if(nextSmallerNumber.isGreaterThan(this) || nextSmallerNumber.digitAt(0) == 0) {
                    return this;
                }

                return nextSmallerNumber;
            }
        }

        return this;
    }

    private Number concat(long otherDigit) {
        return this.concat(Number.of(otherDigit));
    }

    private boolean isGreaterThan(Number other) {
        return this.toLong() > other.toLong();
    }

    private Number starting(int length) {
        String newNumber = number.substring(0, length);
        return new Number(newNumber);
    }

    Number sub(int beginIndex) {
        String newNumber = number.substring(beginIndex);
        return new Number(newNumber);
    }

    private Number sorted(Comparator<Long> comparator) {
        String sortedDigits = IntStream.range(0, number.length())
                .mapToObj(this::digitAt)
                .sorted(comparator)
                .map(Object::toString)
                .collect(Collectors.joining());

        return new Number(sortedDigits);
    }

    private Number concat(Number other) {
        return new Number(number + other.number);
    }

    long digitAt(int index) {
        String substringAtIndex = number.substring(index, index + 1);
        return Long.parseLong(substringAtIndex);
    }

    Number swap(int index, int otherIndex) {
        if(index == otherIndex) {
            return this;
        }

        if(index < otherIndex) {
            String swapped = number.substring(0, index)
                    + number.charAt(otherIndex)
                    + number.substring(index + 1, otherIndex)
                    + number.charAt(index)
                    + number.substring(otherIndex + 1);

            return new Number(swapped);
        } else {
            return swap(otherIndex, index);
        }
    }

    long toLong() {
        return Long.parseLong(number);
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number1 = (Number) o;
        return Objects.equals(number, number1.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    static Number of(long n) {
        return new Number(String.valueOf(n));
    }
}
