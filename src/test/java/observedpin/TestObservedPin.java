package observedpin;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static observedpin.ObservedPin.getPINs;

public class TestObservedPin {

    public static HashMap<String, String[]> expectations = new HashMap<>() {
        {
            put("8", new String[]{"5", "7", "8", "9", "0"});
            put("11", new String[]{"11", "21", "41", "12", "22", "42", "14", "24", "44"});
            put("369", new String[]{"236", "238", "239", "256", "258", "259", "266", "268", "269", "296", "298", "299", "336", "338", "339", "356", "358", "359", "366", "368", "369", "396", "398", "399", "636", "638", "639", "656", "658", "659", "666", "668", "669", "696", "698", "699"});
        }
    };
    private final static Comparator<String> comp = String::compareTo;

    @Test
    public void testPins() {
        for (String entered : expectations.keySet()) {
            test(entered, Arrays.asList(expectations.get(entered)), getPINs(entered));
        }
    }

    private void test(String entered, List<String> expected, List<String> result) {
        result.sort(comp);
        expected.sort(comp);
        Assert.assertEquals("For observed PIN " + entered, expected, result);
    }

    @Test
    public void shouldReturnPinsForOneDigitPin() throws Exception {
        test("0", "0", "8");
        test("1", "1", "2", "4");
        test("2", "1", "2", "3", "5");
        test("3", "2", "3", "6");
        test("4", "1", "4", "5", "7");
        test("5", "2", "4", "5", "6", "8");
        test("6", "3", "5", "6", "9");
        test("7", "4", "7", "8");
        test("8", "5", "7", "8", "9", "0");
        test("9", "6", "8", "9");
    }

    @Test
    public void shouldReturnPinsForTwoDigitsPin() throws Exception {
        test("11", "11", "21", "41", "12", "22", "42", "14", "24", "44");
        test("69",
                "36", "38", "39",
                "56", "58", "59",
                "66", "68", "69",
                "96", "98", "99");
    }

    private void test(String entered, String... expected) {
        List<String> actual = getPINs(entered);
        actual.sort(comp);

        List<String> expectedAsList = Arrays.asList(expected);
        expectedAsList.sort(comp);

        Assert.assertEquals("For observed PIN " + entered, expectedAsList, actual);
    }
}
