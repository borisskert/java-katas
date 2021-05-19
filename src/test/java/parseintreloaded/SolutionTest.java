package parseintreloaded;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void fixedTests() {
        assertEquals(1 , Parser.parseInt("one"));
        assertEquals(20 , Parser.parseInt("twenty"));
        assertEquals(246 , Parser.parseInt("two hundred forty-six"));
    }

    @Test
    public void shouldParseOneDigitNumber() throws Exception {
        assertEquals(0 , Parser.parseInt("zero"));
        assertEquals(1 , Parser.parseInt("one"));
        assertEquals(2 , Parser.parseInt("two"));
        assertEquals(3 , Parser.parseInt("three"));
        assertEquals(4 , Parser.parseInt("four"));
        assertEquals(5 , Parser.parseInt("five"));
        assertEquals(6 , Parser.parseInt("six"));
        assertEquals(7 , Parser.parseInt("seven"));
        assertEquals(8 , Parser.parseInt("eight"));
        assertEquals(9 , Parser.parseInt("nine"));
    }

    @Test
    public void shouldParseTeenNumbers() throws Exception {
        assertEquals(10 , Parser.parseInt("ten"));
        assertEquals(11 , Parser.parseInt("eleven"));
        assertEquals(12 , Parser.parseInt("twelve"));
        assertEquals(13 , Parser.parseInt("thirteen"));
        assertEquals(14 , Parser.parseInt("fourteen"));
        assertEquals(15 , Parser.parseInt("fifteen"));
        assertEquals(16 , Parser.parseInt("sixteen"));
        assertEquals(17 , Parser.parseInt("seventeen"));
        assertEquals(18 , Parser.parseInt("eighteen"));
        assertEquals(19 , Parser.parseInt("nineteen"));
    }

    @Test
    public void shouldParseTypicalTwoDigitNumbers() throws Exception {
        assertEquals(20 , Parser.parseInt("twenty"));
        assertEquals(21 , Parser.parseInt("twenty-one"));
        assertEquals(22 , Parser.parseInt("twenty-two"));
        assertEquals(23 , Parser.parseInt("twenty-three"));
        assertEquals(24 , Parser.parseInt("twenty-four"));
        assertEquals(25 , Parser.parseInt("twenty-five"));
        assertEquals(26 , Parser.parseInt("twenty-six"));
        assertEquals(27 , Parser.parseInt("twenty-seven"));
        assertEquals(28 , Parser.parseInt("twenty-eight"));
        assertEquals(29 , Parser.parseInt("twenty-nine"));
    }

    @Test
    public void shouldParseTenners() throws Exception {
        assertEquals(30 , Parser.parseInt("thirty"));
        assertEquals(40 , Parser.parseInt("forty"));
        assertEquals(50 , Parser.parseInt("fifty"));
        assertEquals(60 , Parser.parseInt("sixty"));
        assertEquals(70 , Parser.parseInt("seventy"));
        assertEquals(80 , Parser.parseInt("eighty"));
        assertEquals(90 , Parser.parseInt("ninety"));
    }

    @Test
    public void shouldParseHundreds() throws Exception {
        assertEquals(100 , Parser.parseInt("one hundred"));
        assertEquals(200 , Parser.parseInt("two hundred"));
        assertEquals(300 , Parser.parseInt("three hundred"));
        assertEquals(400 , Parser.parseInt("four hundred"));
        assertEquals(500 , Parser.parseInt("five hundred"));
        assertEquals(600 , Parser.parseInt("six hundred"));
        assertEquals(700 , Parser.parseInt("seven hundred"));
        assertEquals(800 , Parser.parseInt("eight hundred"));
        assertEquals(900 , Parser.parseInt("nine hundred"));
    }

    @Test
    public void shouldParseTypicalThreeDigitNumbers() throws Exception {
        assertEquals(101 , Parser.parseInt("one hundred and one"));
        assertEquals(111 , Parser.parseInt("one hundred and eleven"));
        assertEquals(122 , Parser.parseInt("one hundred and twenty-two"));
        assertEquals(227 , Parser.parseInt("two hundred twenty-seven"));
        assertEquals(999 , Parser.parseInt("nine hundred and ninety-nine"));
    }

    @Test
    public void shouldParseThousands() throws Exception {
        assertEquals(1000 , Parser.parseInt("one thousand"));
        assertEquals(2000 , Parser.parseInt("two thousand"));
        assertEquals(3000 , Parser.parseInt("three thousand"));
        assertEquals(4000 , Parser.parseInt("four thousand"));
        assertEquals(5000 , Parser.parseInt("five thousand"));
        assertEquals(6000 , Parser.parseInt("six thousand"));
        assertEquals(7000 , Parser.parseInt("seven thousand"));
        assertEquals(8000 , Parser.parseInt("eight thousand"));
        assertEquals(9000 , Parser.parseInt("nine thousand"));
    }

    @Test
    public void shouldParseTypicalFourDigitNumbers() throws Exception {
        assertEquals(1001 , Parser.parseInt("one thousand and one"));
        assertEquals(2015 , Parser.parseInt("two thousand and fifteen"));
        assertEquals(3111 , Parser.parseInt("three thousand one hundred and eleven"));
        assertEquals(5222 , Parser.parseInt("five thousand two hundred and twenty-two"));
        assertEquals(9999 , Parser.parseInt("nine thousand nine hundred and ninety-nine"));
    }

    @Test
    public void shouldParseRandomNumbers() throws Exception {
        assertEquals(93373 , Parser.parseInt("ninety-three thousand three hundred seventy-three"));
        assertEquals(227653 , Parser.parseInt("two hundred twenty-seven thousand six hundred fifty-three"));
    }

    @Test
    public void shouldParseFixed() throws Exception {
        assertEquals(1000000 , Parser.parseInt("one million"));
    }
}
