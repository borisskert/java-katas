package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GrammarTest {

    @Test
    public void shouldParseSimpleGrammar() {
        Grammar grammar = Grammar.parse("Z -> A");

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z")
        });

        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("A"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new Terminal("$"),
                new Terminal("A"),
        });

        assertThat(grammar.rules()).isEqualTo(new Rule[]{
                new Rule(
                    new NonTerminal("Z'"),
                    new Symbol[]{
                            new NonTerminal("Z"),
                            new Terminal("$")}),
                new Rule(
                    new NonTerminal("Z"),
                    new Symbol[]{
                            new Terminal("A")})
        });

        assertThat(grammar.first(new NonTerminal("Z'"))).isEqualTo(new Terminal[] {
                new Terminal("A")
        });

        assertThat(grammar.first(new NonTerminal("Z"))).isEqualTo(new Terminal[] {
                new Terminal("A")
        });

        assertThat(grammar.follow(new NonTerminal("Z'"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("Z"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
    }

    @Test
    public void shouldParseTwoLinesGrammar() {
        Grammar grammar = Grammar.parse("Z -> A\nZ -> B");

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z")
        });
        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("A"),
                new Terminal("B"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new Terminal("$"),
                new Terminal("A"),
                new Terminal("B"),
        });

        assertThat(grammar.rules()).isEqualTo(new Rule[]{
                new Rule(
                        new NonTerminal("Z'"),
                        new Symbol[]{
                                new NonTerminal("Z"),
                                new Terminal("$")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new Terminal("A")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new Terminal("B")})
        });

        assertThat(grammar.first(new NonTerminal("Z'"))).isEqualTo(new Terminal[] {
                new Terminal("A"),
                new Terminal("B"),
        });

        assertThat(grammar.first(new NonTerminal("Z"))).isEqualTo(new Terminal[] {
                new Terminal("A"),
                new Terminal("B"),
        });

        assertThat(grammar.follow(new NonTerminal("Z'"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("Z"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
    }

    @Test
    public void shouldParseThreeLinesGrammar() {
        Grammar grammar = Grammar.parse("Z -> A\nZ -> B\nA -> B");

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A")
        });

        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("B"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new Terminal("$"),
                new Terminal("B"),
        });

        assertThat(grammar.rules()).isEqualTo(new Rule[]{
                new Rule(
                        new NonTerminal("Z'"),
                        new Symbol[]{
                                new NonTerminal("Z"),
                                new Terminal("$")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new NonTerminal("A")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new Terminal("B")}),
                new Rule(
                        new NonTerminal("A"),
                        new Symbol[]{
                                new Terminal("B")})
        });

        assertThat(grammar.first(new NonTerminal("Z'"))).isEqualTo(new Terminal[] {
                new Terminal("B")
        });

        assertThat(grammar.first(new NonTerminal("Z"))).isEqualTo(new Terminal[] {
                new Terminal("B")
        });

        assertThat(grammar.first(new NonTerminal("A"))).isEqualTo(new Terminal[] {
                new Terminal("B")
        });

        assertThat(grammar.follow(new NonTerminal("Z'"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("Z"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("A"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
    }

    @Test
    public void shouldParseLightComplexGrammar() {
        Grammar grammar = Grammar.parse(
                "Z -> A\n" +
                        "A -> B\n" +
                        "A -> a\n" +
                        "B -> b\n"
        );

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
        });

        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
                new Terminal("$"),
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.rules()).isEqualTo(new Rule[]{
                new Rule(
                        new NonTerminal("Z'"),
                        new Symbol[]{
                                new NonTerminal("Z"),
                                new Terminal("$")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new NonTerminal("A")}),
                new Rule(
                        new NonTerminal("A"),
                        new Symbol[]{
                                new NonTerminal("B")}),
                new Rule(
                        new NonTerminal("A"),
                        new Symbol[]{
                                new Terminal("a")}),
                new Rule(
                        new NonTerminal("B"),
                        new Symbol[]{
                                new Terminal("b")})
        });

        assertThat(grammar.first(new NonTerminal("Z'"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("Z"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("A"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("B"))).isEqualTo(new Terminal[] {
                new Terminal("b")
        });

        assertThat(grammar.follow(new NonTerminal("Z'"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("Z"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("A"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("B"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
    }

    @Test
    public void shouldParseLightComplexerGrammar() {
        Grammar grammar = Grammar.parse(
                "Z -> A\n" +
                        "A -> B\n" +
                        "A -> a\n" +
                        "B -> b * a\n"
        );

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
        });

        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("a"),
                new Terminal("b"),
                new Terminal("*"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
                new Terminal("$"),
                new Terminal("a"),
                new Terminal("b"),
                new Terminal("*"),
        });

        assertThat(grammar.rules()).isEqualTo(new Rule[]{
                new Rule(
                        new NonTerminal("Z'"),
                        new Symbol[]{
                                new NonTerminal("Z"),
                                new Terminal("$")}),
                new Rule(
                        new NonTerminal("Z"),
                        new Symbol[]{
                                new NonTerminal("A")}),
                new Rule(
                        new NonTerminal("A"),
                        new Symbol[]{
                                new NonTerminal("B")}),
                new Rule(
                        new NonTerminal("A"),
                        new Symbol[]{
                                new Terminal("a")}),
                new Rule(
                        new NonTerminal("B"),
                        new Symbol[]{
                                new Terminal("b"),
                                new Terminal("*"),
                                new Terminal("a"),
                        })
        });

        assertThat(grammar.first(new NonTerminal("Z'"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("Z"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("A"))).isEqualTo(new Terminal[] {
                new Terminal("a"),
                new Terminal("b"),
        });

        assertThat(grammar.first(new NonTerminal("B"))).isEqualTo(new Terminal[] {
                new Terminal("b")
        });

        assertThat(grammar.follow(new NonTerminal("Z'"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("Z"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("A"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
        assertThat(grammar.follow(new NonTerminal("B"))).isEqualTo(new Terminal[]{
                new Terminal("$")
        });
    }

    @Test
    public void shouldParseMoreComplexGrammar() throws Exception {
        Grammar grammar = Grammar.parse(
                "Z -> A\n" +
                        "A -> A + B\n" +
                        "A -> a\n" +
                        "B -> b * a\n"
        );

        assertThat(grammar.nonTerminals()).isEqualTo(new NonTerminal[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
        });

        assertThat(grammar.terminals()).isEqualTo(new Terminal[] {
                new Terminal("$"),
                new Terminal("+"),
                new Terminal("a"),
                new Terminal("b"),
                new Terminal("*"),
        });

        assertThat(grammar.symbols()).isEqualTo(new Symbol[] {
                new NonTerminal("Z'"),
                new NonTerminal("Z"),
                new NonTerminal("A"),
                new NonTerminal("B"),
                new Terminal("$"),
                new Terminal("+"),
                new Terminal("a"),
                new Terminal("b"),
                new Terminal("*"),
        });
    }
}
