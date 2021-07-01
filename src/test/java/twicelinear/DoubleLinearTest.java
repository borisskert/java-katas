package twicelinear;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class DoubleLinearTest {

    private static void testing(int actual, int expected) {
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDoExamples() throws Exception {
        testing(DoubleLinear.dblLinear(0), 1);
        testing(DoubleLinear.dblLinear(1), 3);
        testing(DoubleLinear.dblLinear(2), 4);
        testing(DoubleLinear.dblLinear(3), 7);
        testing(DoubleLinear.dblLinear(4), 9);
        testing(DoubleLinear.dblLinear(5), 10);
    }

    @Test
    public void testSixthToTenth() {
        testing(DoubleLinear.dblLinear(6), 13);
        testing(DoubleLinear.dblLinear(7), 15);
        testing(DoubleLinear.dblLinear(8), 19);
        testing(DoubleLinear.dblLinear(9), 21);
        testing(DoubleLinear.dblLinear(10), 22);
    }

    @Test
    public void testEleventhToFifteenth() {
        testing(DoubleLinear.dblLinear(11), 27);
        testing(DoubleLinear.dblLinear(12), 28);
        testing(DoubleLinear.dblLinear(13), 31);
        testing(DoubleLinear.dblLinear(14), 39);
        testing(DoubleLinear.dblLinear(15), 40);
    }

    @Test
    public void testBeyondFifteenth() throws Exception {
        testing(DoubleLinear.dblLinear(20), 57);
        testing(DoubleLinear.dblLinear(25), 79);
        testing(DoubleLinear.dblLinear(30), 91);
        testing(DoubleLinear.dblLinear(50), 175);
    }

    @Test
    public void shouldDoFixedTests() throws Exception {
        testing(DoubleLinear.dblLinear(500), 3355);
    }

    @Test
    @Ignore
    public void shouldDoForHugeNumbers() throws Exception {
        testing(DoubleLinear.dblLinear(60000), 1511311);
    }

    @Test
    public void shouldDoRandomTests() throws Exception {
        testing(DoubleLinear.dblLinear(10404), 163819);
        testing(DoubleLinear.dblLinear(11450), 185176);
        testing(DoubleLinear.dblLinear(14732), 251895);
    }

    @Test
    public void shouldCalculateBits() throws Exception {
        assertThat(MathUtil.bits(0)).isEqualTo(1);
        assertThat(MathUtil.bits(1)).isEqualTo(1);
        assertThat(MathUtil.bits(2)).isEqualTo(2);
        assertThat(MathUtil.bits(3)).isEqualTo(2);
        assertThat(MathUtil.bits(4)).isEqualTo(3);
        assertThat(MathUtil.bits(5)).isEqualTo(3);
        assertThat(MathUtil.bits(6)).isEqualTo(3);
        assertThat(MathUtil.bits(7)).isEqualTo(3);
        assertThat(MathUtil.bits(8)).isEqualTo(4);
        assertThat(MathUtil.bits(9)).isEqualTo(4);
        assertThat(MathUtil.bits(10)).isEqualTo(4);
        assertThat(MathUtil.bits(11)).isEqualTo(4);
        assertThat(MathUtil.bits(12)).isEqualTo(4);
        assertThat(MathUtil.bits(13)).isEqualTo(4);
        assertThat(MathUtil.bits(14)).isEqualTo(4);
        assertThat(MathUtil.bits(15)).isEqualTo(4);
        assertThat(MathUtil.bits(16)).isEqualTo(5);
        assertThat(MathUtil.bits(17)).isEqualTo(5);
        assertThat(MathUtil.bits(18)).isEqualTo(5);
        assertThat(MathUtil.bits(19)).isEqualTo(5);
        assertThat(MathUtil.bits(20)).isEqualTo(5);
        assertThat(MathUtil.bits(21)).isEqualTo(5);
        assertThat(MathUtil.bits(22)).isEqualTo(5);
        assertThat(MathUtil.bits(23)).isEqualTo(5);
        assertThat(MathUtil.bits(24)).isEqualTo(5);
        assertThat(MathUtil.bits(25)).isEqualTo(5);
        assertThat(MathUtil.bits(26)).isEqualTo(5);
        assertThat(MathUtil.bits(27)).isEqualTo(5);
        assertThat(MathUtil.bits(28)).isEqualTo(5);
        assertThat(MathUtil.bits(29)).isEqualTo(5);
        assertThat(MathUtil.bits(30)).isEqualTo(5);
        assertThat(MathUtil.bits(31)).isEqualTo(5);
    }
}
