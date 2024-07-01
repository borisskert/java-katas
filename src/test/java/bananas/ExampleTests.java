package bananas;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExampleTests {

    // common test code
    private void doTest(final String input, final Set<String> expected, final Set<String> actual) {
        System.out.printf("INPUT: %s%n", input);
        System.out.printf("EXPECTED: %s %n", expected);
        assertEquals("wrong number of bananas!", expected.size(), actual.size());
        if (!actual.containsAll(expected)) {
            System.out.printf("ACTUAL: %s %n", actual);
            fail("banana mismatch!");
        }
    }

    @Test
    public void ex0() {
        final String input = "banann";
        final Set<String> expected = Collections.emptySet();
        final Set<String> actual = Dinglemouse.bananas(input);
        doTest(input, expected, actual);
    }

    @Test
    public void ex1() {
        final String input = "banana";
        final Set<String> expected = new HashSet<>(List.of("banana"));
        final Set<String> actual = Dinglemouse.bananas(input);
        doTest(input, expected, actual);
    }

    @Test
    public void ex2() {
        final String input = "bbananana";
        final Set<String> expected = new HashSet<>(Arrays.asList(
                "b-an--ana", "-banana--", "-b--anana", "b-a--nana", "-banan--a",
                "b-ana--na", "b---anana", "-bana--na", "-ba--nana", "b-anan--a",
                "-ban--ana", "b-anana--"));
        final Set<String> actual = Dinglemouse.bananas(input);
        doTest(input, expected, actual);
    }

    @Test
    public void ex3() {
        final String input = "bananaaa";
        final Set<String> expected = new HashSet<>(Arrays.asList(
                "banan-a-", "banana--", "banan--a"));
        final Set<String> actual = Dinglemouse.bananas(input);
        doTest(input, expected, actual);
    }

    @Test
    public void ex4() {
        final String input = "bananana";
        final Set<String> expected = new HashSet<>(Arrays.asList(
                "ban--ana", "ba--nana", "bana--na", "b--anana", "banana--",
                "banan--a"));
        final Set<String> actual = Dinglemouse.bananas(input);
        doTest(input, expected, actual);
    }

}
