package mysteryfunction;

import java.math.BigInteger;

public class MysteryFunction {
    private static final BigInteger TWO = BigInteger.valueOf(2L);

    public static long mystery(long index) {
        if(index == 0L) {
            return 0L;
        }

        BigInteger lookupIndex = BigInteger.valueOf(index);
        BigInteger result = BigInteger.ZERO;

        do {
            int bits = lengthOf(lookupIndex);
            BigInteger size = pow(TWO, bits);
            BigInteger offset = size.shiftRight(1);
            BigInteger maxIndex = offset.subtract(BigInteger.ONE);
            BigInteger offsetIndex = lookupIndex.mod(offset);

            lookupIndex = maxIndex.subtract(offsetIndex);
            result = result.add(pow(TWO, (bits - 1)));

        } while(lookupIndex.compareTo(BigInteger.ONE) > 0);

        if(lookupIndex.equals(BigInteger.ONE)) {
            result = result.add(BigInteger.ONE);
        }

        return result.longValue();
    }

    private static BigInteger pow(BigInteger base, int exp) {
        return base.pow(exp);
    }

    public static long mysteryInv(long number) {
        BigInteger index = BigInteger.ZERO;
        BigInteger factor = BigInteger.ONE;
        BigInteger previous = BigInteger.valueOf(number);

        while(previous.compareTo(BigInteger.ZERO) > 0) {
            index = index.add(maxIndex(previous).multiply(factor));

            int bits = lengthOf(previous);
            BigInteger size = pow(TWO, bits);

            BigInteger previousSize = size.shiftRight(1);

            previous = previous.subtract(previousSize);
            factor = factor.negate();
        }

        return index.longValue();
    }

    private static BigInteger maxIndex(BigInteger number) {
        int bits = lengthOf(number);
        BigInteger size = pow(TWO, bits);

        return size.subtract(BigInteger.ONE);
    }

    public static int lengthOf(BigInteger number) {
        return number.bitLength();
    }

    public static String nameOfMystery() {
        return "";
    }
}
