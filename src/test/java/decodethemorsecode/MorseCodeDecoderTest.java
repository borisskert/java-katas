package decodethemorsecode;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MorseCodeDecoderTest {
    @Test
    public void testExampleFromDescription() {
        assertThat(MorseCodeDecoder.decode(".... . -.--   .--- ..- -.. ."), is("HEY JUDE"));
    }

    @Test
    public void testAnotherCodes() {
//        assertThat(MorseCodeDecoder.decode("...---..."), is("SOS"));
//        assertThat(MorseCodeDecoder.decode("   .   . "), is("E E"));
        assertThat(
                MorseCodeDecoder.decode(
                "      ...---... -.-.--   - .... .   --.- ..- .. -.-. -.-   -... .-. --- .-- -.   ..-. --- -..-   .--- ..- -- .--. ...   --- ...- . .-.   - .... .   .-.. .- --.. -.--   -.. --- --. .-.-.-  "
                ),
                is("SOS! THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG."));
    }
}
