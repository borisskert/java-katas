package asimplemusicencoder;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoolListTest {

    @Test
    void shouldZipWithIndices() throws Exception {
        assertThat(
                CoolList.from(List.of(1, 5, 9, 4)).zipWithIndex()
        ).isEqualTo(
                CoolList.from(List.of(new Pair<>(0, 1), new Pair<>(1, 5), new Pair<>(2, 9), new Pair<>(3, 4)))
        );
    }

    @Test
    void shouldUniqueValues() throws Exception {
        assertThat(
                CoolList.from(List.of(2, 1, 2, 2, 3, 1, 4, 2, 3, 6, 6, 5, 6, 5, 7)).nubBy(Comparator.comparingInt(o -> o))
        ).isEqualTo(
                CoolList.from(List.of(2, 1, 3, 4, 6, 5, 7))
        );
    }
}
