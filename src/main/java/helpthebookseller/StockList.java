package helpthebookseller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <a href="https://www.codewars.com/kata/54dc6f5a224c26032800005c/train/java">Help the bookseller !</a>
 */
public class StockList {

    public static String stockSummary(String[] stockArray, String[] categoryArray) {
        if (stockArray.length < 1) {
            return "";
        }

        List<Category> categories = Category.manyFrom(categoryArray);

        List<Book> books = Book.parseMany(stockArray)
                .stream()
                .toList();

        StockSummary stockSummary = StockSummary.empty()
                .putAll(books);

        return categories.stream()
                .map(stockSummary::findQuantity)
                .map(Pair::format)
                .collect(Collectors.joining(" - "));
    }
}

record Category(char letter) {
    public static Category oneOf(char letter) {
        return new Category(letter);
    }

    public static Category oneOf(String letters) {
        return Category.oneOf(letters.charAt(0));
    }

    public static List<Category> manyFrom(String[] letters) {
        return Arrays.stream(letters)
                .map(Category::oneOf)
                .toList();
    }

    @Override
    public String toString() {
        return String.valueOf(letter);
    }
}

record Book(Category category, int quantity) {

    public static Book parseOne(String input) {
        String[] split = input.split(" ");

        final Category category = Category.oneOf(split[0]);
        final int quantity = Integer.parseInt(split[1]);

        return new Book(category, quantity);
    }

    public static List<Book> parseMany(String input) {
        return Arrays.stream(input.split(", "))
                .parallel()
                .map(Book::parseOne)
                .toList();
    }

    public static List<Book> parseMany(String[] input) {
        return Arrays.stream(input)
                .parallel()
                .map(Book::parseMany)
                .flatMap(List::stream)
                .toList();
    }
}

class StockSummary {
    private final Map<Category, Integer> summaries;

    private StockSummary(Map<Category, Integer> summaries) {
        this.summaries = summaries;
    }

    public StockSummary put(Book book) {
        final Map<Category, Integer> newSummaries = new HashMap<>(summaries);

        newSummaries.merge(book.category(), book.quantity(), Integer::sum);

        return new StockSummary(Map.copyOf(newSummaries));
    }

    public StockSummary putAll(List<Book> books) {
        return books.stream()
                .reduce(
                        this,
                        StockSummary::put,
                        StockSummary::merge
                );
    }

    private StockSummary merge(StockSummary other) {
        final Map<Category, Integer> mergedSummaries = new HashMap<>(summaries);

        other.summaries
                .forEach(
                        (category, quantity) -> mergedSummaries.merge(
                                category,
                                quantity,
                                Integer::sum)
                );

        return new StockSummary(mergedSummaries);
    }

    public Pair<Category, Integer> findQuantity(Category category) {
        Integer quantity = summaries.getOrDefault(category, 0);
        return new Pair<>(category, quantity);
    }

    public static StockSummary empty() {
        return new StockSummary(Map.of());
    }
}

record Pair<T, S>(T first, S second) {
    public String format() {
        return "(" + first + " : " + second + ")";
    }
}
