package squareintosquares;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DecomposeTest {

    Decompose decompose;

    @Before
    public void setup() throws Exception {
        decompose = new Decompose();
    }

    @Test
    public void test1() {
        assertEquals("1 2 4 10", decompose.decompose(11));
    }

    @Test
    public void mySimpleExamples() {
        assertEquals(null, decompose.decompose(1));
        assertEquals(null, decompose.decompose(2));
        assertEquals(null, decompose.decompose(3));
        assertEquals(null, decompose.decompose(4));
        assertEquals("3 4", decompose.decompose(5));
        assertEquals(null, decompose.decompose(6));
        assertEquals("2 3 6", decompose.decompose(7));
        assertEquals(null, decompose.decompose(8));
        assertEquals("1 4 8", decompose.decompose(9));
    }

    @Test
    public void failed() throws Exception {
        assertEquals("1 2 3 7 9", decompose.decompose(12));
    }
}
