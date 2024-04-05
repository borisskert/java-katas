package firstnonrepeatingcharacter;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @Test @DisplayName("Sample tests")
    void sampleTests() {
        assertEquals("a", Kata.firstNonRepeatingLetter("a"), "For input \"a\"");
        assertEquals("t", Kata.firstNonRepeatingLetter("streSS"), "For input \"streSS\"");
        assertEquals("-", Kata.firstNonRepeatingLetter("moon-men"), "For input \"moon-men\"");
        assertEquals("", Kata.firstNonRepeatingLetter("moonmoon"), "For input \"moonmoon\"");
    }
}
