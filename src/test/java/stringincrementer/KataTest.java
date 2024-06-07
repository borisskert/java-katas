package stringincrementer;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KataTest {

    private static void doTest(String str, String expected) {
        String actual = assertDoesNotThrow(() -> Kata.incrementString(str), "Solution thrown an unexpected exception for str=\"" + str + "\"\n\n");
        assertEquals(expected, actual, "Incorrect answer for str=\"" + str + "\"\n\n");
    }

    @Test
    public void exampleTests() {
        doTest("foobar000", "foobar001");
        doTest("foo", "foo1");
        doTest("foobar001", "foobar002");
        doTest("foobar99", "foobar100");
        doTest("foobar099", "foobar100");
        doTest("", "1");
        doTest(
                "]'?I_uUV(u@/zkLD`7_fIuaQ.H:a`}8vV3!82899756893582718362412883268037",
                "]'?I_uUV(u@/zkLD`7_fIuaQ.H:a`}8vV3!82899756893582718362412883268038"
        );
        doTest(
                "1",
                "2"
        );
        doTest(
                "999",
                "1000"
        );
        doTest(
                "000000000014035618407425231",
                "000000000014035618407425232"
        );
    }
}
