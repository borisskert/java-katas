package decodethemorsecode;

import java.util.LinkedList;
import java.util.List;

public class MorseCodeDecoder {
    private static final List<Rule> translationRules = new LinkedList<>();

    static {
        translationRules.add(Rule.of("\\.\\.\\.---\\.\\.\\.", "SOS"));
        translationRules.add(Rule.of("\\.-\\.-\\.-", ":"));
        translationRules.add(Rule.of("-\\.-\\.--", "!"));

        translationRules.add(Rule.of("\\.----", "1"));
        translationRules.add(Rule.of("\\.\\.---", "2"));
        translationRules.add(Rule.of("\\.\\.\\.--", "3"));
        translationRules.add(Rule.of("\\.\\.\\.\\.-", "4"));
        translationRules.add(Rule.of("\\.\\.\\.\\.\\.", "5"));
        translationRules.add(Rule.of("-\\.\\.\\.\\.", "6"));
        translationRules.add(Rule.of("--\\.\\.\\.", "7"));
        translationRules.add(Rule.of("---\\.\\.", "8"));
        translationRules.add(Rule.of("----\\.", "9"));
        translationRules.add(Rule.of("-----", "0"));

        translationRules.add(Rule.of("-\\.\\.\\.", "B"));
        translationRules.add(Rule.of("-\\.-\\.", "C"));
        translationRules.add(Rule.of("\\.\\.-\\.", "F"));
        translationRules.add(Rule.of("\\.\\.\\.\\.", "H"));
        translationRules.add(Rule.of("\\.---", "J"));
        translationRules.add(Rule.of("\\.-\\.\\.", "L"));
        translationRules.add(Rule.of("\\.--\\.", "P"));
        translationRules.add(Rule.of("--\\.-", "Q"));
        translationRules.add(Rule.of("\\.\\.\\.-", "V"));
        translationRules.add(Rule.of("-\\.\\.-", "X"));
        translationRules.add(Rule.of("-\\.--", "Y"));
        translationRules.add(Rule.of("--\\.\\.", "Z"));

        translationRules.add(Rule.of("-\\.\\.", "D"));
        translationRules.add(Rule.of("--\\.", "G"));
        translationRules.add(Rule.of("-\\.-", "K"));
        translationRules.add(Rule.of("---", "O"));
        translationRules.add(Rule.of("\\.-\\.", "R"));
        translationRules.add(Rule.of("\\.\\.\\.", "S"));
        translationRules.add(Rule.of("\\.\\.-", "U"));
        translationRules.add(Rule.of("\\.--", "W"));
        translationRules.add(Rule.of("   ", "_"));

        translationRules.add(Rule.of("\\.-", "A"));
        translationRules.add(Rule.of("\\.\\.", "I"));
        translationRules.add(Rule.of("--", "M"));
        translationRules.add(Rule.of("-\\.", "N"));

        translationRules.add(Rule.of("\\.", "E"));
        translationRules.add(Rule.of("-", "T"));

        translationRules.add(Rule.of(" ", ""));
        translationRules.add(Rule.of("_", " "));
        translationRules.add(Rule.of(":", "."));
    }

    public static String decode(String morseCode) {
        String decoded = morseCode;

        for (Rule rule : translationRules) {
            decoded = decoded.replaceAll(rule.morse, rule.ascii);
        }

        return decoded.trim();
    }

    private static class Rule {
        final String morse;
        final String ascii;

        private Rule(String morse, String ascii) {
            this.morse = morse;
            this.ascii = ascii;
        }

        static Rule of(String morse, String ascii) {
            return new Rule(morse, ascii);
        }
    }
}
