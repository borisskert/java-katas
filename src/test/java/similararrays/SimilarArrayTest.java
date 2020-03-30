package similararrays;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimilarArrayTest {

    @Test
    public void shouldReturnTrueForSameArray() throws Exception {
        int[] array = {1, 2, 3};
        boolean areSimilar = SimilarArray.areSimilar(array, array);
        assertEquals(true, areSimilar);
    }

    @Test
    public void shouldReturnTrueForTwoEqualArrays() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assertEquals(true, areSimilar);
    }

    @Test
    public void shouldReturnTrueForOneElementSwitched() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{2, 1, 3}, new int[]{1, 2, 3});
        assertEquals(true, areSimilar);
    }

    @Test
    public void shouldReturnTrueForAnotherOneElementSwitched() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{3, 2, 1}, new int[]{1, 2, 3});
        assertEquals(true, areSimilar);
    }

    @Test
    public void shouldReturnTrueForStillAnotherOneElementSwitched() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{1, 3, 2}, new int[]{1, 2, 3});
        assertEquals(true, areSimilar);
    }

    @Test
    public void shouldReturnFalseWhenTwoElementsSwitched() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{3, 1, 2}, new int[]{1, 2, 3});
        assertFalse(areSimilar);
    }

    @Test
    public void shouldReturnFalseForSaltuksArrays() throws Exception {
        boolean areSimilar = SimilarArray.areSimilar(new int[]{1, 2, 2}, new int[]{2, 1, 1});
        assertFalse(areSimilar);
    }
}