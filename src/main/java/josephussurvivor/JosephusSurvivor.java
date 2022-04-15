package josephussurvivor;

import java.util.stream.IntStream;

/**
 * <a href="https://www.codewars.com/kata/555624b601231dc7a400017a/train/java">Josephus Survivor</a>
 */
public class JosephusSurvivor {
    public static int josephusSurvivor(final int n, final int k) {
        return IntStream.range(2, n + 1)
                .reduce(1, (j, ni) -> (j + k - 1) % ni + 1);
    }
}
