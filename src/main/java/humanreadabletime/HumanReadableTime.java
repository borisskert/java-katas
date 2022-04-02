package humanreadabletime;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * https://www.codewars.com/kata/52685f7382004e774f0001f7/train/java
 */
public class HumanReadableTime {
    public static String makeReadable(int seconds) {
        Duration duration = Duration.of(seconds, ChronoUnit.SECONDS);

        return String.format(
                "%02d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
        );
    }
}
