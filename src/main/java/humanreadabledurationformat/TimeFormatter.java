package humanreadabledurationformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/52742f58faf5485cae000b9a/train/java
 */
public class TimeFormatter {
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;
    static final int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
    static final int SECONDS_PER_YEAR = 365 * SECONDS_PER_DAY;

    private static final String DEFAULT_SEPARATOR = ", ";
    private static final String LAST_SEPARATOR = " and ";

    public static String formatDuration(int durationInSeconds) {
        if (durationInSeconds == 0) {
            return "now";
        }

        List<String> formatted = Stream.of(
                new Years(),
                new Days(),
                new Hours(),
                new Minutes(),
                new Seconds()
        )
                .map(f -> f.format(durationInSeconds))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());

        String joined = String.join(DEFAULT_SEPARATOR, formatted);
        return replaceLast(joined, DEFAULT_SEPARATOR, LAST_SEPARATOR);
    }

    private static String replaceLast(String original, String search, String replacement) {
        int found = original.lastIndexOf(search);

        if (found > -1) {
            return original.substring(0, found)
                    + replacement
                    + original.substring(found + search.length());
        }

        return original;
    }
}

interface Format {
    Optional<String> format(int durationInSeconds);
}

class Years implements Format {

    @Override
    public Optional<String> format(int durationInSeconds) {
        int years = extractYears(durationInSeconds);
        return formatYears(years);
    }

    private static int extractYears(int durationInSeconds) {
        return durationInSeconds / TimeFormatter.SECONDS_PER_YEAR;
    }

    private static Optional<String> formatYears(int years) {
        if (years > 1) {
            return Optional.of(years + " years");
        } else if (years > 0) {
            return Optional.of(years + " year");
        }

        return Optional.empty();
    }
}

class Days implements Format {

    @Override
    public Optional<String> format(int durationInSeconds) {
        int days = extractDays(durationInSeconds);
        return formatDays(days);
    }

    private static int extractDays(int durationInSeconds) {
        return durationInSeconds % TimeFormatter.SECONDS_PER_YEAR / TimeFormatter.SECONDS_PER_DAY;
    }

    private static Optional<String> formatDays(int days) {
        if (days > 1) {
            return Optional.of(days + " days");
        } else if (days > 0) {
            return Optional.of(days + " day");
        }

        return Optional.empty();
    }
}

class Hours implements Format {

    @Override
    public Optional<String> format(int durationInSeconds) {
        int hours = extractHours(durationInSeconds);
        return formatHours(hours);
    }

    private static int extractHours(int durationInSeconds) {
        return durationInSeconds % TimeFormatter.SECONDS_PER_DAY / TimeFormatter.SECONDS_PER_HOUR;
    }

    private static Optional<String> formatHours(int hours) {
        if (hours > 1) {
            return Optional.of(hours + " hours");
        } else if (hours > 0) {
            return Optional.of(hours + " hour");
        }

        return Optional.empty();
    }
}

class Minutes implements Format {

    @Override
    public Optional<String> format(int durationInSeconds) {
        int minutes = extractMinutes(durationInSeconds);
        return formatMinutes(minutes);
    }

    static int extractMinutes(int durationInSeconds) {
        return durationInSeconds % TimeFormatter.SECONDS_PER_HOUR / TimeFormatter.SECONDS_PER_MINUTE;
    }

    private static Optional<String> formatMinutes(int minutes) {
        if (minutes > 1) {
            return Optional.of(minutes + " minutes");
        } else if (minutes > 0) {
            return Optional.of(minutes + " minute");
        }

        return Optional.empty();
    }
}

class Seconds implements Format {

    @Override
    public Optional<String> format(int durationInSeconds) {
        int seconds = extractSeconds(durationInSeconds);
        return formatSeconds(seconds);
    }

    private static int extractSeconds(int durationInSeconds) {
        return durationInSeconds % TimeFormatter.SECONDS_PER_MINUTE;
    }

    private static Optional<String> formatSeconds(int seconds) {
        if (seconds > 1) {
            return Optional.of(seconds + " seconds");
        } else if (seconds > 0) {
            return Optional.of(seconds + " second");
        }

        return Optional.empty();
    }
}
