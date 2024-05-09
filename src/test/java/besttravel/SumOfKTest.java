package besttravel;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SumOfKTest {

    @Test
    public void BasicTests1() {
        System.out.println("****** Basic Tests small numbers******");
        List<Integer> ts = new ArrayList<>(Arrays.asList(50, 55, 56, 57, 58));
        int n = SumOfK.chooseBestSum(163, 3, ts);
        assertEquals(163, n);
        ts = new ArrayList<>(Arrays.asList(50));
        Integer m = SumOfK.chooseBestSum(163, 3, ts);
        assertEquals(null, m);
        ts = new ArrayList<>(Arrays.asList(91, 74, 73, 85, 73, 81, 87));
        n = SumOfK.chooseBestSum(230, 3, ts);
        assertEquals(228, n);
    }

    @Test
    @Ignore("Performance test")
    public void performanceTest() {
        List<Integer> ts = new ArrayList<>(Arrays.asList(
                91, 74, 73, 85, 73, 81, 87, 45, 76, 12, 87, 97, 54, 34, 87, 55, 22, 54, 83, 56, 36, 34, 28, 57, 86, 64,
                93, 73, 64, 75, 91, 74, 53, 92, 63, 66, 48, 93, 63, 82, 99, 65, 21, 65, 10, 70, 40, 30
        ));
        long start = System.currentTimeMillis();
        Integer n = SumOfK.chooseBestSum(331, 6, ts);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        assertEquals(331, n.intValue());
    }


    @Test
    @Ignore("Performance test (< 6 seconds on M3 Pro)")
    public void hardPerformanceTest() {
        List<Integer> ts = new ArrayList<>(Arrays.asList(
                931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897, 455, 242, 564, 873, 586, 336,
                934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636, 428, 943, 653, 862, 929, 645,
                251, 665, 120, 750, 420, 340, 931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897,
                455, 242, 564, 873, 586, 336, 934, 208, 557, 876, 644
        ));
        long start = System.currentTimeMillis();
        Integer n = SumOfK.chooseBestSum(3198, 5, ts);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        assertEquals(3198, n.intValue());
    }

    @Test
    @Ignore("Performance test (~ 25 seconds on M3 Pro)")
    public void harderPerformanceTest() {
        List<Integer> ts = new ArrayList<>(Arrays.asList(
                931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897, 455, 242, 564, 873, 586, 336,
                934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636, 428, 943, 653, 862, 929, 645,
                251, 665, 120, 750, 420, 340, 931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897,
                455, 242, 564, 873, 586, 336, 934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636
        ));
        long start = System.currentTimeMillis();
        Integer n = SumOfK.chooseBestSum(3198, 5, ts);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        assertEquals(3198, n.intValue());
    }

    @Test
    @Ignore("Performance test (~ 1 minute on M3 Pro)")
    public void evenHarderPerformanceTest() {
        List<Integer> ts = new ArrayList<>(Arrays.asList(
                931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897, 455, 242, 564, 873, 586, 336,
                934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636, 428, 943, 653, 862, 929, 645,
                251, 665, 120, 750, 420, 340, 931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897,
                455, 242, 564, 873, 586, 336, 934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636,
                428, 943, 653, 862, 929, 645, 251, 665, 120, 750
        ));
        long start = System.currentTimeMillis();
        Integer n = SumOfK.chooseBestSum(3198, 5, ts);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        assertEquals(3198, n.intValue());
    }

    @Test
    @Ignore("Performance test (~ 6 minutes on M3 Pro)")
    public void hardestPerformanceTest() {
        List<Integer> ts = new ArrayList<>(Arrays.asList(
                931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897, 455, 242, 564, 873, 586, 336,
                934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636, 428, 943, 653, 862, 929, 645,
                251, 665, 120, 750, 420, 340, 931, 744, 763, 825, 713, 851, 867, 435, 726, 112, 847, 697, 547, 384, 897,
                455, 242, 564, 873, 586, 336, 934, 208, 557, 876, 644, 934, 753, 664, 725, 941, 754, 563, 972, 683, 636,
                428, 943, 653, 862, 929, 645, 251, 665, 120, 750, 420, 340, 931, 744, 763, 825, 713, 851, 867, 435, 726
        ));
        long start = System.currentTimeMillis();
        Integer n = SumOfK.chooseBestSum(3198, 5, ts);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        assertEquals(3198, n.intValue());
    }
}
