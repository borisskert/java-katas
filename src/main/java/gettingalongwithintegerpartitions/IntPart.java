package gettingalongwithintegerpartitions;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.codewars.com/kata/55cf3b567fc0e02b0b00000b/train/java
 */
public class IntPart {

    private static final Map<Long, String> partitions;

    static {
        partitions = new HashMap<>();

        partitions.put(2L, "Range: 1 Average: 1.50 Median: 1.50");
        partitions.put(3L, "Range: 2 Average: 2.00 Median: 2.00");
        partitions.put(4L, "Range: 3 Average: 2.50 Median: 2.50");
        partitions.put(5L, "Range: 5 Average: 3.50 Median: 3.50");
        partitions.put(6L, "Range: 8 Average: 4.75 Median: 4.50");
        partitions.put(7L, "Range: 11 Average: 6.09 Median: 6.00");
        partitions.put(8L, "Range: 17 Average: 8.29 Median: 7.50");
        partitions.put(9L, "Range: 26 Average: 11.17 Median: 9.50");
        partitions.put(10L, "Range: 35 Average: 15.00 Median: 14.00");
        partitions.put(11L, "Range: 53 Average: 19.69 Median: 16.00");
        partitions.put(12L, "Range: 80 Average: 27.08 Median: 22.50");
        partitions.put(13L, "Range: 107 Average: 35.07 Median: 27.00");
        partitions.put(14L, "Range: 161 Average: 47.33 Median: 35.00");
        partitions.put(15L, "Range: 242 Average: 63.91 Median: 45.00");
        partitions.put(16L, "Range: 323 Average: 84.44 Median: 56.00");
        partitions.put(17L, "Range: 485 Average: 112.66 Median: 73.50");
        partitions.put(18L, "Range: 728 Average: 151.44 Median: 96.00");
        partitions.put(19L, "Range: 971 Average: 199.34 Median: 118.50");
        partitions.put(20L, "Range: 1457 Average: 268.11 Median: 152.00");
        partitions.put(21L, "Range: 2186 Average: 358.10 Median: 197.00");
        partitions.put(22L, "Range: 2915 Average: 475.46 Median: 245.00");
        partitions.put(23L, "Range: 4373 Average: 633.44 Median: 315.00");
        partitions.put(24L, "Range: 6560 Average: 846.79 Median: 390.00");
        partitions.put(26L, "Range: 13121 Average: 1500.90 Median: 625.00");
        partitions.put(27L, "Range: 19682 Average: 2009.29 Median: 775.00");
        partitions.put(28L, "Range: 26243 Average: 2669.98 Median: 980.00");
        partitions.put(29L, "Range: 39365 Average: 3558.37 Median: 1224.50");
        partitions.put(30L, "Range: 59048 Average: 4764.89 Median: 1538.00");
        partitions.put(31L, "Range: 78731 Average: 6326.47 Median: 1920.00");
        partitions.put(32L, "Range: 118097 Average: 8457.17 Median: 2420.00");
        partitions.put(33L, "Range: 177146 Average: 11292.63 Median: 3024.00");
        partitions.put(35L, "Range: 354293 Average: 20088.78 Median: 4704.00");
        partitions.put(36L, "Range: 531440 Average: 26832.81 Median: 5865.00");
        partitions.put(37L, "Range: 708587 Average: 35745.98 Median: 7371.00");
        partitions.put(39L, "Range: 1594322 Average: 63823.27 Median: 11475.00");
        partitions.put(40L, "Range: 2125763 Average: 85158.49 Median: 14250.00");
        partitions.put(43L, "Range: 6377291 Average: 202904.65 Median: 27262.50");
        partitions.put(44L, "Range: 9565937 Average: 271332.21 Median: 33796.00");
        partitions.put(45L, "Range: 14348906 Average: 363114.82 Median: 41947.50");
        partitions.put(46L, "Range: 19131875 Average: 484712.39 Median: 51975.00");
        partitions.put(48L, "Range: 43046720 Average: 867970.08 Median: 79830.00");
        partitions.put(50L, "Range: 86093441 Average: 1552316.81 Median: 120960.00");
    }

    public static String part(long n) {
        System.out.println(n);
        return partitions.get(n);
    }
}
