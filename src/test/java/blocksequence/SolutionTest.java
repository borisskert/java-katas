package blocksequence;

import org.junit.Test;

import static blocksequence.Solution.invMitternacht;
import static blocksequence.Solution.mitternachtBase;
import static blocksequence.Solution.mitternachtCount;
import static blocksequence.Solution.mitternacht;
import static blocksequence.Solution.mitternachtOffset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class SolutionTest{
    @Test
    public void basicTests(){
        assertEquals(1, Solution.solve(1L));
        assertEquals(1, Solution.solve(2L));
        assertEquals(2, Solution.solve(3L));
        assertEquals(1, Solution.solve(100L));
        assertEquals(2, Solution.solve(2100L));
        assertEquals(2, Solution.solve(3100L));
        assertEquals(1, Solution.solve(55L));
        assertEquals(6, Solution.solve(123456L));
        assertEquals(3, Solution.solve(123456789L));
        assertEquals(4, Solution.solve(999999999999999999L));
        assertEquals(1, Solution.solve(1000000000000000000L));
        assertEquals(7, Solution.solve(999999999999999993L));
    }

    @Test
    public void myTests() throws Exception {
        // 1
        assertEquals(1, Solution.solve(1L));

        // 2
        assertEquals(1, Solution.solve(2L));
        assertEquals(2, Solution.solve(3L));

        // 3
        assertEquals(1, Solution.solve(4L));
        assertEquals(2, Solution.solve(5L));
        assertEquals(3, Solution.solve(6L));

        // 4
        assertEquals(1, Solution.solve(7L));
        assertEquals(2, Solution.solve(8L));
        assertEquals(3, Solution.solve(9L));
        assertEquals(4, Solution.solve(10L));

        // 5
        assertEquals(1, Solution.solve(11L));
        assertEquals(2, Solution.solve(12L));
        assertEquals(3, Solution.solve(13L));
        assertEquals(4, Solution.solve(14L));
        assertEquals(5, Solution.solve(15L));

        // 6
        assertEquals(1, Solution.solve(16L));
        assertEquals(2, Solution.solve(17L));
        assertEquals(3, Solution.solve(18L));
        assertEquals(4, Solution.solve(19L));
        assertEquals(5, Solution.solve(20L));
        assertEquals(6, Solution.solve(21L));

        // 7
        assertEquals(1, Solution.solve(22L));
        assertEquals(2, Solution.solve(23L));
        assertEquals(3, Solution.solve(24L));
        assertEquals(4, Solution.solve(25L));
        assertEquals(5, Solution.solve(26L));
        assertEquals(6, Solution.solve(27L));
        assertEquals(7, Solution.solve(28L));

        // 8
        assertEquals(1, Solution.solve(29L));
        assertEquals(2, Solution.solve(30L));
        assertEquals(3, Solution.solve(31L));
        assertEquals(4, Solution.solve(32L));
        assertEquals(5, Solution.solve(33L));
        assertEquals(6, Solution.solve(34L));
        assertEquals(7, Solution.solve(35L));
        assertEquals(8, Solution.solve(36L));

        // 9
        assertEquals(1, Solution.solve(37L));
        assertEquals(2, Solution.solve(38L));
        assertEquals(3, Solution.solve(39L));
        assertEquals(4, Solution.solve(40L));
        assertEquals(5, Solution.solve(41L));
        assertEquals(6, Solution.solve(42L));
        assertEquals(7, Solution.solve(43L));
        assertEquals(8, Solution.solve(44L));
        assertEquals(9, Solution.solve(45L));

        // 10
        assertEquals(1, Solution.solve(46L));
        assertEquals(2, Solution.solve(47L));
        assertEquals(3, Solution.solve(48L));
        assertEquals(4, Solution.solve(49L));
        assertEquals(5, Solution.solve(50L));
        assertEquals(6, Solution.solve(51L));
        assertEquals(7, Solution.solve(52L));
        assertEquals(8, Solution.solve(53L));
        assertEquals(9, Solution.solve(54L));
        assertEquals(1, Solution.solve(55L));
        assertEquals(0, Solution.solve(56L));

        // 11
        assertEquals(1, Solution.solve(57L));
        assertEquals(2, Solution.solve(58L));
        assertEquals(3, Solution.solve(59L));
        assertEquals(4, Solution.solve(60L));
        assertEquals(5, Solution.solve(61L));
        assertEquals(6, Solution.solve(62L));
        assertEquals(7, Solution.solve(63L));
        assertEquals(8, Solution.solve(64L));
        assertEquals(9, Solution.solve(65L));
        assertEquals(1, Solution.solve(66L));
        assertEquals(0, Solution.solve(67L));
        assertEquals(1, Solution.solve(68L));
        assertEquals(1, Solution.solve(69L));

        // 12
        assertEquals(1, Solution.solve(70L));
        assertEquals(2, Solution.solve(71L));
        assertEquals(3, Solution.solve(72L));
        assertEquals(4, Solution.solve(73L));
        assertEquals(5, Solution.solve(74L));
        assertEquals(6, Solution.solve(75L));
        assertEquals(7, Solution.solve(76L));
        assertEquals(8, Solution.solve(77L));
        assertEquals(9, Solution.solve(78L));
        assertEquals(1, Solution.solve(79L));
        assertEquals(0, Solution.solve(80L));
        assertEquals(1, Solution.solve(81L));
        assertEquals(1, Solution.solve(82L));
        assertEquals(1, Solution.solve(83L));
        assertEquals(2, Solution.solve(84L));

        // 13
        assertEquals(1, Solution.solve(85L));
        assertEquals(2, Solution.solve(86L));
        assertEquals(3, Solution.solve(87L));
        assertEquals(4, Solution.solve(88L));
        assertEquals(5, Solution.solve(89L));
        assertEquals(6, Solution.solve(90L));
        assertEquals(7, Solution.solve(91L));
        assertEquals(8, Solution.solve(92L));
        assertEquals(9, Solution.solve(93L));
        assertEquals(1, Solution.solve(94L));
        assertEquals(0, Solution.solve(95L));
        assertEquals(1, Solution.solve(96L));
        assertEquals(1, Solution.solve(97L));
        assertEquals(1, Solution.solve(98L));
        assertEquals(2, Solution.solve(99L));
        assertEquals(1, Solution.solve(100L));
        assertEquals(3, Solution.solve(101L));

        // 14
        assertEquals(1, Solution.solve(102L));
        assertEquals(2, Solution.solve(103L));
        assertEquals(3, Solution.solve(104L));
        assertEquals(4, Solution.solve(105L));
        assertEquals(5, Solution.solve(106L));
        assertEquals(6, Solution.solve(107L));
        assertEquals(7, Solution.solve(108L));
        assertEquals(8, Solution.solve(109L));
        assertEquals(9, Solution.solve(110L));
        assertEquals(1, Solution.solve(111L));
        assertEquals(0, Solution.solve(112L));
        assertEquals(1, Solution.solve(113L));
        assertEquals(1, Solution.solve(114L));
        assertEquals(1, Solution.solve(115L));
        assertEquals(2, Solution.solve(116L));
        assertEquals(1, Solution.solve(117L));
        assertEquals(3, Solution.solve(118L));
        assertEquals(1, Solution.solve(119L));
        assertEquals(4, Solution.solve(120L));

        // 15
        assertEquals(1, Solution.solve(121L));
        assertEquals(2, Solution.solve(122L));
        assertEquals(3, Solution.solve(123L));
        assertEquals(4, Solution.solve(124L));
        assertEquals(5, Solution.solve(125L));
        assertEquals(6, Solution.solve(126L));
        assertEquals(7, Solution.solve(127L));
        assertEquals(8, Solution.solve(128L));
        assertEquals(9, Solution.solve(129L));
        assertEquals(1, Solution.solve(130L));
        assertEquals(0, Solution.solve(131L));
        assertEquals(1, Solution.solve(132L));
        assertEquals(1, Solution.solve(133L));
        assertEquals(1, Solution.solve(134L));
        assertEquals(2, Solution.solve(135L));
        assertEquals(1, Solution.solve(136L));
        assertEquals(3, Solution.solve(137L));
        assertEquals(1, Solution.solve(138L));
        assertEquals(4, Solution.solve(139L));
        assertEquals(1, Solution.solve(140L));
        assertEquals(5, Solution.solve(141L));

        // 16
        assertEquals(1, Solution.solve(142L));
        assertEquals(2, Solution.solve(143L));
        assertEquals(3, Solution.solve(144L));
        assertEquals(4, Solution.solve(145L));
        assertEquals(5, Solution.solve(146L));
        assertEquals(6, Solution.solve(147L));
        assertEquals(7, Solution.solve(148L));
        assertEquals(8, Solution.solve(149L));
        assertEquals(9, Solution.solve(150L));
        assertEquals(1, Solution.solve(151L));
        assertEquals(0, Solution.solve(152L));
        assertEquals(1, Solution.solve(153L));
        assertEquals(1, Solution.solve(154L));
        assertEquals(1, Solution.solve(155L));
        assertEquals(2, Solution.solve(156L));
        assertEquals(1, Solution.solve(157L));
        assertEquals(3, Solution.solve(158L));
        assertEquals(1, Solution.solve(159L));
        assertEquals(4, Solution.solve(160L));
        assertEquals(1, Solution.solve(161L));
        assertEquals(5, Solution.solve(162L));
        assertEquals(1, Solution.solve(163L));
        assertEquals(6, Solution.solve(164L));

        // 17
        assertEquals(1, Solution.solve(165L));
        assertEquals(2, Solution.solve(166L));
        assertEquals(3, Solution.solve(167L));
        assertEquals(4, Solution.solve(168L));
        assertEquals(5, Solution.solve(169L));
        assertEquals(6, Solution.solve(170L));
        assertEquals(7, Solution.solve(171L));
        assertEquals(8, Solution.solve(172L));
        assertEquals(9, Solution.solve(173L));
        assertEquals(1, Solution.solve(174L));
        assertEquals(0, Solution.solve(175L));
        assertEquals(1, Solution.solve(176L));
        assertEquals(1, Solution.solve(177L));
        assertEquals(1, Solution.solve(178L));
        assertEquals(2, Solution.solve(179L));
        assertEquals(1, Solution.solve(180L));
        assertEquals(3, Solution.solve(181L));
        assertEquals(1, Solution.solve(182L));
        assertEquals(4, Solution.solve(183L));
        assertEquals(1, Solution.solve(184L));
        assertEquals(5, Solution.solve(185L));
        assertEquals(1, Solution.solve(186L));
        assertEquals(6, Solution.solve(187L));
        assertEquals(1, Solution.solve(188L));
        assertEquals(7, Solution.solve(189L));

        // ...

        // 36
        assertEquals(1, Solution.solve(982L));
        assertEquals(2, Solution.solve(983L));
        assertEquals(3, Solution.solve(984L));
        assertEquals(4, Solution.solve(985L));
        assertEquals(5, Solution.solve(986L));
        assertEquals(6, Solution.solve(987L));
        assertEquals(7, Solution.solve(988L));
        assertEquals(8, Solution.solve(989L));
        assertEquals(9, Solution.solve(990L));
        assertEquals(1, Solution.solve(991L));
        assertEquals(0, Solution.solve(992L));
        assertEquals(1, Solution.solve(993L));
        assertEquals(1, Solution.solve(994L));
        assertEquals(1, Solution.solve(995L));
        assertEquals(2, Solution.solve(996L));
        assertEquals(1, Solution.solve(997L));
        assertEquals(3, Solution.solve(998L));
        assertEquals(1, Solution.solve(999L));
        assertEquals(4, Solution.solve(1000L));
        assertEquals(1, Solution.solve(1001L));
        assertEquals(5, Solution.solve(1002L));
        assertEquals(1, Solution.solve(1003L));
        assertEquals(6, Solution.solve(1004L));
        assertEquals(1, Solution.solve(1005L));
        assertEquals(7, Solution.solve(1006L));
        assertEquals(1, Solution.solve(1007L));
        assertEquals(8, Solution.solve(1008L));
        assertEquals(1, Solution.solve(1009L));
        assertEquals(9, Solution.solve(1010L));
        assertEquals(2, Solution.solve(1011L));
        assertEquals(0, Solution.solve(1012L));
        assertEquals(2, Solution.solve(1013L));
        assertEquals(1, Solution.solve(1014L));
        assertEquals(2, Solution.solve(1015L));
        assertEquals(2, Solution.solve(1016L));
        assertEquals(2, Solution.solve(1017L));
        assertEquals(3, Solution.solve(1018L));
        assertEquals(2, Solution.solve(1019L));
        assertEquals(4, Solution.solve(1020L));
        assertEquals(2, Solution.solve(1021L));
        assertEquals(5, Solution.solve(1022L));
        assertEquals(2, Solution.solve(1023L));
        assertEquals(6, Solution.solve(1024L));
        assertEquals(2, Solution.solve(1025L));
        assertEquals(7, Solution.solve(1026L));
        assertEquals(2, Solution.solve(1027L));
        assertEquals(8, Solution.solve(1028L));
        assertEquals(2, Solution.solve(1029L));
        assertEquals(9, Solution.solve(1030L));
        assertEquals(3, Solution.solve(1031L));
        assertEquals(0, Solution.solve(1032L));
        assertEquals(3, Solution.solve(1033L));
        assertEquals(1, Solution.solve(1034L));
        assertEquals(3, Solution.solve(1035L));
        assertEquals(2, Solution.solve(1036L));
        assertEquals(3, Solution.solve(1037L));
        assertEquals(3, Solution.solve(1038L));
        assertEquals(3, Solution.solve(1039L));
        assertEquals(4, Solution.solve(1040L));
        assertEquals(3, Solution.solve(1041L));
        assertEquals(5, Solution.solve(1042L));
        assertEquals(3, Solution.solve(1043L));
        assertEquals(6, Solution.solve(1044L));
    }

    @Test
    public void shouldGetXforY() throws Exception {
        for(long y = 1L; y < 100L; y++) {
            System.out.println("Y: " + y + " / X: " + Solution.x(y));
        }

        assertEquals(1L, Solution.x(1L));

        assertEquals(2L, Solution.x(2L));
        assertEquals(2L, Solution.x(3L));

        assertEquals(3L, Solution.x(4L));
        assertEquals(3L, Solution.x(5L));
        assertEquals(3L, Solution.x(6L));

        assertEquals(4L, Solution.x(7L));
        assertEquals(4L, Solution.x(8L));
        assertEquals(4L, Solution.x(9L));
        assertEquals(4L, Solution.x(10L));

        assertEquals(5L, Solution.x(11L));
        assertEquals(5L, Solution.x(12L));
        assertEquals(5L, Solution.x(13L));
        assertEquals(5L, Solution.x(14L));
        assertEquals(5L, Solution.x(15L));

        assertEquals(6L, Solution.x(16L));
        assertEquals(6L, Solution.x(17L));
        assertEquals(6L, Solution.x(18L));
        assertEquals(6L, Solution.x(19L));
        assertEquals(6L, Solution.x(20L));
        assertEquals(6L, Solution.x(21L));

        assertEquals(7L, Solution.x(22L));
        assertEquals(7L, Solution.x(23L));
        assertEquals(7L, Solution.x(24L));
        assertEquals(7L, Solution.x(25L));
        assertEquals(7L, Solution.x(26L));
        assertEquals(7L, Solution.x(27L));
        assertEquals(7L, Solution.x(28L));

        assertEquals(8L, Solution.x(29L));
        assertEquals(8L, Solution.x(30L));
        assertEquals(8L, Solution.x(31L));
        assertEquals(8L, Solution.x(32L));
        assertEquals(8L, Solution.x(33L));
        assertEquals(8L, Solution.x(34L));
        assertEquals(8L, Solution.x(35L));
        assertEquals(8L, Solution.x(36L));

        assertEquals(9L, Solution.x(37L));
        assertEquals(9L, Solution.x(38L));
        assertEquals(9L, Solution.x(39L));
        assertEquals(9L, Solution.x(40L));
        assertEquals(9L, Solution.x(41L));
        assertEquals(9L, Solution.x(42L));
        assertEquals(9L, Solution.x(43L));
        assertEquals(9L, Solution.x(44L));
        assertEquals(9L, Solution.x(45L));

        // 10 (11 elements)
        assertEquals(10L, Solution.x(46L));
        assertEquals(10L, Solution.x(47L));
        assertEquals(10L, Solution.x(48L));
        assertEquals(10L, Solution.x(49L));
        assertEquals(10L, Solution.x(50L));
        assertEquals(10L, Solution.x(51L));
        assertEquals(10L, Solution.x(52L));
        assertEquals(10L, Solution.x(53L));
        assertEquals(10L, Solution.x(54L));
        assertEquals(10L, Solution.x(55L));
        assertEquals(10L, Solution.x(56L));

        // 11 (13 elements)
        assertEquals(11L, Solution.x(57L));
        assertEquals(11L, Solution.x(58L));
        assertEquals(11L, Solution.x(59L));
        assertEquals(11L, Solution.x(60L));
        assertEquals(11L, Solution.x(61L));
        assertEquals(11L, Solution.x(62L));
        assertEquals(11L, Solution.x(63L));
        assertEquals(11L, Solution.x(64L));
        assertEquals(11L, Solution.x(65L));
        assertEquals(11L, Solution.x(66L));
        assertEquals(11L, Solution.x(67L));
        assertEquals(11L, Solution.x(68L));
        assertEquals(11L, Solution.x(69L));
    }

    @Test
    public void shouldNotFail() throws Exception {
        assertEquals(10L, Solution.x(56L));
//        assertEquals(11L, Solution.x(57L));

    }

    @Test
    public void shouldDoSomething() throws Exception {
        for(long i = 1L; i < 200L; i++) {
            System.out.println("i: " + i + " result: " + Solution.solve(i) + " / m: " + Solution.mitternacht(i) + " / count: " + mitternachtCount(i) + " base: " + mitternachtBase(i) + " offset: " + mitternachtOffset(i));
        }
    }

    @Test
    public void shouldDoSchmarrn() throws Exception {
        for(long i = 1L; i < 100L; i++) {
            System.out.println("i: " + i + " / m: " + Solution.schmarrn(i));
        }
    }

    @Test
    public void shouldGetYForX_singleDigit() throws Exception {
        assertEquals(1, Solution.y(1L));
        assertEquals(2, Solution.y(2L));
        assertEquals(4, Solution.y(3L));
        assertEquals(7, Solution.y(4L));
        assertEquals(11, Solution.y(5L));

        assertEquals(16, Solution.y(6L));
        assertEquals(22, Solution.y(7L));
        assertEquals(29, Solution.y(8L));
        assertEquals(37, Solution.y(9L));
        assertEquals(46, Solution.y(10L));
    }

    @Test
    public void shouldGetYForX_doubleDigit() throws Exception {
        assertEquals(57, Solution.y(11L));
        assertEquals(70, Solution.y(12L));
        assertEquals(85, Solution.y(13L));
        assertEquals(102, Solution.y(14L));
        assertEquals(121, Solution.y(15L));

        assertEquals(142, Solution.y(16L));
        assertEquals(165, Solution.y(17L));
        assertEquals(190, Solution.y(18L));

        // ...

        assertEquals(982, Solution.y(36L));
        assertEquals(1045, Solution.y(37L));

        // ...

        assertEquals(8857, Solution.y(99L));
    }

    @Test
    public void shouldGetYForX_threeDigit() throws Exception {
        long y = 0L;

        for(long x = 1L; x < 1000L; x++) {
            long y_new = Solution.y(x);

            System.out.println("X: " + x + " / Y: " + y_new + " / diff: " + (y_new - y));

            y = y_new;
        }

        assertEquals(9046, Solution.y(100L));
        assertEquals(9238, Solution.y(101L));
        assertEquals(9433, Solution.y(102L));
    }

    @Test
    public void shouldInverseMitternacht() throws Exception {
        double mitternacht = mitternacht(8.0);
        double actual = invMitternacht(mitternacht);
        assertThat(actual).isEqualTo(8.0);

        System.out.println(mitternachtCount(8.0));
    }
}
