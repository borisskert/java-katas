package simplepiglatin;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PigLatinTest {
    @Test
    public void FixedTests() {
        assertEquals("igPay atinlay siay oolcay", PigLatin.pigIt("Pig latin is cool"));
        assertEquals("hisTay siay ymay tringsay", PigLatin.pigIt("This is my string"));
        assertEquals("yMay unctuationPay !", PigLatin.pigIt("My Punctuation !"));
    }
}
