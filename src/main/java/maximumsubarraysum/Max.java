package maximumsubarraysum;

import java.util.Arrays;

public class Max {
    public static int sequence(int[] arr) {
        return Arrays.stream(arr)
                .boxed()
                .reduce(
                        Search.create(),
                        Search::examine,
                        Search::combine
                )
                .record();
    }
}

class Search {
    private final int current;
    private final int record;

    private Search(int current, int record) {
        this.current = current;
        this.record = record;
    }

    public Search examine(int value) {
        int nextCurrent = Math.max(current + value, 0);
        int nextRecord = Math.max(nextCurrent, record);

        return new Search(nextCurrent, nextRecord);
    }

    public int record() {
        return record;
    }

    public Search combine(Search other) {
        int nextCurrent = Math.max(current, other.current);
        int nextRecord = Math.max(record, other.record);

        return new Search(nextCurrent, nextRecord);
    }

    public static Search create() {
        return new Search(0, 0);
    }
}
