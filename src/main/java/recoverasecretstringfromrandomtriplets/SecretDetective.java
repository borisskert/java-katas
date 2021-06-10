package recoverasecretstringfromrandomtriplets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/53f40dff5f9d31b813000774/train/java
 */
public class SecretDetective {

    public String recoverSecret(char[][] triplets) {
        Letters letters = Letters.collect(triplets);

        return letters.sorted()
                .map(Letter::toCharSequence)
                .collect(Collectors.joining());
    }
}

class Letters {
    private final Map<Character, Letter> collectedLetters = new HashMap<>();

    private Letters() {
    }

    Stream<Letter> sorted() {
        return collectedLetters.values().stream().sorted();
    }

    private void add(char[] triplets) {
        Letter first = letterOf(triplets[0]);
        Letter second = letterOf(triplets[1]);
        Letter third = letterOf(triplets[2]);

        collectedLetters.put(first.toChar(), first.addSuccessor(second).addSuccessor(third));
        collectedLetters.put(second.toChar(), second.addPredecessor(first).addSuccessor(third));
        collectedLetters.put(third.toChar(), third.addPredecessor(first).addPredecessor(second));
    }

    private Letter letterOf(char tripletLetter) {
        return collectedLetters.getOrDefault(tripletLetter, Letter.of(tripletLetter));
    }

    static Letters collect(char[][] triplets) {
        Letters letters = new Letters();

        for (char[] triplet : triplets) {
            letters.add(triplet);
        }

        return letters;
    }
}

class Letter implements Comparable<Letter> {
    private final char letter;

    private final Set<Letter> predecessors = new HashSet<>();
    private final Set<Letter> successors = new HashSet<>();

    private Letter(char letter) {
        this.letter = letter;
    }

    Letter addPredecessor(Letter predecessor) {
        predecessors.add(predecessor);
        return this;
    }

    Letter addSuccessor(Letter successor) {
        successors.add(successor);
        return this;
    }

    CharSequence toCharSequence() {
        return String.valueOf(letter);
    }

    char toChar() {
        return letter;
    }

    static Letter of(char letter) {
        return new Letter(letter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter1 = (Letter) o;
        return letter == letter1.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter);
    }

    @Override
    public String toString() {
        return "Letter{" +
                "letter=" + letter +
                '}';
    }

    @Override
    public int compareTo(Letter other) {
        if (this.equals(other)) {
            return 0;
        }

        if (isPredecessorOf(other)) {
            return -1;
        }
        if (isSuccessorOf(other)) {
            return 1;
        }

        return 0;
    }

    private boolean isPredecessorOf(Letter other) {
        if (successors.contains(other)) {
            return true;
        }

        if (predecessors.contains(other)) {
            return false;
        }

        if (other.predecessors.contains(this)) {
            return true;
        }

        if (other.successors.contains(this)) {
            return false;
        }

        return successors.stream().anyMatch(p -> p.isPredecessorOf(other));
    }

    private boolean isSuccessorOf(Letter other) {
        if (predecessors.contains(other)) {
            return true;
        }

        if (successors.contains(other)) {
            return false;
        }

        if (other.successors.contains(this)) {
            return true;
        }

        if (other.predecessors.contains(this)) {
            return false;
        }

        return predecessors.stream().anyMatch(p -> p.isSuccessorOf(other));
    }
}
