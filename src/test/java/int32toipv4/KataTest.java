package int32toipv4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KataTest {
    @Test
    public void sampleTest() {
        assertEquals("128.32.10.1", Kata.longToIP(2149583361L));
        assertEquals("128.114.17.104", Kata.longToIP(2154959208L));
        assertEquals("0.0.0.0", Kata.longToIP(0));
    }
}
