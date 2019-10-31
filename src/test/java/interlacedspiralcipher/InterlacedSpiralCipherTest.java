package interlacedspiralcipher;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterlacedSpiralCipherTest {
    @Test
    public void SampleTests() {

        String clear = "Romani ite domum",
                crypt = "Rntodomiimuea  m";
        assertEquals(crypt, InterlacedSpiralCipher.encode(clear));
        assertEquals(clear, InterlacedSpiralCipher.decode(crypt));

        clear = "Sic transit gloria mundi";
        crypt = "Stsgiriuar i ninmd l otac";
        assertEquals(crypt, InterlacedSpiralCipher.encode(clear));
        assertEquals(clear, InterlacedSpiralCipher.decode(crypt));
    }
}
