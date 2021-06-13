package pyramidslidedown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/551f23362ff852e2ab000037/train/java
 */
public class LongestSlideDown {

    public static int longestSlideDown(int[][] pyramid) {
        SlideDownRow slideDownRow = SlideDownRow.empty();

        for (int index = pyramid.length - 1; index >= 0; index--) {
            int[] row = pyramid[index];

            slideDownRow = slideDownRow.next(row);
        }

        return slideDownRow.slideDown();
    }
}

class SlideDownRow {
    private final List<Integer> values;

    private SlideDownRow(List<Integer> values) {
        this.values = values;
    }

    SlideDownRow next(int[] row) {
        if (values.isEmpty()) {
            return SlideDownRow.of(row);
        }

        List<Integer> newSlideDown = new ArrayList<>(row.length);
        for (int index = 0; index < row.length; index++) {
            int rowValue = row[index];

            Integer first = values.get(index);
            Integer second = values.get(index + 1);

            Integer newValue = rowValue + Math.max(first, second);
            newSlideDown.add(newValue);
        }

        return new SlideDownRow(newSlideDown);
    }

    int slideDown() {
        return values.get(0);
    }

    static SlideDownRow empty() {
        return new SlideDownRow(List.of());
    }

    static SlideDownRow of(int[] row) {
        List<Integer> newSlideDown = Arrays.stream(row)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
        return new SlideDownRow(newSlideDown);
    }
}
