package greedisgood;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * <a href="https://www.codewars.com/kata/5270d0d18625160ada0000e4/train/java">Greed is Good</a>
 */
public class Greed {
    private static final List<Rule> rules = List.of(
            new SpecificCountRule(1, 3, 1000),
            new SpecificCountRule(6, 3, 600),
            new SpecificCountRule(5, 3, 500),
            new SpecificCountRule(4, 3, 400),
            new SpecificCountRule(3, 3, 300),
            new SpecificCountRule(2, 3, 200),
            new SpecificCountRule(1, 1, 100),
            new SpecificCountRule(5, 1, 50)
    );

    private final int[] dice;
    private final int score;

    private Greed(int[] dice, int score) {
        this.dice = dice;
        this.score = score;
    }

    private Greed findScore() {
        return rules.stream()
                .filter(rule -> rule.test(dice))
                .findFirst()
                .map(rule -> {
                    int newScore = rule.score() + score;
                    int[] nextDice = rule.remaining(dice);

                    return new Greed(nextDice, newScore)
                            .findScore();
                })
                .orElse(this);
    }

    public static int greedy(int[] dice) {
        return new Greed(dice, 0)
                .findScore()
                .score;
    }
}

interface Rule extends Predicate<int[]> {
    int score();

    int[] remaining(int[] dice);
}

class SpecificCountRule implements Rule {
    private final int value;
    private final int count;
    private final int score;

    SpecificCountRule(int value, int count, int score) {
        this.value = value;
        this.count = count;
        this.score = score;
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public int[] remaining(int[] dice) {
        int[] others = Arrays.stream(dice)
                .filter(i -> i != value)
                .toArray();

        IntStream limitedValues = Arrays.stream(dice)
                .filter(i -> i == value)
                .limit(dice.length - others.length - count);

        return IntStream.concat(
                Arrays.stream(others),
                limitedValues
        ).toArray();
    }

    @Override
    public boolean test(int[] dice) {
        return Arrays.stream(dice)
                .filter(i -> i == value)
                .toArray()
                .length >= count;
    }
}
