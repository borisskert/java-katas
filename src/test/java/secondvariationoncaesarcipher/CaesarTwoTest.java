package secondvariationoncaesarcipher;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CaesarTwoTest {

    @Test
    public void test1() {
        String u = "I should have known that you would have a perfect answer for me!!!";
        List<String> v = Arrays.asList("ijJ tipvme ibw", "f lopxo uibu z", "pv xpvme ibwf ", "b qfsgfdu botx", "fs gps nf!!!");
        assertEquals(v, CaesarTwo.encodeStr(u, 1));
        assertEquals(u, CaesarTwo.decode(v));
    }

    @Test
    public void test2() {
        String u = "O CAPTAIN! my Captain! our fearful trip is done;";
        List<String> v = Arrays.asList("opP DBQUBJ", "O! nz Dbqu", "bjo! pvs g", "fbsgvm usj", "q jt epof;");
        assertEquals(v, CaesarTwo.encodeStr(u, 1));
        assertEquals(u, CaesarTwo.decode(v));
    }

    @Test
    public void test8() throws Exception {
        String u = "I have spread my dreams under your feet; Tread softly because you tread on my dreams. William B Yeats (1865-1939)";
        List<String> v = Arrays.asList("ihH gzud roqdzc lx cqdz", "lr tmcdq xntq edds; Sqd", "zc rneskx adbztrd xnt s", "qdzc nm lx cqdzlr. Vhkk", "hzl A Xdzsr (1865-1939)");
        assertEquals(v, CaesarTwo.encodeStr(u, 25));
        assertEquals(u, CaesarTwo.decode(v));
    }
}
