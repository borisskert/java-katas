package catchingcarmileagenumbers;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * https://www.codewars.com/kata/52c4dd683bfd3b434c000292/train/java
 */
public class CarMileage {

    public static int isInteresting(int number, int[] awesomePhrases) {
        CarMileageNumber carMileageNumber = CarMileageNumber.checkWith(awesomePhrases);

        if (carMileageNumber.isInteresting(number)) {
            return 2;
        }
        if (carMileageNumber.isAlmostInteresting(number)) {
            return 1;
        }

        return 0;
    }

    private static class CarMileageNumber {
        private static final int MINIMAL_INTERESTING_NUMBER = 100;
        private static final Pattern followedByZerosMatcher = Pattern.compile("^[1-9]0[0]+$");

        private final int[] awesomePhrases;

        private CarMileageNumber(int[] awesomePhrases) {
            this.awesomePhrases = awesomePhrases;
        }

        static CarMileageNumber checkWith(int[] awesomePhrases) {
            return new CarMileageNumber(awesomePhrases);
        }

        boolean isInteresting(int number) {
            return !isTooSmall(number) && (
                    isAwesomePhrase(number) ||
                            isPalindrome(number) ||
                            isFollowedByAllZeros(number) ||
                            isSequentialIncrementing(number) ||
                            isSequentialDecrementing(number)
            );
        }

        boolean isAlmostInteresting(int number) {
            return isInteresting(number + 1) || isInteresting(number + 2);
        }

        private boolean isTooSmall(int number) {
            return number < MINIMAL_INTERESTING_NUMBER;
        }

        private boolean isAwesomePhrase(int number) {
            return Arrays.stream(awesomePhrases)
                    .filter(awesomePhrase -> awesomePhrase == number)
                    .count() > 0;
        }

        private boolean isFollowedByAllZeros(int number) {
            String numberAsText = String.valueOf(number);
            return followedByZerosMatcher.matcher(numberAsText).matches();
        }

        private boolean isPalindrome(int number) {
            String numberAsText = String.valueOf(number);

            for (int index = 0; index < numberAsText.length() / 2; index++) {
                int counterIndex = numberAsText.length() - 1 - index;

                if (numberAsText.charAt(index) != numberAsText.charAt(counterIndex)) {
                    return false;
                }
            }

            return true;
        }

        private boolean isSequentialIncrementing(int number) {
            return isSequential(number, this::areSequentialIncrementing);
        }

        private boolean isSequentialDecrementing(int number) {
            return isSequential(number, this::areSequentialDecrementing);
        }

        private boolean isSequential(int number, BiFunction<Integer, Integer, Boolean> compare) {
            int length = (int) Math.log10(number) + 1;

            Integer followingDigit = null;

            for (int index = 0; index < length; index++) {
                int divisor = (int) Math.pow(10.0, index);
                int digit = number / divisor % 10;

                if (followingDigit != null) {
                    if (!compare.apply(digit, followingDigit)) {
                        return false;
                    }
                }

                followingDigit = digit;
            }

            return true;
        }

        private boolean areSequentialIncrementing(int digit, int followingDigit) {
            return digit == followingDigit - 1 ||
                    digit == 9 && followingDigit == 0;
        }

        private boolean areSequentialDecrementing(int digit, int followingDigit) {
            return digit == followingDigit + 1;
        }
    }
}
