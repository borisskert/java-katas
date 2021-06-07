package romannumeralshelper;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RomanNumeralsTest {

    @Test
    public void testToRoman() throws Exception {
        assertThat("1 converts to 'I'", RomanNumerals.toRoman(1), is("I"));
        assertThat("2 converts to 'II'", RomanNumerals.toRoman(2), is("II"));
        assertThat("3 converts to 'III'", RomanNumerals.toRoman(3), is("III"));
        assertThat("4 converts to 'IV'", RomanNumerals.toRoman(4), is("IV"));
        assertThat("5 converts to 'V'", RomanNumerals.toRoman(5), is("V"));
        assertThat("6 converts to 'VI'", RomanNumerals.toRoman(6), is("VI"));
        assertThat("7 converts to 'VII'", RomanNumerals.toRoman(7), is("VII"));
        assertThat("8 converts to 'VIII'", RomanNumerals.toRoman(8), is("VIII"));
        assertThat("9 converts to 'IX'", RomanNumerals.toRoman(9), is("IX"));
        assertThat("10 converts to 'X'", RomanNumerals.toRoman(10), is("X"));
        assertThat("20 converts to 'XX'", RomanNumerals.toRoman(20), is("XX"));
        assertThat("11 converts to 'XI'", RomanNumerals.toRoman(11), is("XI"));
        assertThat("15 converts to 'XV'", RomanNumerals.toRoman(15), is("XV"));
        assertThat("19 converts to 'XIX'", RomanNumerals.toRoman(19), is("XIX"));
        assertThat("44 converts to 'XLIV'", RomanNumerals.toRoman(44), is("XLIV"));
        assertThat("50 converts to 'L'", RomanNumerals.toRoman(50), is("L"));
        assertThat("100 converts to 'C'", RomanNumerals.toRoman(100), is("C"));
        assertThat("120 converts to 'CXX'", RomanNumerals.toRoman(120), is("CXX"));
        assertThat("125 converts to 'CXXV'", RomanNumerals.toRoman(125), is("CXXV"));
        assertThat("500 converts to 'D'", RomanNumerals.toRoman(500), is("D"));
        assertThat("1000 converts to 'M'", RomanNumerals.toRoman(1000), is("M"));
        assertThat("1666 converts to 'MDCLXVI'", RomanNumerals.toRoman(1666), is("MDCLXVI"));
        assertThat("2008 converts to 'MMVIII'", RomanNumerals.toRoman(2008), is("MMVIII"));
    }

    @Test
    public void testFromRoman() throws Exception {
        assertThat("'I' converts to 1", RomanNumerals.fromRoman("I"), is(1));
        assertThat("'II' converts to 2", RomanNumerals.fromRoman("II"), is(2));
        assertThat("'III' converts to 3", RomanNumerals.fromRoman("III"), is(3));
        assertThat("'IV' converts to 4", RomanNumerals.fromRoman("IV"), is(4));
        assertThat("'V' converts to 5", RomanNumerals.fromRoman("V"), is(5));
        assertThat("'VI' converts to 6", RomanNumerals.fromRoman("VI"), is(6));
        assertThat("'VII' converts to 7", RomanNumerals.fromRoman("VII"), is(7));
        assertThat("'VIII' converts to 8", RomanNumerals.fromRoman("VIII"), is(8));
    }
}
