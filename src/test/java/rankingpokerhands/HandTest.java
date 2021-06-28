package rankingpokerhands;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandTest {

    @Test
    public void shouldParseHand() throws Exception {
        Hand parsedHand = Hand.parse("6S AD 7H 4S AS");
        Hand expectedHands = Hand.of(
                Card.of(Value.SIX, Color.SPADES),
                Card.of(Value.ACE, Color.DIAMONDS),
                Card.of(Value.SEVEN, Color.HEARTS),
                Card.of(Value.FOUR, Color.SPADES),
                Card.of(Value.ACE, Color.SPADES)
        );

        assertThat(parsedHand).isEqualTo(expectedHands);
    }
}