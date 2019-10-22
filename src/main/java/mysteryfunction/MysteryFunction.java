package mysteryfunction;

import java.math.BigInteger;

public class MysteryFunction {
    private static final BigInteger TWO = BigInteger.valueOf(2L);

    public static long mystery(long index) {
        BigInteger lookupIndex = BigInteger.valueOf(index);
        BigInteger result = BigInteger.ZERO;

        while(lookupIndex.compareTo(BigInteger.ONE) > 0) {
            int bits = lookupIndex.bitLength();
            BigInteger size = TWO.pow(bits);
            BigInteger offset = size.shiftRight(1);
            BigInteger maxIndex = offset.subtract(BigInteger.ONE);
            BigInteger offsetIndex = lookupIndex.mod(offset);

            lookupIndex = maxIndex.subtract(offsetIndex);
            result = result.add(TWO.pow(bits - 1));
        }

        result = result.add(lookupIndex.mod(TWO));

        return result.longValue();
    }

    public static long mysteryInv(long number) {
        BigInteger index = BigInteger.ZERO;
        BigInteger factor = BigInteger.ONE;
        BigInteger previous = BigInteger.valueOf(number);

        while(previous.compareTo(BigInteger.ZERO) > 0) {
            BigInteger maxIndex = maxIndex(previous);
            index = index.add(maxIndex.multiply(factor));

            int bits = previous.bitLength();
            BigInteger size = TWO.pow(bits);

            BigInteger previousSize = size.shiftRight(1);

            previous = previous.subtract(previousSize);
            factor = factor.negate();
        }

        return index.longValue();
    }

    private static BigInteger maxIndex(BigInteger number) {
        int bits = number.bitLength();
        BigInteger size = TWO.pow(bits);

        return size.subtract(BigInteger.ONE);
    }

    public static String nameOfMystery() {
        return "";
    }
}
