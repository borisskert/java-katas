package rangeextraction;

import java.util.Stack;
import java.util.StringJoiner;

/**
 * https://www.codewars.com/kata/51ba717bb08c1cd60f00002f/train/java
 */
class Solution {
    public static String rangeExtraction(int[] arr) {
        Ranges ranges = Ranges.empty();

        for (int number : arr) {
            ranges.next(number);
        }

        return ranges.toString();
    }
}

class Ranges {
    private final Stack<Range> ranges;

    private Ranges(Stack<Range> ranges) {
        this.ranges = ranges;
    }

    void next(int number) {
        if (ranges.isEmpty()) {
            ranges.push(Range.of(number));
        } else {
            Range range = ranges.pop();
            if (range.end().equals(number - 1)) {
                ranges.push(range.extend(number));
            } else {
                ranges.push(range);
                ranges.push(Range.of(number));
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");

        for (Range range : ranges) {
            joiner.add(range.toString());
        }

        return joiner.toString();
    }

    static Ranges empty() {
        return new Ranges(new Stack<>());
    }
}

class Range {
    private final Integer start;
    private final Integer end;

    private Range(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer end() {
        return end;
    }

    public Range extend(int end) {
        return new Range(start, end);
    }

    static Range of(int start) {
        return new Range(start, start);
    }

    @Override
    public String toString() {
        if (start.equals(end)) {
            return start.toString();
        }

        if (start.equals(end - 1)) {
            return start + "," + end;
        }

        return start + "-" + end;
    }
}
