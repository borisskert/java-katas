package mostfrequentlyusedwords;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class SolutionTest {

    @Test
    public void sampleTests() {
        assertEquals(Arrays.asList("e", "d", "a"), TopWords.top3("a a a  b  c c  d d d d  e e e e e"));
        assertEquals(Arrays.asList("e", "ddd", "aa"), TopWords.top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"));
        assertEquals(Arrays.asList("won't", "wont"), TopWords.top3("  //wont won't won't "));
        assertEquals(Arrays.asList("e"), TopWords.top3("  , e   .. "));
        assertEquals(Arrays.asList(), TopWords.top3("  ...  "));
        assertEquals(Arrays.asList(), TopWords.top3("  '  "));
        assertEquals(Arrays.asList(), TopWords.top3("  '''  "));
        assertEquals(Arrays.asList("njzypndapd", "fqy'reth", "flhb"), TopWords.top3("fQy'rEtH njZYPNDApd FLHB FLHB:njZYPNDApd FLHB fQy'rEtH fQy'rEtH/FLHB;njZYPNDApd_njZYPNDApd njZYPNDApd.clwY njZYPNDApd FLHB:njZYPNDApd ZwT njZYPNDApd njZYPNDApd/njZYPNDApd!FLHB njZYPNDApd?MkacrU.njZYPNDApd fQy'rEtH clwY-njZYPNDApd fQy'rEtH:fQy'rEtH FLHB fQy'rEtH?FLHB!njZYPNDApd/fQy'rEtH;njZYPNDApd,FLHB/MkacrU fQy'rEtH/FLHB/rPJEkDZttJ.clwY?ZwT;ZwT FLHB fQy'rEtH:fQy'rEtH;njZYPNDApd MkacrU/FLHB FLHB.clwY;fQy'rEtH/MkacrU,fQy'rEtH:ZwT,MkacrU fQy'rEtH;fQy'rEtH fQy'rEtH fQy'rEtH fQy'rEtH:njZYPNDApd ZwT/FLHB_njZYPNDApd?fQy'rEtH;njZYPNDApd:fQy'rEtH-fQy'rEtH,FLHB-fQy'rEtH?fQy'rEtH;FLHB fQy'rEtH!fQy'rEtH.fQy'rEtH_FLHB njZYPNDApd FLHB FLHB_fQy'rEtH:fQy'rEtH:clwY njZYPNDApd ZwT MkacrU njZYPNDApd njZYPNDApd?clwY njZYPNDApd njZYPNDApd:njZYPNDApd!njZYPNDApd njZYPNDApd ZwT!"));
        assertEquals(Arrays.asList("a", "of", "on"), TopWords.top3(Stream
                .of("In a village of La Mancha, the name of which I have no desire to call to",
                        "mind, there lived not long since one of those gentlemen that keep a lance",
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                        "coursing. An olla of rather more beef than mutton, a salad on most",
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                        "on Sundays, made away with three-quarters of his income.")
                .collect(Collectors.joining("\n"))));
    }
}
