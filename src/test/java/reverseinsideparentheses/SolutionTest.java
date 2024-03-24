package reverseinsideparentheses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @Test
    void testSimple() {
        assertEquals("h(le)lo", Kata.reverseParens("h(el)lo"));
        assertEquals("hello", Kata.reverseParens("hello"));
        assertEquals("a (b c (d e))", Kata.reverseParens("a ((d e) c b)"));
        assertEquals("one (ruof (three) owt)", Kata.reverseParens("one (two (three) four)"));
        assertEquals("one (two ((thr)ee) four)", Kata.reverseParens("one (ruof ((rht)ee) owt)"));
    }
}
