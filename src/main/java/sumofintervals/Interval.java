package sumofintervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Interval {
    public static int sumIntervals(int[][] intervals) {
        if (intervals == null) {
            return 0;
        }

        MyIntervals myIntervals = MyIntervals.from(intervals);

        return myIntervals.sum();
    }

    public static class MyIntervals {
        private final List<MyInterval> intervals = new ArrayList<>();

        public void add(MyInterval interval) {
            intervals.add(interval);

            Collections.sort(intervals);

            mergeIntervals();
        }

        private void mergeIntervals() {
            for (int index = 1; index < intervals.size(); index++) {
                MyInterval first = intervals.get(index - 1);
                MyInterval second = intervals.get(index);

                if (first.isMergeableWith(second)) {
                    intervals.remove(index);
                    intervals.remove(index - 1);

                    MyInterval merged = first.mergeWith(second);
                    intervals.add(merged);

                    Collections.sort(intervals);
                    mergeIntervals();

                    break;
                }
            }
        }

        public int sum() {
            int sum = 0;

            for (MyInterval myInterval : intervals) {
                sum += myInterval.sum();
            }

            return sum;
        }

        public static MyIntervals from(int[][] intervals) {
            MyIntervals myIntervals = new MyIntervals();

            for (int[] interval : intervals) {
                MyInterval myInterval = MyInterval.createFrom(interval);
                myIntervals.add(myInterval);
            }

            return myIntervals;
        }
    }

    public static class MyInterval implements Comparable<MyInterval> {
        private final int start;
        private final int end;

        private MyInterval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int sum() {
            return end - start;
        }

        public boolean isMergeableWith(MyInterval interval) {
            return this.start <= interval.start && this.end >= interval.end
                    || this.start >= interval.end
                    || this.end >= interval.start;
        }

        public MyInterval mergeWith(MyInterval interval) {
            int start = Math.min(this.start, interval.start);
            int end = Math.max(this.end, interval.end);

            return new MyInterval(start, end);
        }

        public static MyInterval createFrom(int[] interval) {
            return new MyInterval(interval[0], interval[1]);
        }

        @Override
        public int compareTo(MyInterval o) {
            int startCompare = Integer.compare(this.start, o.start);
            int endCompare = Integer.compare(this.end, o.end);

            if (startCompare == 0) {
                return endCompare;
            }

            return startCompare;
        }
    }
}
