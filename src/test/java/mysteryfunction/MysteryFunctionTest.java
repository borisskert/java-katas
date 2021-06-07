package mysteryfunction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MysteryFunctionTest {

    @Test
    public void mysteryOf0() throws Exception {
        assertEquals("mystery(0) ", 0, MysteryFunction.mystery(0));
    }

    @Test
    public void mysteryOf1() throws Exception {
        assertEquals("mystery(1) ", 1, MysteryFunction.mystery(1));
    }

    @Test
    public void mysteryOf2() throws Exception {
        assertEquals("mystery(2) ", 3, MysteryFunction.mystery(2));
    }

    @Test
    public void mysteryOf3() throws Exception {
        assertEquals("mystery(3) ", 2, MysteryFunction.mystery(3));
    }

    @Test
    public void mysteryOf4() throws Exception {
        assertEquals("mystery(4) ", 6, MysteryFunction.mystery(4));
    }

    @Test
    public void mysteryOf5() throws Exception {
        assertEquals("mystery(5) ", 7, MysteryFunction.mystery(5));
    }

    @Test
    public void mysteryOf6() throws Exception {
        assertEquals("mystery(6) ", 5, MysteryFunction.mystery(6));
    }

    @Test
    public void mysteryOf7() throws Exception {
        assertEquals("mystery(7) ", 4, MysteryFunction.mystery(7));
    }

    @Test
    public void mysteryOf8() throws Exception {
        assertEquals("mystery(8) ", 12, MysteryFunction.mystery(8));
    }

    @Test
    public void mysteryOf9() throws Exception {
        assertEquals("mystery(9) ", 13, MysteryFunction.mystery(9));
    }

    @Test
    public void mysteryOf10() throws Exception {
        assertEquals("mystery(10) ", 15, MysteryFunction.mystery(10));
    }

    @Test
    public void mysteryOfAll() throws Exception {
        for(long i = 1L; i <= 2048L * 16L; i++) {
            long mystery = MysteryFunction.mystery(i);
            long mysteryInv = MysteryFunction.mysteryInv(mystery);

            assertEquals("mystery(mystery(" + i + ")) -> " + i, mysteryInv, i);
        }
    }

    @Test
    public void mysteryOf19() throws Exception {
        assertEquals("mystery(19) ", 26, MysteryFunction.mystery(19));
    }

    @Test
    public void mysteryOf5945746802532687066() throws Exception {
        assertEquals("mystery(5945746802532687066) ", 8917775615655940791L, MysteryFunction.mystery(5945746802532687066L));
    }

    @Test
    public void mysteryInvOf8917775615655940791() throws Exception {
        assertEquals("mysteryInv(8917775615655940791) ", 5945746802532687066L, MysteryFunction.mysteryInv(8917775615655940791L));
    }

    @Test
    public void mysteryInvOf0() throws Exception {
        assertEquals("mysteryInv(0)", 0, MysteryFunction.mysteryInv(0));
    }

    @Test
    public void mysteryInvOf1() throws Exception {
        assertEquals("mysteryInv(1)", 1, MysteryFunction.mysteryInv(1));
    }

    @Test
    public void mysteryInvOf2() throws Exception {
        assertEquals("mysteryInv(2)", 3, MysteryFunction.mysteryInv(2));
    }

    @Test
    public void mysteryInvOf3() throws Exception {
        assertEquals("mysteryInv(3)", 2, MysteryFunction.mysteryInv(3));
    }

    @Test
    public void mysteryInvOf4() throws Exception {
        assertEquals("mysteryInv(4)", 7, MysteryFunction.mysteryInv(4));
    }

    @Test
    public void mysteryInvOf5() throws Exception {
        assertEquals("mysteryInv(5)", 6, MysteryFunction.mysteryInv(5));
    }

    @Test
    public void mysteryInvOf6() throws Exception {
        assertEquals("mysteryInv(6)", 4, MysteryFunction.mysteryInv(6));
    }

    @Test
    public void mysteryInvOf7() throws Exception {
        assertEquals("mysteryInv(7)", 5, MysteryFunction.mysteryInv(7));
    }

    @Test
    public void mysteryInvOf13() throws Exception {
        assertEquals("mysteryInv(13)", 9, MysteryFunction.mysteryInv(13));
    }

    @Test
    public void mysteryInvOf26() throws Exception {
        assertEquals("mysteryInv(26)", 19, MysteryFunction.mysteryInv(26));
    }
}
