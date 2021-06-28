package rankingpokerhands;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static rankingpokerhands.Card.of;
import static rankingpokerhands.Color.DIAMONDS;
import static rankingpokerhands.Value.ACE;

class CardTest {

    @Test
    public void shouldParseCard() throws Exception {
        Card parsedCard = Card.parse("AD");
        assertThat(parsedCard).isEqualTo(of(ACE, DIAMONDS));
    }
}
