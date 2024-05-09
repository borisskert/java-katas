package besttravel;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://www.codewars.com/kata/55e7280b40e1c4a06d0000aa/train/java">Best travel</a>
 */
public class SumOfK {

    public static Integer chooseBestSum(int t, int k, List<Integer> ls) {
        return Lists.combinations(ls, k)
                .stream()
                .map(Integers::sum)
                .filter(sum -> sum <= t)
                .max(Integer::compareTo)
                .orElse(null);
    }
}

class Lists {
    public static <T> List<List<T>> combinations(List<T> list, int n) {
        return combinations(list, n, List.of());
    }

    // Tail recursive
    private static <T> List<List<T>> combinations(List<T> list, int n, List<T> prefix) {
        if (n == 0) {
            return List.of(prefix);
        }

        if (list.isEmpty()) {
            return List.of();
        }

        T first = list.get(0);
        List<T> rest = list.subList(1, list.size());

        List<List<T>> restCombinations = combinations(rest, n - 1, List.copyOf(prefix));
        List<List<T>> allCombinations = new ArrayList<>();

        for (List<T> restCombination : restCombinations) {
            List<T> comb = new ArrayList<>();
            comb.add(first);
            comb.addAll(restCombination);
            allCombinations.add(comb);
        }

        List<List<T>> remainingCombinations = combinations(rest, n, List.copyOf(prefix));
        allCombinations.addAll(remainingCombinations);

        return List.copyOf(allCombinations);
    }
}

class Integers {
    public static int sum(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }
}
