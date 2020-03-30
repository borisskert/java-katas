package similararrays;

import java.util.Objects;

public class SimilarArray {

    public static boolean areSimilar(int[] a, int[] b) {
        if (a == b) {
            return true;
        }

        if (a.length != b.length) {
            return false;
        }

        Pair first = Pair.empty();
        Pair second = Pair.empty();

        for (int index = 0; index < a.length; index++) {
            int valueA = a[index];
            int valueB = b[index];

            if (valueA != valueB) {
                if (first.isEmpty()) {
                    first = Pair.of(valueA, valueB);
                } else if (second.isEmpty()) {
                    second = Pair.of(valueA, valueB);
                } else {
                    return false;
                }
            }
        }

        return first.equals(second);
    }
}

class Pair {
    private final Integer a;
    private final Integer b;

    private Pair(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public boolean isEmpty() {
        return a == null && b == null;
    }

    public static Pair of(int a, int b) {
        return new Pair(a, b);
    }

    public static Pair empty() {
        return new Pair(null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b) ||
                Objects.equals(a, pair.b) && Objects.equals(b, pair.a);
    }
}