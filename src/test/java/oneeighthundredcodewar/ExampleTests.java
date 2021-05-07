package oneeighthundredcodewar;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;


public class ExampleTests {

    private Set<String> show(final Set<String> set) {
        System.out.println(set.toString());
        return set;
    }

    @Test
    public void example() {
        final Set<String> expected = new HashSet<>();
        expected.add("1-800-CODE-WAR");
        expected.add("1-800-CODE-YAP");
        expected.add("1-800-CODE-WAS");
        expected.add("1-800-CODE-ZAP");
        assertEquals(expected, show(Dinglemouse.check1800("1-800-CODE-WAR")));
    }

    @Test
    public void anotherExample() {
        final Set<String> expected = new HashSet<>();
        expected.add("1-800-HERS-ONE");
        expected.add("1-800-HERS-NOD");
        expected.add("1-800-HER-POOF");
        assertEquals(expected, show(Dinglemouse.check1800("1-800-HER-POOF")));
    }
}
