package observedpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ObservedPin {

    private static final Map<Character, char[]> pins;

    static {
        pins = new HashMap<>();

        pins.put('0', new char[]{'0', '8'});
        pins.put('1', new char[]{'1', '2', '4'});
        pins.put('2', new char[]{'1', '2', '3', '5'});
        pins.put('3', new char[]{'2', '3', '6'});
        pins.put('4', new char[]{'1', '4', '5', '7'});
        pins.put('5', new char[]{'2', '4', '5', '6', '8'});
        pins.put('6', new char[]{'3', '5', '6', '9'});
        pins.put('7', new char[]{'4', '7', '8'});
        pins.put('8', new char[]{'5', '7', '8', '9', '0'});
        pins.put('9', new char[]{'6', '8', '9'});
    }

    public static List<String> getPINs(String observed) {
        Pins pins = Pins.create();

        for (int index = 0; index < observed.length(); index++) {
            char characterAtIndex = observed.charAt(index);
            char[] alternativeCharacters = ObservedPin.pins.get(characterAtIndex);

            pins = pins.add(alternativeCharacters);
        }

        return pins.toList();
    }

    private static class Pins {
        private final Set<String> permutations;

        private Pins(Set<String> permutations) {
            this.permutations = permutations;
        }

        public static Pins create() {
            return new Pins(new HashSet<>());
        }

        public Pins add(char[] nextCharacters) {
            if (permutations.size() < 1) {
                return initializeEmpty(nextCharacters);
            }

            Set<String> newPermutations = new HashSet<>();

            for (char nextCharacter : nextCharacters) {
                for (String permutation : permutations) {
                    newPermutations.add(permutation + nextCharacter);
                }
            }

            return new Pins(newPermutations);
        }

        private Pins initializeEmpty(char[] nextCharacters) {
            Set<String> newPermutations = new HashSet<>();

            for (char nextCharacter : nextCharacters) {
                newPermutations.add(String.valueOf(nextCharacter));
            }

            return new Pins(newPermutations);
        }

        public List<String> toList() {
            return new ArrayList<>(permutations);
        }
    }
}
