package twicelinear;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static twicelinear.DoubleLinear.limit;

public class DoubleLinearTest {

    private static void testing(int actual, int expected) {
        assertEquals(expected, actual);
    }

    @Test
    public void test() {
        testing(DoubleLinear.dblLinear(6), 22);
        testing(DoubleLinear.dblLinear(7), 22);
        testing(DoubleLinear.dblLinear(10), 22);
        testing(DoubleLinear.dblLinear(15), 40);
        testing(DoubleLinear.dblLinear(20), 57);
        testing(DoubleLinear.dblLinear(25), 79);
        testing(DoubleLinear.dblLinear(30), 91);
        testing(DoubleLinear.dblLinear(50), 175);
    }

    @Test
    public void shouldDoExamples() throws Exception {
//        testing(DoubleLinear.dblLinear(0), 1);
        testing(DoubleLinear.dblLinear(1), 3);
        testing(DoubleLinear.dblLinear(2), 4);
        testing(DoubleLinear.dblLinear(3), 7);
        testing(DoubleLinear.dblLinear(4), 9);
    }

    @Test
    public void shouldDoFixedTests() throws Exception {
        testing(DoubleLinear.dblLinear(500), 3355);
    }

    @Test
    public void shouldDoRandomTests() throws Exception {
        testing(DoubleLinear.dblLinear(10404), 163819);
    }

    @Test
    public void shouldCalculateLimit() throws Exception {
        assertThat(limit(0)).isEqualTo(1);
        assertThat(limit(1)).isEqualTo(2);
        assertThat(limit(2)).isEqualTo(5);
        assertThat(limit(3)).isEqualTo(4);
        assertThat(limit(4)).isEqualTo(5);
        assertThat(limit(5)).isEqualTo(12); // 8 + 8/2
        assertThat(limit(6)).isEqualTo(12);
        assertThat(limit(7)).isEqualTo(8);
        assertThat(limit(8)).isEqualTo(9);
        assertThat(limit(9)).isEqualTo(10);
        assertThat(limit(10)).isEqualTo(11);
        assertThat(limit(11)).isEqualTo(24); // 16 + 16/2
        assertThat(limit(12)).isEqualTo(24);
        assertThat(limit(13)).isEqualTo(24);
        assertThat(limit(14)).isEqualTo(24);
        assertThat(limit(15)).isEqualTo(16);
        assertThat(limit(16)).isEqualTo(17);
        assertThat(limit(17)).isEqualTo(18);
        assertThat(limit(18)).isEqualTo(19);
        assertThat(limit(19)).isEqualTo(20);
        assertThat(limit(20)).isEqualTo(21);
        assertThat(limit(21)).isEqualTo(22);
        assertThat(limit(22)).isEqualTo(23);
        assertThat(limit(23)).isEqualTo(24);
        assertThat(limit(24)).isEqualTo(48); // 32 + 32/2
        assertThat(limit(25)).isEqualTo(48);
    }
}
