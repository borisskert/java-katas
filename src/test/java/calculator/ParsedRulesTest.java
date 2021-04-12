package calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ParsedRulesTest {

    @Test
    public void shouldParseSingleLine() throws Exception {
        ParsedRules parsed = ParsedRules.parse("Z -> A");

        assertThat(parsed.rules()).isEqualTo(new ParsedRule[]{new ParsedRule("Z", "A")});
        assertThat(parsed.bySymbol(new NonTerminal("Z"))).isEqualTo(new ParsedRule[]{
                new ParsedRule("Z", "A")
        });
        assertThat(parsed.createInitial()).isEqualTo(new ParsedRule("Z'", "Z $"));
    }

    @Test
    public void shouldParseTwoLines() {
        ParsedRules parsed = ParsedRules.parse("Z -> A\nZ -> A * B");

        assertThat(parsed.rules()).isEqualTo(new ParsedRule[]{
                new ParsedRule("Z", "A"),
                new ParsedRule("Z", "A * B")
        });

        assertThat(parsed.bySymbol(new NonTerminal("Z"))).isEqualTo(new ParsedRule[]{
                new ParsedRule("Z", "A"),
                new ParsedRule("Z", "A * B")
        });

        assertThat(parsed.createInitial()).isEqualTo(new ParsedRule("Z'", "Z $"));
    }

    @Test
    public void shouldParseThreeLines() {
        ParsedRules parsed = ParsedRules.parse("Z -> A\nZ -> A * B\nA -> B + B");

        assertThat(parsed.rules()).isEqualTo(new ParsedRule[]{
                new ParsedRule("Z", "A"),
                new ParsedRule("Z", "A * B"),
                new ParsedRule("A", "B + B"),
        });

        assertThat(parsed.bySymbol(new NonTerminal("Z"))).isEqualTo(new ParsedRule[]{
                new ParsedRule("Z", "A"),
                new ParsedRule("Z", "A * B")
        });

        assertThat(parsed.createInitial()).isEqualTo(new ParsedRule("Z'", "Z $"));
    }
}
