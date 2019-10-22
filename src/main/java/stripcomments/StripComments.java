package stripcomments;

import java.util.StringJoiner;

public class StripComments {

    public static String stripComments(String text, String[] commentSymbols) {
        String[] lines = text.split("\n");
        StringJoiner joiner = new StringJoiner("\n");

        for (String line : lines) {
            for (String commentSymbol : commentSymbols) {
                line = readUntil(line, commentSymbol);
            }

            joiner.add(trimRight(line));
        }

        return joiner.toString();
    }

    private static String readUntil(String input, String symbol) {
        int index = input.indexOf(symbol);

        if (index == -1) {
            return input;
        } else if (index == 0) {
            return "";
        } else {
            return input.substring(0, index);
        }
    }

    private static String trimRight(String input) {
        if(input.length() == 0) {
            return input;
        }

        int newLength = input.length();

        while(newLength > 0 && input.charAt(newLength - 1) == ' '){
            newLength--;
        }

        return input.substring(0, newLength);
    }
}
