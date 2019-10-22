package stripcomments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StripCommentsTest {

    @Test
    public void stripComments() throws Exception {
//        assertEquals(
//                "apples, pears\ngrapes\nbananas",
//                StripComments.stripComments("apples, pears # and bananas\ngrapes\nbananas !apples", new String[]{"#", "!"})
//        );
//
//        assertEquals(
//                "a\nc\nd",
//                StripComments.stripComments("a #b\nc\nd $e f g", new String[]{"#", "$"})
//        );
//
//        assertEquals("a\n b\nc"
//                ,
//                StripComments.stripComments("a \n" +
//                        " b \n" +
//                        "c ", new String[]{"#", "$"})
//        );
//
//        assertEquals("a\n" +
//                        " b\n" +
//                        "c\n" +
//                        "a\n" +
//                        "a"
//                ,
//                StripComments.stripComments("a \n" +
//                        " b \n" +
//                        "c \n" +
//                        "a\n" +
//                        "a", new String[]{"#", "$"})
//        );

        assertEquals("b\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "\n" +
                        "baa\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "cff\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "afe\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "dce\n" +
                        "\n" +
                        "ff\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "da\n" +
                        "\n" +
                        "ce\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "b\n" +
                        "\n" +
                        "cd\n" +
                        "\n" +
                        "ed\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "ed\n" +
                        "\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "b\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "baa\n" +
                        "\n" +
                        "\n" +
                        "ca\n" +
                        "\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "b\n" +
                        "a\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "da\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "cd\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "b"
                ,
                StripComments.stripComments("b\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "#f\n" +
                        "d\n" +
                        "\n" +
                        "\n" +
                        "e#b\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "\n" +
                        "baa##e\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "cff#f\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "f\n" +
                        "\n" +
                        "afe\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "\n" +
                        "#\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "#\n" +
                        "dce\n" +
                        "\n" +
                        "ff#\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "da#\n" +
                        "\n" +
                        "ce\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "b\n" +
                        "\n" +
                        "cd\n" +
                        "\n" +
                        "ed\n" +
                        "\n" +
                        "#\n" +
                        "\n" +
                        "##\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "ed\n" +
                        "\n" +
                        "\n" +
                        "d\n" +
                        "\n" +
                        "b\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "##ba#\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "baa\n" +
                        "\n" +
                        "\n" +
                        "ca\n" +
                        "\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "b\n" +
                        "a\n" +
                        "\n" +
                        "#b\n" +
                        "\n" +
                        "da#\n" +
                        "\n" +
                        "a\n" +
                        "\n" +
                        "cd\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "ef\n" +
                        "\n" +
                        "#\n" +
                        "\n" +
                        "bdb\n" +
                        "\n" +
                        "bf\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "e\n" +
                        "\n" +
                        "b\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "c\n" +
                        "\n" +
                        "##\n" +
                        "\n" +
                        "\n" +
                        "c", new String[]{"#"})
        );
//
//        assertEquals("\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "e\n" +
//                        "\n" +
//                        "baa\n" +
//                        "\n" +
//                        "\n"
//                ,
//                StripComments.stripComments(
//                        "##ba#\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "e\n" +
//                        "\n" +
//                        "baa\n" +
//                        "\n" +
//                        "\n", new String[]{"#"})
//        );

    }
}
