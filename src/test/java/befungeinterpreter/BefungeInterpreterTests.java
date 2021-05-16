package befungeinterpreter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class BefungeInterpreterTests {
    BefungeInterpreter interpreter;

    @Before
    public void setup() throws Exception {
        interpreter = new BefungeInterpreter();
    }

    @Test
    public void tests() {
        assertEquals(
                "123456789",
                interpreter.interpret(
                        ">987v>.v\n" +
                                "v456<  :\n" +
                                ">321 ^ _@"));

    }

    @Test
    public void shouldPrintHelloWorld() throws Exception {
        String code = "" +
                ">              v\n" +
                "v  ,,,,,\"Hello\"<\n" +
                ">48*,          v\n" +
                "v,,,,,,\"World!\"<\n" +
                ">25*,@";

        assertEquals(
                "Hello World!\n",
                interpreter.interpret(code)
        );
    }

    @Test
    public void shouldPrintHelloWorldMoreComplex() throws Exception {
        String code = "" +
                " >25*\"!dlrow ,olleH\":v\n" +
                "                  v:,_@\n" +
                "                  >  ^\n";

        assertEquals(
                "Hello, world!\n",
                interpreter.interpret(code)
        );
    }


    @Test
    public void shouldCompileAnotherHelloWorld() throws Exception {
        String code = "" +
                ">25*\"!dlroW olleH\":v\n" +
                "                v:,_@\n" +
                "                >  ^";

        assertEquals(
                "Hello World!\n",
                interpreter.interpret(code)
        );
    }

    @Test
    public void shouldCompileFactorial() throws Exception {
        String code = "" +
                "08>:1-:v v *_$.@ \n" +
                "  ^    _$>\\:^  ^    _$>\\:^";

        assertEquals(
                "40320",
                interpreter.interpret(code)
        );
    }

    @Test
    public void shouldCompileRandomDirection() throws Exception {
        String code = "" +
                "v@.<\n" +
                " >1^\n" +
                ">?<^\n" +
                " >2^";

        assertThat(interpreter.interpret(code)).isIn("1", "2");
    }

    @Test
    public void shouldPerformOneMillion() throws Exception {
        String code = "" +
                "v@.<\n" +
                " >1^\n" +
                ">?<^\n" +
                " >2^";

        for (int index = 0; index < 1000000; index++) {
            interpreter.interpret(code);
        }
    }

    @Test
    public void shouldQuine() throws Exception {
        String code = "01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@";

        assertEquals(
                "01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@",
                interpreter.interpret(code)
        );
    }

    @Test
    public void shouldSieve() throws Exception {
        String code = "" +
                "2>:3g\" \"-!v\\  g30          <\n" +
                " |!`\"&\":+1_:.:03p>03g+:\"&\"`|\n" +
                " @               ^  p3\\\" \":<\n" +
                "2 2345678901234567890123456789012345678";

        assertEquals(
                "23571113171923293137",
                interpreter.interpret(code)
        );
    }
}
