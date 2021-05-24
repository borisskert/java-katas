package themillionthfibonaccikata;

import java.math.BigInteger;

/**
 * https://www.codewars.com/kata/53d40c1e2f13e331fc000c26/train/java
 */
public class Fibonacci {

    public static BigInteger fib(BigInteger n) {
        if (isNegative(n)) {
            return logarithmicFibonacci(
                    BigInteger.ZERO,
                    BigInteger.ONE.negate(),
                    BigInteger.ZERO,
                    BigInteger.ONE.negate(),
                    n.negate()
            );
        }

        return logarithmicFibonacci(
                BigInteger.ZERO,
                BigInteger.ONE,
                BigInteger.ZERO,
                BigInteger.ONE,
                n
        );
    }

    /**
     * https://rybczak.net/2015/11/01/calculation-of-fibonacci-numbers-in-logarithmic-number-of-steps/
     */
    private static BigInteger logarithmicFibonacci(
            BigInteger a,
            BigInteger b,
            BigInteger p,
            BigInteger q,
            BigInteger k
    ) {
        if (k.equals(BigInteger.ZERO)) {
            return a;
        }

        if (isEven(k)) {
            BigInteger nextP = p.pow(2).add(q.pow(2));
            BigInteger nextQ = p.multiply(BigInteger.TWO).add(q).multiply(q);
            BigInteger nextK = k.divide(BigInteger.TWO);

            return logarithmicFibonacci(
                    a,
                    b,
                    nextP,
                    nextQ,
                    nextK
            );
        } else {
            BigInteger nextA = p.multiply(a).add(q.multiply(b));
            BigInteger nextB = q.multiply(a).add(b.multiply(p.add(q)));
            BigInteger nextK = k.subtract(BigInteger.ONE);

            return logarithmicFibonacci(
                    nextA,
                    nextB,
                    p,
                    q,
                    nextK
            );
        }
    }

    private static boolean isEven(BigInteger number) {
        return number.getLowestSetBit() != 0;
    }

    private static boolean isNegative(BigInteger number) {
        return number.signum() < 0;
    }
}
