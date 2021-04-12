package calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParsedRuleTest {

    @Test
    public void shouldParseSimpleLine() {
        ParsedRule parsed = ParsedRule.parse("Z -> A");

        assertEquals(parsed.left(), "Z");
        assertEquals(parsed.right(), "A");
    }

    @Test
    public void shouldParseAnotherLine() {
        ParsedRule parsed = ParsedRule.parse("Z -> A * B");

        assertEquals(parsed.left(), "Z");
        assertEquals(parsed.right(), "A * B");
    }
}
