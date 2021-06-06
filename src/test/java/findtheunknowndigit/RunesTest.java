package findtheunknowndigit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RunesTest {

    @Test
    public void testSample() {
        assertEquals("Answer for expression '1+1=?' ", 2, Runes.solveExpression("1+1=?"));
        assertEquals("Answer for expression '123*45?=5?088' ", 6, Runes.solveExpression("123*45?=5?088"));
        assertEquals("Answer for expression '-5?*-1=5?' ", 0, Runes.solveExpression("-5?*-1=5?"));
        assertEquals("Answer for expression '19--45=5?' ", -1, Runes.solveExpression("19--45=5?"));
        assertEquals("Answer for expression '??*??=302?' ", 5, Runes.solveExpression("??*??=302?"));
        assertEquals("Answer for expression '?*11=??' ", 2, Runes.solveExpression("?*11=??"));
        assertEquals("Answer for expression '??*1=??' ", 2, Runes.solveExpression("??*1=??"));
        assertEquals("Answer for expression '??+??=??' ", -1, Runes.solveExpression("??+??=??"));
        assertEquals(8, Runes.solveExpression("-?56373--9216=-?47157"));
    }
}
