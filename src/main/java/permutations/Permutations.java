package permutations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Permutations {

    public static List<String> singlePermutations(String text) {
        Permutation permutation = Permutation.create();

        for (char nextCharacter : text.toCharArray()) {
            permutation = permutation.add(nextCharacter);
        }

        return permutation.toList();
    }

    private static class Permutation {
        private final Set<String> permutations;

        private Permutation(Set<String> permutations) {
            this.permutations = permutations;
        }

        public static Permutation create() {
            return new Permutation(new HashSet<>());
        }

        public Permutation add(char nextCharacter) {
            if (permutations.size() < 1) {
                String characterAsString = String.valueOf(nextCharacter);
                return new Permutation(Collections.singleton(characterAsString));
            }

            Set<String> newPermutations = new HashSet<>();

            for (String permutation : permutations) {
                newPermutations.addAll(mutate(permutation, nextCharacter));
            }

            return new Permutation(newPermutations);
        }

        public List<String> toList() {
            return new ArrayList<>(permutations);
        }

        private Set<String> mutate(String text, char nextCharacter) {
            Set<String> mutations = new HashSet<>();

            for (int index = 0; index <= text.length(); index++) {
                mutations.add(putInto(text, nextCharacter, index));
            }

            return mutations;
        }

        private String putInto(String text, char character, int index) {
            char[] chars = text.toCharArray();
            char[] newChars = putInto(chars, character, index);

            return new String(newChars);
        }

        private char[] putInto(char[] characters, char character, int index) {
            char[] newArray = new char[characters.length + 1];

            for (int counter = 0; counter < characters.length + 1; counter++) {
                if (counter < index) {
                    newArray[counter] = characters[counter];
                }
                if (counter == index) {
                    newArray[counter] = character;
                }
                if (counter > index) {
                    newArray[counter] = characters[counter - 1];
                }
            }

            return newArray;
        }
    }
}
