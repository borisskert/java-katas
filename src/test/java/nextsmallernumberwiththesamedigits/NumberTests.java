package nextsmallernumberwiththesamedigits;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberTests {
    @Test
    public void shouldReturnDigitAt() throws Exception {
        assertThat(Number.of(1234).digitAt(0)).isEqualTo(1);
        assertThat(Number.of(1234).digitAt(1)).isEqualTo(2);
        assertThat(Number.of(1234).digitAt(2)).isEqualTo(3);
        assertThat(Number.of(1234).digitAt(3)).isEqualTo(4);
    }

    @Test
    public void shouldSwapDigitsOfTwoDigitNumber() throws Exception {
        Number swappedDigits = Number.of(21).swap(0, 1);
        assertThat(swappedDigits.toLong()).isEqualTo(12);
    }

    @Test
    public void shouldSwapLastTwoDigitsOfThreeDigitNumber() throws Exception {
        Number swappedDigits = Number.of(321).swap(1, 2);
        assertThat(swappedDigits.toLong()).isEqualTo(312);
    }

    @Test
    public void shouldSwapFirstTwoDigitsOfThreeDigitNumber() throws Exception {
        Number swappedDigits = Number.of(321).swap(0, 1);
        assertThat(swappedDigits.toLong()).isEqualTo(231);
    }

    @Test
    public void should() throws Exception {
        assertThat(Number.of(7379661).swap(2, 4)).isEqualTo(Number.of(7369761));
    }

    @Test
    public void shouldReturnSubNumber() throws Exception {
        assertThat(Number.of(12345).sub(0)).isEqualTo(Number.of(12345));
        assertThat(Number.of(12345).sub(1)).isEqualTo(Number.of(2345));
        assertThat(Number.of(12345).sub(2)).isEqualTo(Number.of(345));
        assertThat(Number.of(12345).sub(3)).isEqualTo(Number.of(45));
        assertThat(Number.of(12345).sub(4)).isEqualTo(Number.of(5));
    }
}
