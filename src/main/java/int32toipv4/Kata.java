package int32toipv4;

import java.util.StringJoiner;

/**
 * https://www.codewars.com/kata/52e88b39ffb6ac53a400022e/train/java
 */
public class Kata {
    public static String longToIP(long ip) {
        return IpAddress.of(ip)
                .toString();
    }
}

class IpAddress {
    private final Octet first;
    private final Octet second;
    private final Octet third;
    private final Octet forth;

    private IpAddress(Octet first, Octet second, Octet third, Octet forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    @Override
    public String toString() {
        StringJoiner dotJoiner = new StringJoiner(".");

        dotJoiner.add(first.toString());
        dotJoiner.add(second.toString());
        dotJoiner.add(third.toString());
        dotJoiner.add(forth.toString());

        return dotJoiner.toString();
    }

    static IpAddress of(long value) {
        Octet first = Octet.of(value / (1 << 24));
        Octet second = Octet.of(value / (1 << 16) & 255);
        Octet third = Octet.of(value / (1 << 8) & 255);
        Octet forth = Octet.of(value & 255);

        return new IpAddress(first, second, third, forth);
    }
}

class Octet {
    private final long value;

    private Octet(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    static Octet of(long value) {
        return new Octet(value);
    }
}
