package validparentheses;

/**
 * https://www.codewars.com/kata/52774a314c2333f0a7000688/train/java
 */
public class Solution {

    public static boolean validParentheses(String parens) {
        return Parentheses.of(parens)
                .isValid();
    }
}

class Parentheses {

    private final String parens;
    private final int position;
    private final int counter;

    private Parentheses(String parens, int position, int counter) {
        this.parens = parens;
        this.position = position;
        this.counter = counter;
    }

    public boolean isValid() {
        if (counter < 0) {
            return false;
        }

        if (counter == 0 && isFinished()) {
            return true;
        }

        if (isFinished()) {
            return false;
        }

        if (isOpening()) {
            return increment().isValid();
        }

        if (isClosing()) {
            return decrement().isValid();
        }

        return next().isValid();
    }

    private boolean isFinished() {
        return position >= parens.length();
    }

    private boolean isOpening() {
        return parens.charAt(position) == '(';
    }

    private boolean isClosing() {
        return parens.charAt(position) == ')';
    }

    private Parentheses next() {
        return new Parentheses(parens, position + 1, counter);
    }

    private Parentheses increment() {
        return new Parentheses(parens, position + 1, counter + 1);
    }

    private Parentheses decrement() {
        return new Parentheses(parens, position + 1, counter - 1);
    }

    public static Parentheses of(String parens) {
        return new Parentheses(parens, 0, 0);
    }
}
