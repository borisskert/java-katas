package poppingballoons;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BalloonTest {

    @Test
    void shouldPerformExampleTest() {
        int input[] = {5, 7, 5, 7, 4, 5};
        int expected[] = {5, 7, 5, 4};
        assertEquals(Arrays.stream(expected).boxed().toList(), Balloon.poppingBalloons(4, input));
    }

    @Test
    void shouldPerformDuplicate5() {
        int input[] = {1, 2, 3, 4, 5, 5, 7, 8, 9, 10};
        int expected[] = {5, 10, 9, 8, 7, 5, 4, 3, 2, 1};
        assertEquals(Arrays.stream(expected).boxed().toList(), Balloon.poppingBalloons(10, input));
    }

    @Test
    void shouldPerformTest3() {
        int input[] = {1, 1, 1, 10, 5, 5, 10, 7, 8};
        int expected[] = {1, 10, 5, 1, 8};
        assertEquals(Arrays.stream(expected).boxed().toList(), Balloon.poppingBalloons(5, input));
    }


    @Test
    void shouldPerformTest4() {
        int input[] = {12, 9, 8, 8, 12, 12, 6, 1, 8};
        int expected[] = {8, 12, 12, 8, 1, 6, 8};
        assertEquals(Arrays.stream(expected).boxed().toList(), Balloon.poppingBalloons(7, input));
    }

    @Test
    void shouldPerformRandomTest() {
        int input[] = {8, 19, 112, 114, 119, 129, 54, 119, 104, 60, 104, 107, 82, 7, 83, 51, 26, 74, 28, 9, 130, 26, 24, 30, 26, 20, 46, 96, 86, 59, 92, 97, 16, 21, 144, 37, 71, 122, 63, 126, 124, 91, 103, 60, 115, 99, 51, 64, 130, 150, 79, 132, 15, 55, 66, 150, 142, 144, 44, 136, 16, 43, 117, 142, 54, 61, 74, 49, 116, 14, 13, 4, 21, 144, 47, 142, 128, 8, 74, 25, 138, 32, 90, 32, 86, 147, 22, 22, 98, 1, 123, 62, 65, 62, 35, 136, 26, 92, 19, 42, 16, 32, 130, 3, 107, 40, 142, 14, 82, 85, 48, 7, 101, 92, 101, 122, 138, 142, 80, 117, 75, 2, 17, 44, 39, 50, 131, 69, 26, 35, 64, 112, 69, 51, 141, 47, 149, 41, 71, 134, 23, 34, 117, 37, 133, 138, 69, 109, 79, 135, 80, 117, 3, 134, 12, 55, 97, 111, 106, 133, 121, 75, 140, 65, 140, 22, 43, 143, 42, 32, 52, 137, 76, 72, 31, 83, 46, 34, 105, 135, 13, 91, 116, 59, 33, 75, 42, 72, 76, 140, 132, 116, 35, 144, 61, 118, 84, 26, 110, 38, 123, 22, 123, 127, 57, 97, 150, 57, 62, 39, 49, 149, 143, 102, 103, 47, 7, 133, 140, 61, 13, 93, 40, 99, 10, 83, 38, 66, 60, 3, 87, 80, 139, 25, 115, 9, 55, 66, 49, 44, 19, 101, 67, 117, 15, 61, 25, 45, 45, 102, 95, 67, 88, 47, 86, 61, 80, 63, 28, 87, 118, 27, 37, 136, 132, 59, 123, 61, 34, 51, 136, 90, 126, 52, 126, 123, 150, 125, 43, 51, 125, 140, 40, 86, 77, 12, 2, 49, 43, 9, 138, 40, 123, 83, 82, 143, 97, 115, 1, 6, 119, 151, 119, 39, 45, 127, 28, 80, 151, 3, 61, 28, 18, 15, 82, 37, 32, 108, 75, 28, 136, 36, 20, 74, 19, 106, 120, 86, 85, 103, 110, 75, 32, 81, 36, 12, 122, 139, 150, 95, 100, 73, 86, 97, 16, 95, 100, 141, 46, 67, 60, 121, 129, 50, 89, 37, 123, 114, 109, 100, 113, 74, 18, 123, 122, 119, 89, 13, 54, 19, 19, 146, 95, 131, 29, 5, 86, 88, 118, 28, 4, 147, 118, 66, 25, 59, 51, 68, 99, 5, 32, 144, 62, 111, 150, 124, 47, 71, 22, 39, 151, 53, 148, 124, 48, 109, 150, 76, 96, 106, 61, 50, 106, 18, 38, 19, 101, 91, 24, 104, 71, 59, 104, 112, 7, 35, 149, 82, 94, 65, 29, 71, 27, 92, 66, 56, 44, 99, 40, 93, 124, 135, 122, 127, 9, 107, 45, 74, 121, 72, 32, 141, 121, 22, 71};
        int expected[] = {32, 61, 123, 19, 150, 32, 86, 123, 61, 71, 22, 74, 150, 51, 28, 19, 86, 32, 123, 61, 26, 122, 40, 66, 71, 82, 59, 22, 47, 144, 19, 119, 74, 37, 97, 150, 75, 86, 136, 28, 32, 80, 140, 51, 123, 61, 117, 26, 142, 121, 45, 9, 124, 99, 44, 92, 35, 7, 104, 71, 101, 106, 39, 62, 59, 25, 66, 118, 95, 13, 122, 60, 16, 19, 74, 75, 37, 82, 28, 3, 119, 97, 83, 40, 138, 43, 49, 86, 150, 136, 51, 123, 80, 47, 61, 140, 22, 144, 32, 117, 142, 26, 141, 72, 121, 107, 127, 135, 65, 149, 112, 104, 91, 38, 18, 50, 106, 76, 109, 124, 151, 71, 99, 118, 54, 100, 67, 46, 95, 122, 12, 103, 15, 28, 45, 39, 119, 115, 143, 82, 9, 40, 43, 126, 34, 59, 132, 136, 37, 86, 25, 101, 19, 44, 49, 66, 55, 80, 3, 60, 83, 13, 61, 133, 7, 47, 62, 150, 97, 123, 35, 116, 140, 42, 75, 22, 69, 138, 117, 51, 92, 130, 32, 16, 74, 142, 144, 26, 93, 27, 29, 24, 96, 48, 124, 111, 5, 147, 4, 88, 131, 89, 18, 109, 114, 50, 129, 121, 141, 100, 95, 139, 36, 110, 85, 106, 20, 151, 127, 1, 2, 12, 125, 52, 126, 90, 118, 87};

        List<Integer> actual = Balloon.poppingBalloons(228, input);
        assertThat(actual).isEqualTo(Arrays.stream(expected).boxed().toList());
    }
}