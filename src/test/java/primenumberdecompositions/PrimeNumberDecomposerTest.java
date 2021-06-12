package primenumberdecompositions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PrimeNumberDecomposerTest {
    @Test
    public void shouldDecomposeToPrimeFactors() throws Exception {
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(0L)).isEqualTo(new Long[]{});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(2L)).isEqualTo(new Long[]{2L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(3L)).isEqualTo(new Long[]{3L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(4L)).isEqualTo(new Long[]{2L, 2L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(5L)).isEqualTo(new Long[]{5L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(6L)).isEqualTo(new Long[]{2L, 3L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(7L)).isEqualTo(new Long[]{7L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(8L)).isEqualTo(new Long[]{2L, 2L, 2L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(100L)).isEqualTo(new Long[]{2L, 2L, 5L, 5L});
        assertThat(PrimeNumberDecomposer.getAllPrimeFactors(135L)).isEqualTo(new Long[]{3L, 3L, 3L, 5L});
    }

    @Test
    public void shouldDecomposeToUniquePrimeFactorsWithCount() throws Exception {
        assertThat(PrimeNumberDecomposer.getUniquePrimeFactorsWithCount(2L)).isEqualTo(new Long[][]{new Long[]{2L}, new Long[]{1L}});
        assertThat(PrimeNumberDecomposer.getUniquePrimeFactorsWithCount(100L)).isEqualTo(new Long[][]{new Long[]{2L, 5L}, new Long[]{2L, 2L}});
        assertThat(PrimeNumberDecomposer.getUniquePrimeFactorsWithCount(135L)).isEqualTo(new Long[][]{new Long[]{3L, 5L}, new Long[]{3L, 1L}});
    }

    @Test
    public void shouldDecomposeToPrimeFactorPotencies() throws Exception {
        assertThat(PrimeNumberDecomposer.getPrimeFactorPotencies(2L)).isEqualTo(new Long[]{2L});
        assertThat(PrimeNumberDecomposer.getPrimeFactorPotencies(100L)).isEqualTo(new Long[]{4L, 25L});
        assertThat(PrimeNumberDecomposer.getPrimeFactorPotencies(135L)).isEqualTo(new Long[]{27L, 5L});
    }
}
