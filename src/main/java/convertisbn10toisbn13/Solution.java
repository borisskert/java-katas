package convertisbn10toisbn13;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/61ce25e92ca4fb000f689fb0/train/java">Convert ISBN-10 to ISBN-13</a>
 */
public class Solution {
    public static String isbnConverter(String isbn) {
        return Isbn10.from(isbn)
                .toIsbn13()
                .format();
    }
}

class Isbn10 {
    private final Groups groups;

    private Isbn10(Groups groups) {
        this.groups = groups;
    }

    Isbn13 toIsbn13() {
        return Isbn13.fromIsbn10Groups(groups);
    }

    static Isbn10 from(String isbn) {
        Groups groups = Groups.from(isbn);
        return new Isbn10(groups);
    }
}

class Isbn13 {
    private static final String PREFIX_NUMBER = "978";

    private final Groups groups;

    private Isbn13(Groups groups) {
        this.groups = groups;
    }

    public String format() {
        return groups.format();
    }

    static Isbn13 fromIsbn10Groups(Groups isbn10groups) {
        Groups relevantGroups = Groups.of(PREFIX_NUMBER)
                .append(isbn10groups.init());

        String checkdigit = Integer.toString(relevantGroups.checkdigit());

        return new Isbn13(relevantGroups.append(Groups.of(checkdigit)));
    }
}

class Groups {
    private static final String GROUP_DELIMITER = "-";

    private final List<String> groups;

    private Groups(List<String> groups) {
        this.groups = groups;
    }

    Groups init() {
        List<String> limited = groups.stream()
                .limit(groups.size() - 1)
                .toList();

        return new Groups(limited);
    }

    Stream<String> stream() {
        return groups.stream();
    }

    int checkdigit() {
        List<Integer> pureDigits = this.digits();

        int sum = IntStream.range(0, pureDigits.size())
                .map(index -> {
                    int factor = index % 2 == 0 ? 1 : 3;
                    int digit = pureDigits.get(index);

                    return factor * digit;
                }).sum();

        int checksum = sum % 10;

        return checksum == 0 ? 0 : 10 - checksum;
    }

    String format() {
        StringJoiner dashes = new StringJoiner(GROUP_DELIMITER);
        return groups.stream()
                .reduce(
                        dashes,
                        StringJoiner::add,
                        StringJoiner::merge
                )
                .toString();
    }

    private List<Integer> digits() {
        return groups.stream()
                .flatMap(s -> s.chars().map(c -> c - '0').boxed())
                .toList();
    }

    static Groups from(String isbn) {
        List<String> groups = Arrays
                .stream(isbn.split(GROUP_DELIMITER))
                .toList();

        return new Groups(groups);
    }

    public static Groups of(String group) {
        return new Groups(List.of(group));
    }

    public Groups append(Groups other) {
        List<String> appended = Stream.concat(
                this.stream(),
                other.stream()
        ).toList();

        return new Groups(appended);
    }
}
