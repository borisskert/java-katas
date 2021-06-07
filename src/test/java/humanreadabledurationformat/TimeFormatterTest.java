package humanreadabledurationformat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeFormatterTest {
    @Test
    public void exampleTests() {
        assertEquals("1 second", TimeFormatter.formatDuration(1));
        assertEquals("1 minute and 2 seconds", TimeFormatter.formatDuration(62));
        assertEquals("2 minutes", TimeFormatter.formatDuration(120));
        assertEquals("1 hour", TimeFormatter.formatDuration(3600));
        assertEquals("1 hour, 1 minute and 2 seconds", TimeFormatter.formatDuration(3662));
    }

    @Test
    public void shouldFulfillBasicTests() throws Exception {
        assertEquals("now", TimeFormatter.formatDuration(0));
    }

    @Test
    public void shouldFulfillRandomTests() throws Exception {
        assertEquals("75 days, 1 hour, 47 minutes and 36 seconds", TimeFormatter.formatDuration(6486456));
        assertEquals("4 years, 68 days, 3 hours and 4 minutes", TimeFormatter.formatDuration(132030240));
    }
}
