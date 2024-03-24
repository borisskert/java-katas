package reverseinsideparentheses;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/5e07b5c55654a900230f0229/train/java">Reverse Inside Parentheses (Inside Parentheses)</a>
 */
public class Kata {
    public static String reverseParens(String text) {
        return Literal.parse(text)
                .stream()
                .map(Literal::reversedInsideParentheses)
                .map(Literal::toString)
                .collect(Collectors.joining());
    }
}

interface Literal {
    int length();

    Literal reversed();

    default Literal reversedInsideParentheses() {
        return this;
    }

    static List<Literal> parse(String text) {
        if (text.isEmpty()) {
            return List.of();
        }

        return Parentheses.tryParse(text)
                .or(() -> Text.tryParse(text))
                .map(parsedLiteral -> {
                    int length = parsedLiteral.length();
                    String remainingText = text.substring(length);
                    List<Literal> otherParsed = parse(remainingText);

                    return Stream.concat(
                            Stream.of(parsedLiteral),
                            otherParsed.stream()
                    ).toList();
                }).orElse(List.of());
    }
}

class Text implements Literal {
    private final String text;

    Text(String text) {
        this.text = text;
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public Literal reversed() {
        // https://stackoverflow.com/a/7569370/13213024
        String reversed = new StringBuilder(text)
                .reverse()
                .toString();

        return new Text(reversed);
    }

    @Override
    public String toString() {
        return text;
    }

    static Optional<Literal> tryParse(String text) {
        if (text.startsWith("(") || text.startsWith(")")) {
            return Optional.empty();
        }

        String extracted = extractText(text);
        return Optional.of(new Text(extracted));
    }

    private static String extractText(String text) {
        int openingIndex = text.indexOf(')');
        int closingIndex = text.indexOf('(');

        if (closingIndex == openingIndex) /* both are -1 (not found) */ {
            return text;
        } else if (closingIndex == -1) {
            return text.substring(0, openingIndex);
        } else if (openingIndex == -1) {
            return text.substring(0, closingIndex);
        }

        int untilIndex = Math.min(openingIndex, closingIndex);
        return text.substring(0, untilIndex);
    }
}

class Parentheses implements Literal {
    private final List<Literal> literals;

    Parentheses(List<Literal> literals) {
        this.literals = literals;
    }

    @Override
    public int length() {
        return literals.stream()
                .map(Literal::length)
                .reduce(Integer::sum)
                .orElse(0) + 2;
    }

    @Override
    public Literal reversed() {
        // https://stackabuse.com/java-8-streams-collect-and-reverse-stream-into-list/
        List<Literal> literals = this.literals
                .stream()
                .map(Literal::reversed)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Collections.reverse(list);
                                    return list;
                                }
                        )
                );

        return new Parentheses(literals);
    }

    @Override
    public Literal reversedInsideParentheses() {
        List<Literal> literals = this.literals
                .stream()
                .map(Literal::reversedInsideParentheses)
                .map(Literal::reversed)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Collections.reverse(list);
                                    return list;
                                }
                        )
                );

        return new Parentheses(literals);
    }

    @Override
    public String toString() {
        return "(" + literals.stream()
                .map(Object::toString)
                .collect(Collectors.joining()) +
                ")";
    }

    static Optional<Literal> tryParse(String text) {
        if (text.startsWith("(")) {
            List<Literal> parsed = Literal.parse(text.substring(1));
            return Optional.of(new Parentheses(parsed));
        }

        return Optional.empty();
    }
}
