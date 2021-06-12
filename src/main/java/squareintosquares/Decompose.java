package squareintosquares;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/54eb33e5bc1a25440d000891/train/java
 */
public class Decompose {

    public String decompose(long n) {
        Roots roots = Roots.decompose(n * n, n - 1);

        if (roots.isEmpty()) {
            return null;
        }

        return roots.toString();
    }
}

class Roots {
    private final Set<Long> roots = new HashSet<>();

    private Roots() {
    }

    private Roots(Stream<Long> roots) {
        roots.forEach(this.roots::add);
    }

    Roots with(long root) {
        Stream<Long> concatenatedRoots = Stream.concat(
                roots.stream(),
                Stream.of(root)
        );
        return new Roots(concatenatedRoots);
    }

    Long squareSum() {
        long squareSum = 0L;

        for (Long root : roots) {
            squareSum += (root * root);
        }

        return squareSum;
    }

    boolean isEmpty() {
        return roots.isEmpty();
    }

    @Override
    public String toString() {
        StringJoiner spaceJoiner = new StringJoiner(" ");

        roots.stream()
                .sorted()
                .forEach(root -> spaceJoiner.add(root.toString()));

        return spaceJoiner.toString();
    }

    static Roots empty() {
        return new Roots();
    }

    static Roots of(long root) {
        return new Roots(Stream.of(root));
    }

    static Roots decompose(long square, long root) {
        if (square == 0L || root == 0L) {
            return Roots.empty();
        }

        if (square == 1L) {
            return Roots.of(1L);
        }

        if (root * root == square) {
            return Roots.of(root);
        }

        while (root * root > square) {
            root -= 1;
        }

        Roots roots = decompose(square - root * root, root - 1);
        if (roots.isEmpty()) {
            return decompose(square, root - 1);
        }

        if (roots.with(root).squareSum() != square) {
            return Roots.empty();
        }

        return roots.with(root);
    }
}
