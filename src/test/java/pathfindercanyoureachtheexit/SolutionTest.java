package pathfindercanyoureachtheexit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static pathfindercanyoureachtheexit.Finder.pathFinder;

public class SolutionTest {

    @Test
    public void sampleTests() {

        String a = ".W.\n" +
                ".W.\n" +
                "...",

                b = ".W.\n" +
                        ".W.\n" +
                        "W..",

                c = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......",

                d = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        ".....W\n" +
                        "....W.";

        assertEquals(true, pathFinder(a));
        assertEquals(false, pathFinder(b));
        assertEquals(true, pathFinder(c));
        assertEquals(false, pathFinder(d));
    }

    @Test
    public void fainingTests() throws Exception {
        String maze = "" +
                ".W.\n" +
                ".W.\n" +
                "W..";

        assertThat(pathFinder(maze)).isEqualTo(false);
    }

    @Test
    public void moreTests() throws Exception {
        String maze = ".W...\n" +
                ".W...\n" +
                ".W.W.\n" +
                "...W.\n" +
                "...W.\n" +
                ".W...\n" +
                ".W...\n" +
                ".W.W.\n" +
                "...WW\n" +
                "...W.\n" +
                "..W\n" +
                ".W.\n" +
                "W..\n" +
                ".WWWW\n" +
                ".W...\n" +
                ".W.W.\n" +
                ".W.W.\n" +
                "...W.\n" +
                ".W...\n" +
                "W....\n" +
                ".....\n" +
                ".....\n" +
                ".....";

        assertThat(pathFinder(maze)).isEqualTo(false);
    }

