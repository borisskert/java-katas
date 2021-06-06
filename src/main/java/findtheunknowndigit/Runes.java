package findtheunknowndigit;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * https://www.codewars.com/kata/546d15cebed2e10334000ed9/train/java
 */
public class Runes {

    public static int solveExpression(final String expression) {
        Expression parsedExpression = Expression.parse(expression);

        return IntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(parsedExpression::isCorrect)
                .findFirst()
                .orElse(-1);
    }

}

class Expression {
    private static final Pattern SIMPLE_MATH_EXPRESSION = Pattern.compile("^(?<a>-?([0-9]|\\?)+)(?<operator>[+\\-*])(?<b>-?([0-9]|\\?)+)=(?<c>-?([0-9]|\\?)+)");

    private final String expression;
    private final Number a;
    private final Number b;
    private final Number c;
    private final Operation operator;

    private Expression(String expression, Number a, Number b, Number c, Operation operator) {
        this.expression = expression;
        this.a = a;
        this.b = b;
        this.c = c;
        this.operator = operator;
    }

    boolean isCorrect(int placeholder) {
        try {
            if (expression.contains(String.valueOf(placeholder))) {
                return false;
            }

            return c.value(placeholder) == operator.calculate(a, b, placeholder);
        } catch (IllegalNumberException e) {
            return false;
        }
    }

    static Expression parse(String expression) {
        Matcher matcher = SIMPLE_MATH_EXPRESSION.matcher(expression);

        if (matcher.matches()) {
            String a = matcher.group("a");
            String b = matcher.group("b");
            String c = matcher.group("c");
            String operator = matcher.group("operator");

            return new Expression(
                    expression,
                    Number.of(a),
                    Number.of(b),
                    Number.of(c),
                    Operation.of(operator)
            );
        }

        throw new RuntimeException("Cannot parse expression '" + expression + "'");
    }
}

class Number {
    private static final Predicate<String> ILLEGAL_NUMBER = Pattern.compile("^-?0[0-9]+$").asMatchPredicate();

    private final String value;

    private Number(String value) {
        this.value = value;
    }

    public int value(int placeholder) {
        String possibleValue = value.replaceAll("\\?", String.valueOf(placeholder));

        if (ILLEGAL_NUMBER.test(possibleValue)) {
            throw new IllegalNumberException();
        }

        return Integer.parseInt(possibleValue);
    }

    static Number of(String number) {
        return new Number(number);
    }
}

interface Operation {
    Map<String, Operation> OPERATIONS = Map.of(
            "+", new Addition(),
            "-", new Subtraction(),
            "*", new Multiplication()
    );

    int calculate(Number a, Number b, int placeholder);

    static Operation of(String operator) {
        return Optional.ofNullable(OPERATIONS.get(operator))
                .orElseThrow(
                        () -> new RuntimeException("No operation for '" + operator + "'")
                );
    }
}

class Addition implements Operation {

    @Override
    public int calculate(Number a, Number b, int placeholder) {
        return a.value(placeholder) + b.value(placeholder);
    }
}

class Subtraction implements Operation {

    @Override
    public int calculate(Number a, Number b, int placeholder) {
        return a.value(placeholder) - b.value(placeholder);
    }
}

class Multiplication implements Operation {

    @Override
    public int calculate(Number a, Number b, int placeholder) {
        return a.value(placeholder) * b.value(placeholder);
    }
}

class IllegalNumberException extends RuntimeException {
}
