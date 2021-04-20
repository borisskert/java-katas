package blocksequence;

class Solution{
    public static int solve(long n){
        return y(0, n);
    }

    public static int y(long x){
        int logBase = (int)Math.max(Math.log10(x - 1), 0.0);
        int base = (int)Math.pow(10, logBase);

        return (int)(( x * x ) / 2 - x / 2 + 1)
                + logBase
                * ((int)(( (x - 10) * (x - 10) ) / 2 - (x - 10) / 2 + x - 10));
    }

    private static int y(int i, long n) {
        int offset = mitternachtOffset(n);

        if(mitternachtCount(n) >= 10) {
            if(offset >= 10) {
                if(offset % 2 == 0)
                    return 1;

                return (offset - 10 + 1) / 2 - 1;
            }

            return offset - 1;
        }

        return offset;
    }

    public static long x(long y) {
        double log10 = Math.log10(y);
        double m = mitternacht(y);
        double x = m + 1;

        if((long)x == 11L) {
            return (long)x;
        }

        return (long)x;
    }

    public static double mitternacht(double y) {
        return -0.5 + Math.sqrt(0.25 + 2 * (y - 1));
    }

    public static double invMitternacht(double x) {
        return (Math.pow(x, 2.0) + x) / 2.0 + 1.0;
    }

    public static double mitternachtCount(double y) {
        double mitternacht = mitternacht(y);
        return (int)mitternacht + 1;
    }

    public static double mitternachtBase(double y) {
        return invMitternacht(mitternachtCount(y) - 1);
    }

    public static int mitternachtOffset(double y) {
        return (int)(y - mitternachtBase(y)) + 1;
    }

    public static double schmarrn(long y) {
        double m = mitternacht(y);

        return Math.log10(m);
    }
}