    @Test
    public void randomTests() throws Exception {
        String maze =
                ".W......W.....W...W....W.....W............W..W...WW.W.\n" +
                        "..........WW.W....W.......WW.W.WW..........W......W...\n" +
                        "W....W...W.....W.W.....W..W.WWWW.W..W.....WW...W..W.W.\n" +
                        "..W..W.WW.WW.W.....W..W...W...W...W..............W....\n" +
                        "....W..W.W.WWW.WW........W.WW.....W.....W......W...W.W\n" +
                        ".WWW.W..W..........W.......W.......W.W.WWW...W.....W..\n" +
                        ".W.WW.W.........W.W..W..WWW...W.....W......W.WW...W.W.\n" +
                        "....W.....W..................W...W...W...W..W..W.W.W.W\n" +
                        "WW....W....WW........WWW.....WW...W.....WW...WW....W..\n" +
                        ".........W..W..W.WW.......W..W......W.......W..W.W..W.\n" +
                        "....W.WW..W....W.W.....WW....W.W....W.....W.....W.....\n" +
                        "..W...WW..W.WWW....WW.........W....W..WW....WW..W..W..\n" +
                        "W.W........W....W.....W.......W......WW.W..W...W......\n" +
                        "W...W..W...W..........W...W.WWWW.W...W..W...W.....WWW.\n" +
                        "....W....W..W....WW..W....WW...WWW.............W.W....\n" +
                        "W.........W..WW...............WWW.W.W..WW.........W.W.\n" +
                        "....W.W......W..WW.WW...W.W.W.......W.W.W..W........W.\n" +
                        "WWW.............W..W...W.WW..W....W.W...WW....W.WW...W\n" +
                        "...W...W.........W..WWW..W.....W...WWWW.W..W....WW....\n" +
                        ".....WW.........W..WW.W..W.WWWW..W........W.W....W.WW.\n" +
                        "WW.W.....W....W...WW.W..W....WW....W....WWW..WW......W\n" +
                        "........W.WW.W............WW...W..WWWW...W....W..W..WW\n" +
                        "....WW..WW....W....W...W.WW..........W...W.......W..W.\n" +
                        ".WW..W................W......WW.....W....W.W..W...W..W\n" +
                        "...W...WW...W....W..WW..W.....WW...W.W..WW.W.W..W.W...\n" +
                        "...WW..W........W......W.W....W.......W.W..W..W..W....\n" +
                        ".........W....W..WWWW.....WWW..W.W..W.W.WW......W.W...\n" +
                        "....W.....W.W...W....WW....WWW...W......W......W..WWW.\n" +
                        ".WW..W..WW.....WW.W..WW...W..........W.......W.WWW..W.\n" +
                        "WW........W......W.......W..W...W.WW.WWW.WW........WWW\n" +
                        "WW......W.WW....WW.WW..............W..W.W.W..W......W.\n" +
                        "W....W......W...W.WW..WW...........W.W............W...\n" +
                        ".....W..WW.W.W......W......WWW.WWWW......W....W...W...\n" +
                        "...WW.WW...W...W.......W...WW.W.W.....WW.........W....\n" +
                        "...W...W...W......W.W....W..W.....WWW.W.....W....W....\n" +
                        "W.W...W..........WW.W..W..W.WW.....W.....W..W......W..\n" +
                        "..W......W.....WWW..WW..WWW.....W...........W.W.......\n" +
                        "..W.....W...W...W.W...W.......W.WW.......W.W..WW....WW\n" +
                        "W..W...........W..W..W....WWW.........W..W..W.W....W..\n" +
                        "..WWWW...W......W.W.....W.....W...W...W......W.WWW...W\n" +
                        ".....W....WWW.WWW........WW.....WW....W....WWW.....WW.\n" +
                        ".W...W.W.....W..W..W............W..W.WW...W...........\n" +
                        "......W...............W.W......W.W.W.W......W...WW.W..\n" +
                        "...W..WW...W.WWW.W.......W......W..W..WW.W.....WW.W..W\n" +
                        ".WW....W.W.W..W..WW......W..W.WW....W......W.....W....\n" +
                        ".W.........WW....W..W..........W.W..WW.W..WW..W....WW.\n" +
                        "W.......W...W..W.....WWW.......W............W.....WW.W\n" +
                        ".WW....W..W..W........WW.....W.WW....WW...........W..W\n" +
                        "....W...W...W...W.W..................WW.....WW...WW...\n" +
                        ".W..WW.W...W............W......W.W.......WW...W..W....\n" +
                        "...WW.........W.WWW.........W...WWW......W....W....W.W\n" +
                        "WW.......WW...W.W.WWWWW...W..W.W.W.W...W.....WW....WW.\n" +
                        ".W.W.WW...W.WW.......W.WW.............W.W.W..........W\n" +
                        "......WW..WWWW.W..WW.....W...W.W...W.....W........WW..";

        assertThat(pathFinder(maze)).isEqualTo(true);
    }

    @Test
    public void longRunningTests() throws Exception {
        String maze = "" +
                ".......W..W...........W...\n" +
                "WW.W.WW..W.WWWWWW.WWW.W.W.\n" +
                "W...WWW.WW....W...........\n" +
                "...WW..W.W......W.W..W....\n" +
                "W...WWW...W............W.W\n" +
                ".....W..WWW.W.W.W.......W.\n" +
                "W....W.W......W..W.W...W..\n" +
                ".WW..WW..........WW...WW.W\n" +
                "...WW....W.....W...W.W..W.\n" +
                "........WWWW.....W.......W\n" +
                "W.WW.WW.....W...WWW...W..W\n" +
                "W..W...WWW.......W.WW...WW\n" +
                "....W......WW..W..WW......\n" +
                "W...W.W..W.....W....W..W.W\n" +
                "...WW.W...W.W..WW..W.....W\n" +
                ".........W..W....W.W.....W\n" +
                "WW.W...W......WW.......W..\n" +
                "...W..........W.W.W......W\n" +
                ".....W..W...W...W.W...WWW.\n" +
                "W.W....W..W.W.....WWW...WW\n" +
                "W..W..W.....W.W......W....\n" +
                "W.W..W.W.W.W...W.W....W.WW\n" +
                "......WW...........WWW..W.\n" +
                "W.W...WW.......WW...W.....\n" +
                "....W..W.W........W.WW....\n" +
                "..W...W.W.....WW.....W....";

        assertThat(pathFinder(maze)).isEqualTo(false);
    }
}
