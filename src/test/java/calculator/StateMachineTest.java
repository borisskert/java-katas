package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateMachineTest {

    @Test
    public void shouldCreateStateMachineForSimpleGrammar() throws Exception {
        Grammar grammar = Grammar.parse(
                "Z -> A\n"
        );

        StateMachine machine = new StateMachine(grammar);

        assertThat(machine).isEqualTo(new StateMachine(grammar, new State[]{
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new Terminal("A")
                                }),
                                0
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new Terminal("A")
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                2
                        )
                }),
        }));
    }

    @Test
    public void shouldCreateStateMachineForNotSoSimpleGrammar() throws Exception {
        Grammar grammar = Grammar.parse(
                "Z -> A\n" +
                        "A -> a"
        );

        StateMachine machine = new StateMachine(grammar);

        assertThat(machine).isEqualTo(new StateMachine(grammar, new State[]{
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new NonTerminal("A")
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new Terminal("a")
                                }),
                                0
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new NonTerminal("A")
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new Terminal("a")
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                2
                        )
                }),
        }));
    }

    @Test
    public void shouldCreateStateMachineForSlightlyComplexGrammar() throws Exception {
        Grammar grammar = Grammar.parse(
                "Z -> A\n" +
                        "A -> A + B\n" +
                        "A -> a\n" +
                        "B -> b * a\n"
        );

        StateMachine machine = new StateMachine(grammar);

        assertThat(machine).isEqualTo(new StateMachine(grammar, new State[]{
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new NonTerminal("A")
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new NonTerminal("A"),
                                        new Terminal("+"),
                                        new Terminal("B"),
                                }),
                                0
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new Terminal("a"),
                                }),
                                0
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z'"), new Symbol[]{
                                        new NonTerminal("Z"),
                                        new Terminal("$")
                                }),
                                1
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("Z"), new Symbol[]{
                                        new NonTerminal("A"),
                                }),
                                1
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new NonTerminal("A"),
                                        new Terminal("+"),
                                        new Terminal("B"),
                                }),
                                1
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new Terminal("a"),
                                }),
                                1
                        )
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new NonTerminal("A"),
                                        new Terminal("+"),
                                        new NonTerminal(" "),
                                }),
                                2
                        ),
                        new ReadingRule(
                                new Rule(new NonTerminal("B"), new Symbol[]{
                                        new Terminal("a"),
                                        new Terminal("*"),
                                        new Terminal("b"),
                                }),
                                0
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("A"), new Symbol[]{
                                        new NonTerminal("A"),
                                        new Terminal("+"),
                                        new NonTerminal("B"),
                                }),
                                3
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("B"), new Symbol[]{
                                        new Terminal("a"),
                                        new Terminal("*"),
                                        new Terminal("b"),
                                }),
                                1
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("B"), new Symbol[]{
                                        new Terminal("a"),
                                        new Terminal("*"),
                                        new Terminal("b"),
                                }),
                                2
                        ),
                }),
                new State(new ReadingRule[]{
                        new ReadingRule(
                                new Rule(new NonTerminal("B"), new Symbol[]{
                                        new Terminal("a"),
                                        new Terminal("*"),
                                        new Terminal("b"),
                                }),
                                3
                        ),
                }),
        }));
    }
}
