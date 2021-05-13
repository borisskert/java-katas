package codewarsstylerankingsystem;

import java.util.Arrays;

/**
 * https://www.codewars.com/kata/51fda2d95d6efda45e00004e/solutions/java
 */
public class User {
    public int rank = -8;
    public int progress = 0;

    public void incProgress(int incRank) {
        if(incRank == 0 || incRank < -8 || incRank > 8) {
            throw new IllegalArgumentException();
        }

        upgradeProgress(incRank);
        upgradeRank();

        if(this.rank == 8) {
            this.progress = 0;
        }
    }

    private void upgradeProgress(int incRank) {
        Rank userRank = Rank.of(this.rank);
        Rank activityRank = Rank.of(incRank);

        this.progress += userRank.progress(activityRank);
    }

    private void upgradeRank() {
        while (this.progress >= 100) {
            this.progress -= 100;

            this.rank = Rank.of(this.rank)
                    .next()
                    .rank;
        }
    }

    enum Rank {
        MINUS_EIGHT(-8, 0),
        MINUS_SEVEN(-7, 1),
        MINUS_SIX(-6, 2),
        MINUS_FIVE(-5, 3),
        MINUS_FOUR(-4, 4),
        MINUS_THREE(-3, 5),
        MINUS_TWO(-2, 6),
        MINUS_ONE(-1, 7),
        PLUS_ONE(1, 8),
        PLUS_TWO(2, 9),
        PLUS_THREE(3, 10),
        PLUS_FOUR(4, 11),
        PLUS_FIVE(5, 12),
        PLUS_SIX(6, 13),
        PLUS_SEVEN(7, 14),
        PLUS_EIGHT(8, 15);

        private final int rank;
        private final int value;

        Rank(int rank, int value) {
            this.rank = rank;
            this.value = value;
        }

        int progress(Rank other) {
            int progress = 0;
            int difference = other.value - this.value;

            if(difference == 0) {
                progress = 3;
            } else if(difference == -1) {
                progress = 1;
            }
            else if(difference > 0) {
                progress = difference * difference * 10;
            }

            return progress;
        }

        Rank next() {
            if(this == Rank.MINUS_ONE) {
                return Rank.PLUS_ONE;
            }
            if(this == Rank.PLUS_EIGHT) {
                return this;
            }

            return of(this.rank + 1);
        }

        static Rank of(int rank) {
            return Arrays.stream(Rank.values())
                    .sequential()
                    .filter(r -> r.rank == rank)
                    .findFirst()
                    .orElseThrow();
        }
    }
}
