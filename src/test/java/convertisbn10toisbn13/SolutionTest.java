package convertisbn10toisbn13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @Test
    @DisplayName("Fixed tests")
    void test() {
        assertEquals("978-1-85326-158-9", Solution.isbnConverter("1-85326-158-0"));
        assertEquals("978-0-14-143951-8", Solution.isbnConverter("0-14-143951-3"));
        assertEquals("978-0-02-346450-8", Solution.isbnConverter("0-02-346450-X"));
        assertEquals("978-963-14-2164-4", Solution.isbnConverter("963-14-2164-3"));
        assertEquals("978-1-7982-0894-6", Solution.isbnConverter("1-7982-0894-6"));
    }
}
