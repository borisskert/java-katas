package decodethemorsecodeadvanced;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MorseCodeDecoderTest {
    @Test
    public void testExampleFromDescription() throws Exception {
        assertThat(
                MorseCodeDecoder.decodeMorse(
                        MorseCodeDecoder.decodeBits(
                                "1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011"
                        )
                ),
                is("HEY JUDE")
        );
    }

    @Test
    public void testAnotherSentences() throws Exception {
        assertThat(
                MorseCodeDecoder.decodeMorse(
                        MorseCodeDecoder.decodeBits(
                                "00011100010101010001000000011101110101110001010111000101000111010111010001110101110000000111010101000101110100011101110111000101110111000111010000000101011101000111011101110001110101011100000001011101110111000101011100011101110001011101110100010101000000011101110111000101010111000100010111010000000111000101010100010000000101110101000101110001110111010100011101011101110000000111010100011101110111000111011101000101110101110101110"
                        )
                ),
                is("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.")
        );
    }

    @Test
    public void shouldDecodeBits() throws Exception {
        String bits = MorseCodeDecoder.decodeBits("1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011");
        assertThat(bits, is("···· · −·−−   ·−−− ··− −·· ·"));
    }

    @Test
    public void shouldSingleLetterBits() throws Exception {
        assertThat(MorseCodeDecoder.decodeBits("11"), is("·"));
        assertThat(MorseCodeDecoder.decodeBits("111111"), is("·"));
    }

    @Test
    public void shouldParseSpecialCases() throws Exception {
        assertThat(MorseCodeDecoder.decodeBits("1"), is("·"));
        assertThat(MorseCodeDecoder.decodeBits("01110"), is("·"));
        assertThat(MorseCodeDecoder.decodeBits("1110111"), is("−−"));
    }
}
