package createphonenumber;

import static java.util.Arrays.stream;

/**
 * <a href="https://www.codewars.com/kata/525f50e3b73515a6db000b83/train/java">Create Phone Number</a>
 */
public class Kata {
    public static String createPhoneNumber(int[] numbers) {
        Object[] args = stream(numbers)
                .boxed()
                .toArray();

        return String.format("(%d%d%d) %d%d%d-%d%d%d%d", args);
    }
}
