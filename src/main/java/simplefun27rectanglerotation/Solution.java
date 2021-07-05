package simplefun27rectanglerotation;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/5886e082a836a691340000c3/train/java
 */
class Solution {

    static int rectangleRotation(final int a, final int b) {
        Tuple tuple = Tuple.of(a, b);

        return Stream.of(
                new SpecialCase(),
                new Default()
        )
                .filter(s -> s.test(tuple))
                .map(s -> s.count(tuple))
                .findFirst()
                .orElseThrow();
    }
}

class Tuple {
    private final int a;
    private final int b;

    private Tuple(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    static Tuple of(int a, int b) {
        return new Tuple(a, b);
    }
}

interface Strategy extends Predicate<Tuple> {
    double SQUARE_ROOT_OF_TWO = Math.sqrt(2.0);

    Integer count(Tuple tuple);
}

class SpecialCase implements Strategy {

    @Override
    public Integer count(Tuple tuple) {
        double aDivided = tuple.a() / SQUARE_ROOT_OF_TWO;
        double bDivided = tuple.b() / SQUARE_ROOT_OF_TWO;

        return (int) (aDivided)
                * (int) (bDivided + 1)
                + (int) (aDivided + 1)
                * (int) (bDivided);
    }

    @Override
    public boolean test(Tuple tuple) {
        double aDivided = tuple.a() / SQUARE_ROOT_OF_TWO;
        double bDivided = tuple.b() / SQUARE_ROOT_OF_TWO;

        return (int) aDivided % 2 != 0 && (int) bDivided % 2 == 0
                || (int) aDivided % 2 == 0 && (int) bDivided % 2 != 0;
    }
}

class Default implements Strategy {

    @Override
    public Integer count(Tuple tuple) {
        double aDivided = tuple.a() / SQUARE_ROOT_OF_TWO;
        double bDivided = tuple.b() / SQUARE_ROOT_OF_TWO;

        return (int) (aDivided + 1)
                * (int) (bDivided + 1)
                + (int) (aDivided)
                * (int) (bDivided);
    }

    @Override
    public boolean test(Tuple tuple) {
        return true;
    }
}
