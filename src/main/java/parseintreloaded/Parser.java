package parseintreloaded;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.codewars.com/kata/525c7c5ab6aecef16e0001a5/train/java
 */
public class Parser {
    public static int parseInt(String numStr) {
        return Millions.parse(numStr);
    }
}

class Numbers {
    private static final Map<String, Integer> numbers;

    static {
        numbers = new HashMap<>();

        numbers.put("zero", 0);
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);
        numbers.put("ten", 10);

        numbers.put("eleven", 11);
        numbers.put("twelve", 12);
        numbers.put("thirteen", 13);
        numbers.put("fourteen", 14);
        numbers.put("fifteen", 15);
        numbers.put("sixteen", 16);
        numbers.put("seventeen", 17);
        numbers.put("eighteen", 18);
        numbers.put("nineteen", 19);

        numbers.put("twenty", 20);
        numbers.put("thirty", 30);
        numbers.put("forty", 40);
        numbers.put("fifty", 50);
        numbers.put("sixty", 60);
        numbers.put("seventy", 70);
        numbers.put("eighty", 80);
        numbers.put("ninety", 90);
    }

    public static int get(String numberAsText) {
        Integer foundNumber = numbers.get(numberAsText);

        if (foundNumber == null) {
            throw new RuntimeException("Cannot find number for '" + numberAsText + "'");
        }

        return foundNumber;
    }
}

class Millions {
    private static final Pattern MILLIONS_PATTERN = Pattern.compile("(?<factor>[a-z- ]+) million(?<remainder> [a-z -]+)?$");
    private static final int ONE_MILLION = 1000000;

    public static int parse(String numberAsText) {
        Matcher matcher = MILLIONS_PATTERN.matcher(numberAsText);

        if (matcher.matches()) {
            String factor = matcher.group("factor");
            String remainder = matcher.group("remainder");

            Integer remainderValue = Optional.ofNullable(remainder).map(Thousands::parse).orElse(0);

            return Hundreds.parse(factor) * ONE_MILLION + remainderValue;
        }

        return Thousands.parse(numberAsText);
    }
}

class Thousands {
    private static final Pattern THOUSANDS_PATTERN = Pattern.compile("(?<factor>[a-z- ]+) thousand(?<remainder> [a-z -]+)?$");
    public static final int ONE_THOUSAND = 1000;

    public static int parse(String numberAsText) {
        Matcher matcher = THOUSANDS_PATTERN.matcher(numberAsText);

        if (matcher.matches()) {
            String thousand = matcher.group("factor");
            String remainder = matcher.group("remainder");

            Integer remainderValue = Optional.ofNullable(remainder).map(Hundreds::parse).orElse(0);

            return Hundreds.parse(thousand) * ONE_THOUSAND + remainderValue;
        }

        return Hundreds.parse(numberAsText);
    }
}

class Hundreds {
    private static final Pattern HUNDREDS_PATTERN = Pattern.compile("( (and )?)?(?<factor>[a-z]+) hundred(?<remainder> [a-z -]+)?$");
    public static final int ONE_HUNDRED = 100;

    public static int parse(String numberAsText) {
        Matcher matcher = HUNDREDS_PATTERN.matcher(numberAsText);

        if (matcher.matches()) {
            String hundreds = matcher.group("factor");
            String remainder = matcher.group("remainder");

            Integer remainderValue = Optional.ofNullable(remainder).map(TwoDigits::parse).orElse(0);

            return Numbers.get(hundreds) * ONE_HUNDRED + remainderValue;
        }

        return TwoDigits.parse(numberAsText);
    }
}

class TwoDigits {
    private static final Pattern TWO_DIGITS_PATTERN = Pattern.compile("( (and )?)?(?<factor>[a-z]+)-(?<remainder>[a-z]+)$");

    public static int parse(String numberAsText) {
        Matcher matcher = TWO_DIGITS_PATTERN.matcher(numberAsText);

        if (matcher.matches()) {
            String factor = matcher.group("factor");
            String remainder = matcher.group("remainder");

            return Numbers.get(factor) + Numbers.get(remainder);
        }

        return SingleDigit.parse(numberAsText);
    }
}

class SingleDigit {
    private static final Pattern SINGLE_DIGIT_PATTERN = Pattern.compile("( (and )?)?(?<number>[a-z]+)$");

    public static int parse(String numberAsText) {
        Matcher matcher = SINGLE_DIGIT_PATTERN.matcher(numberAsText);

        if (matcher.matches()) {
            String factor = matcher.group("number");

            return Numbers.get(factor);
        }

        throw new RuntimeException("Illegal number: " + numberAsText);
    }
}
