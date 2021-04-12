package calculator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
E -> E + T
E -> E - T
E -> T
T -> T * F
T -> T / F
T -> F
F -> ( E )
F -> F . N
F -> N
N -> Z
N -> N Z
Z -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
 */

// 1 + 1 + 1
    /*
    E
E X T
E X E X T
E X E X F
E X E X 1
E X E + 1
E X T + 1
E X F + 1
E X 1 + 1
E + 1 + 1
T + 1 + 1
F + 1 + 1
1 + 1 + 1

*/


/**
 * https://www.codewars.com/kata/5235c913397cbf2508000048/train/java
 */
public class Calculator {

    public static Double evaluate(String expression) {
        // your code here
        return -1.0;
    }
}

class Constants {
    public static final String TOKEN_SEPARATOR = " ";
    public static final String TERMINAL_SYMBOL = "$";
    public static final String START_SYMBOL_POSTFIX = "'";
}

class ParsedRule {

    final String left;
    final String right;

    public ParsedRule(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public static ParsedRule parse(String line) {
        String[] splitLine = line.split(" -> ");

        return new ParsedRule(splitLine[0], splitLine[1]);
    }

    public String left() {
        return left;
    }

    public String right() {
        return right;
    }

    public String[] rightTokens() {
        return right.split(Constants.TOKEN_SEPARATOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedRule parsedRule = (ParsedRule) o;
        return Objects.equals(left, parsedRule.left) && Objects.equals(right, parsedRule.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Rule{" + left + "->" + right + '}';
    }
}

class ParsedRules {
    private final ParsedRule[] rules;

    public ParsedRules(ParsedRule[] rules) {
        this.rules = rules;
    }

    public static ParsedRules parse(String grammar) {
        ParsedRule[] parsedRules = Arrays.stream(grammar.split("\n"))
                .map(ParsedRule::parse)
                .collect(Collectors.toUnmodifiableList())
                .toArray(new ParsedRule[0]);

        return new ParsedRules(parsedRules);
    }

    public ParsedRule[] rules() {
        return rules.clone();
    }

    public ParsedRule createInitial() {
        ParsedRule firstRule = rules[0];
        String virtualStartSymbol = firstRule.left + Constants.START_SYMBOL_POSTFIX;

        return new ParsedRule(
                virtualStartSymbol,
                firstRule.left + Constants.TOKEN_SEPARATOR + Constants.TERMINAL_SYMBOL
        );
    }

    public Stream<ParsedRule> stream() {
        return Stream.concat(
                Stream.of(createInitial()),
                Arrays.stream(rules())
        );
    }

    public ParsedRule[] bySymbol(NonTerminal nonTerminal) {
        return stream()
                .filter(r -> r.left.equals(nonTerminal.token))
                .collect(Collectors.toUnmodifiableList())
                .toArray(new ParsedRule[0]);
    }
}

class Rule implements Comparable<Rule> {
    final NonTerminal left;
    final Symbol[] right;

    public Rule(NonTerminal left, Symbol[] right) {
        this.left = left;
        this.right = right;
    }

    public NonTerminal left() {
        return left;
    }

    public Symbol[] right() {
        return right;
    }

    public boolean contains(Symbol symbol) {
        return Arrays.asList(right).contains(symbol);
    }

    public Optional<Symbol> follow(Symbol symbol) {
        for (int index = 0; index < right.length; index++) {
            Symbol currentSymbol = right[index];

            if (currentSymbol.equals(symbol)) {
                if (index + 1 < right.length) {
                    return Optional.of(right[index + 1]);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(left, rule.left) &&
                Arrays.equals(right, rule.right);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(left);
        result = 31 * result + Arrays.hashCode(right);
        return result;
    }

    @Override
    public String toString() {
        return "Rule{" + left + "->" + Arrays.toString(right) + '}';
    }

    @Override
    public int compareTo(Rule o) {
        int left = this.left.compareTo(o.left);

        if(left == 0) {
            return Arrays.compare(this.right, o.right);
        }

        return left;
    }
}

class Rules {
    private final Rule[] rules;

    public Rules(Rule[] rules) {
        this.rules = rules;
    }

    public Stream<Rule> stream() {
        return Arrays.stream(rules.clone());
    }

    public Rule[] array() {
        return rules.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rules rules1 = (Rules) o;
        return Arrays.equals(rules, rules1.rules);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rules);
    }

    @Override
    public String toString() {
        return "Rules{" +
                "rules=" + Arrays.toString(rules) +
                '}';
    }
}

class Grammar {
    private final Symbols symbols;
    private final Rules rules;

    public Grammar(Symbols symbols, Rules rules) {
        this.symbols = symbols;
        this.rules = rules;
    }

    public static Grammar parse(String grammar) {
        ParsedRules parsedRules = ParsedRules.parse(grammar);

        List<String> nonTerminalTokens = parsedRules.stream()
                .map(ParsedRule::left)
                .distinct()
                .collect(Collectors.toUnmodifiableList());

        Terminal[] terminals = parsedRules.stream()
                .flatMap(r -> Arrays.stream(r.rightTokens()))
                .distinct()
                .filter(token -> !nonTerminalTokens.contains(token))
                .map(Terminal::new)
                .collect(Collectors.toUnmodifiableList())
                .toArray(new Terminal[0]);

        NonTerminal[] nonTerminals = nonTerminalTokens.stream()
                .map(NonTerminal::new)
                .collect(Collectors.toUnmodifiableList())
                .toArray(new NonTerminal[0]);

        Symbol[] allSymbols = Stream.concat(
                Arrays.stream(nonTerminals),
                Arrays.stream(terminals)
        )
                .collect(Collectors.toUnmodifiableList())
                .toArray(new Symbol[0]);

        Symbols symbols = new Symbols(allSymbols);

        Rule[] rules = parsedRules.stream()
                .map(
                        r -> {
                            NonTerminal left = (NonTerminal) symbols.byToken(r.left);

                            Symbol[] right = Arrays.stream(r.rightTokens()).map(
                                    symbols::byToken
                            ).collect(Collectors.toUnmodifiableList())
                                    .toArray(new Symbol[0]);

                            return new Rule(left, right);
                        }
                ).collect(Collectors.toUnmodifiableList())
                .toArray(new Rule[0]);

        return new Grammar(symbols, new Rules(rules));
    }

    public Terminal[] terminals() {
        return symbols.terminals();
    }

    public NonTerminal[] nonTerminals() {
        return symbols.nonTerminals();
    }

    public Symbol[] symbols() {
        return symbols.all();
    }

    public Terminal[] first(NonTerminal nonTerminal) {
        return new First().collectForNonTerminal(nonTerminal);
    }

    public Terminal[] follow(NonTerminal nonTerminal) {
        if (nonTerminal.isStart()) {
            return new Terminal[]{
                    new Terminal(Constants.TERMINAL_SYMBOL)
            };
        }

        return new Follow().collectFor(nonTerminal);
    }

    public Rule[] rules() {
        return rules.array();
    }

    public Rule startingRule() {
        return rules.stream()
                .filter(r -> r.left.isStart())
                .findFirst()
                .orElseThrow();
    }

    public Rule[] rulesFor(NonTerminal symbol) {
        return rules.stream()
                .filter(r -> r.left.equals(symbol))
                .collect(Collectors.toUnmodifiableSet())
                .toArray(new Rule[0]);
    }

    /**
     * Collects the FIRST symbols for a given non-terminal symbol
     */
    private class First {
        private final Set<Symbol> visitedNonTerminals = new HashSet<>();
        private final Set<Symbol> collectedTerminals = new HashSet<>();

        private Terminal[] collectForNonTerminal(Symbol nonTerminal) {
            List<Symbol> symbols = rules.stream()
                    .filter(r -> r.left.equals(nonTerminal))
                    .map(Rule::right)
                    .map(a -> a[0])
                    .collect(Collectors.toUnmodifiableList());

            symbols.stream()
                    .filter(Symbol::isTerminal)
                    .distinct()
                    .forEach(collectedTerminals::add);

            List<Symbol> nonTerminals = symbols.stream()
                    .filter(s -> !s.isTerminal())
                    .distinct()
                    .filter(s -> !visitedNonTerminals.contains(s))
                    .collect(Collectors.toUnmodifiableList());

            visitedNonTerminals.addAll(nonTerminals);

            nonTerminals.stream()
                    .flatMap(s -> Arrays.stream(collectForNonTerminal(s)))
                    .distinct()
                    .forEach(collectedTerminals::add);

            return collectedTerminals.toArray(new Terminal[0]);
        }
    }

    /**
     * Collects the FOLLOW symbols for a non-terminal symbol
     */
    public class Follow {
        public Terminal[] collectFor(NonTerminal nonTerminal) {
            return rules.stream()
                    .filter(r ->
                            r.contains(nonTerminal)
                    )
                    .flatMap(r -> Arrays.stream(follow(r, nonTerminal)))
                    .collect(Collectors.toUnmodifiableSet()).toArray(new Terminal[0]);
        }

        private Terminal[] follow(Rule rule, NonTerminal nonTerminal) {
            Optional<Symbol> follow = rule.follow(nonTerminal);

            if (follow.isPresent()) {
                Symbol followingSymbol = follow.get();

                if (followingSymbol.isTerminal()) {
                    return new Terminal[]{(Terminal) followingSymbol};
                } else {
                    return first((NonTerminal) followingSymbol);
                }
            } else {
                return collectFor(rule.left);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grammar grammar = (Grammar) o;
        return Objects.equals(symbols, grammar.symbols) && Objects.equals(rules, grammar.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbols, rules);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "symbols=" + symbols +
                ", rules=" + rules +
                '}';
    }
}

interface Symbol extends Comparable<Symbol> {
    String token();

    boolean has(String token);

    boolean isTerminal();
}

class Symbols {
    private final Symbol[] symbols;

    public Symbols(Symbol[] symbols) {
        this.symbols = symbols;
    }

    public Symbol[] all() {
        return symbols.clone();
    }

    public NonTerminal[] nonTerminals() {
        return Arrays.stream(symbols)
                .filter(s -> !s.isTerminal())
                .collect(Collectors.toUnmodifiableList())
                .toArray(new NonTerminal[0]);
    }

    public Terminal[] terminals() {
        return Arrays.stream(symbols)
                .filter(Symbol::isTerminal)
                .collect(Collectors.toUnmodifiableList())
                .toArray(new Terminal[0]);
    }

    public Symbol byToken(String token) {
        return Arrays.stream(symbols)
                .filter(s -> s.token().equals(token))
                .findFirst()
                .orElseThrow();
    }

    public boolean contains(Symbol symbol) {
        return Arrays.asList(symbols).contains(symbol);
    }

    public Symbol[] toArray() {
        return symbols.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbols symbols1 = (Symbols) o;
        return Arrays.equals(symbols, symbols1.symbols);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(symbols);
    }

    @Override
    public String toString() {
        return "Symbols{" +
                "symbols=" + Arrays.toString(symbols) +
                '}';
    }
}

class Terminal implements Symbol {
    private final String token;

    public Terminal(String token) {
        this.token = token;
    }

    public String token() {
        return token;
    }

    @Override
    public boolean has(String token) {
        return Objects.equals(this.token, token);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return Objects.equals(token, terminal.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "Terminal{" + token + '}';
    }

    @Override
    public int compareTo(Symbol o) {
        return token.compareTo(o.token());
    }
}

class NonTerminal implements Symbol {
    final String token;

    public NonTerminal(String token) {
        this.token = token;
    }

    public String token() {
        return token;
    }

    @Override
    public boolean has(String token) {
        return Objects.equals(this.token, token);
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    public boolean isStart() {
        return token.endsWith(Constants.START_SYMBOL_POSTFIX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonTerminal that = (NonTerminal) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "NonTerminal{" + token + '}';
    }

    @Override
    public int compareTo(Symbol o) {
        return token.compareTo(o.token());
    }
}

class Instruction {
    private final InstructionType type;
    private final Integer parameter;

    private Instruction(InstructionType type, int parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    private Instruction(InstructionType type) {
        this.type = type;
        this.parameter = null;
    }

    static Instruction shift(int parameter) {
        return new Instruction(InstructionType.SHIFT, parameter);
    }

    static Instruction reduce(int parameter) {
        return new Instruction(InstructionType.REDUCE, parameter);
    }

    static Instruction accept() {
        return new Instruction(InstructionType.ACCEPT);
    }

    static Instruction gotoo(int parameter) {
        return new Instruction(InstructionType.GOTO, parameter);
    }
}

enum InstructionType {
    SHIFT,
    REDUCE,
    GOTO,
    ACCEPT,
}

class StateMachine {
    private final Grammar grammar;
    private final Set<State> states;

    StateMachine(Grammar grammar) {
        this.grammar = grammar;
        this.states = Arrays.stream(createStates()).collect(Collectors.toUnmodifiableSet());
    }

    State[] createStates() {
        Rule startingRule = grammar.startingRule();

        Set<State> states = new HashSet<>();
        ReadingRules startingRules = new ReadingRules(startingRule);
        State startingState = new State(startingRules.toArray());
        states.add(startingState);

        State[] extendedStates = extendState(startingState);

        Set<State> statesToAdd;
        do {
            statesToAdd = Arrays.stream(extendedStates).filter(s -> !states.contains(s))
                    .map(this::extendState)
                    .flatMap(Arrays::stream)
                    .filter(s -> !states.contains(s))
                    .collect(Collectors.toUnmodifiableSet());

            states.addAll(statesToAdd);
        } while(!statesToAdd.isEmpty());

        return states.toArray(new State[0]);
    }

    private State[] extendState(State state) {
        Set<State> states = new HashSet<>();
        states.add(state);
        Set<ReadingRules> readingRules = new HashSet<>();
        ReadingRules startingRules = new ReadingRules(state.rules()).expand();
        readingRules.add(startingRules);

        Symbol[] currentSymbols = startingRules.currentSymbols();
        for (Symbol currentSymbol : currentSymbols) {
            ReadingRules nextReadingRules = startingRules.read(currentSymbol);

            if (!readingRules.contains(nextReadingRules)) {
                readingRules.add(nextReadingRules);
                State stateToAdd = new State(nextReadingRules.toArray());
                states.add(stateToAdd);
            }
        }

        return states.toArray(new State[0]);
    }

    StateMachine(Grammar grammar, State[] states) {
        this.grammar = grammar;
        this.states = Arrays.stream(states).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String toString() {
        return "StateMachine{" +
                "grammar=" + grammar +
                ", states=" + states +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateMachine that = (StateMachine) o;
        return Objects.equals(grammar, that.grammar) && Objects.equals(states, that.states);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(grammar);
        result = 31 * result + Objects.hash(states);
        return result;
    }

    class ReadingRules {
        private final Set<ReadingRule> readingRules;

        ReadingRules(ReadingRule[] readingRules) {
            if(readingRules == null) {
                throw new IllegalArgumentException("readingRules is null");
            }
            this.readingRules = Arrays.stream(readingRules)
                    .collect(Collectors.toUnmodifiableSet());
        }

        ReadingRules(Rule rule) {
            ReadingRule readingRule = new ReadingRule(rule);
            ReadingRules expandedRules = expand(readingRule.rule(), readingRule.cursorAt());

            this.readingRules = Arrays.stream(expandedRules.toArray())
                    .collect(Collectors.toUnmodifiableSet());
        }

        ReadingRules(ReadingRule readingRule) {
            ReadingRules expandedRules = expand(readingRule.rule(), readingRule.cursorAt());
            this.readingRules = Arrays.stream(expandedRules.toArray())
                    .collect(Collectors.toUnmodifiableSet());
        }

        public State toState() {
            return new State(this.readingRules.toArray(ReadingRule[]::new));
        }

        ReadingRules expand() {
            Set<ReadingRule> collect = readingRules.stream()
                    .map(r -> expand(r.rule(), r.cursorAt()))
                    .map(ReadingRules::toArray)
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toUnmodifiableSet());

            return new ReadingRules(collect.toArray(new ReadingRule[0]));
        }

        ReadingRules expand(Rule rule, int cursorAt) {
            return new ExpandingRules().expand(rule, cursorAt);
        }

        private class ExpandingRules {
            private final Set<ReadingRule> expandedReadingRules = new HashSet<>();

            ReadingRules expand(ReadingRule readingRule) {
                ReadingRules expanded = expand(readingRule.rule(), readingRule.cursorAt());

                expandedReadingRules.addAll(expanded.stream().collect(Collectors.toUnmodifiableSet()));

                return expanded;
            }

            ReadingRules expand(Rule rule, int cursorAt) {
                if(cursorAt == rule.right.length) {
                    return new ReadingRules(new ReadingRule[]{new ReadingRule(rule, cursorAt)});
                }

                Symbol symbol = rule.right[cursorAt];

                if (!symbol.isTerminal()) {
                    Rule[] rulesForSymbol = grammar.rulesFor((NonTerminal) symbol);

                    Set<ReadingRule> readingRulesForSymbol = Arrays.stream(rulesForSymbol)
                            .map(r -> new ReadingRule(r, 0))
                            .filter(r -> !expandedReadingRules.contains(r))
                            .map(this::expand)
                            .flatMap(ReadingRules::stream)
                            .filter(r -> !expandedReadingRules.contains(r))
                            .collect(Collectors.toUnmodifiableSet());

                    Stream<ReadingRule> concat = Stream.concat(
                            Stream.of(new ReadingRule(rule, cursorAt)),
                            readingRulesForSymbol.stream() // TODO also expand created rule
                    );

                    Set<ReadingRule> collect = concat.collect(Collectors.toUnmodifiableSet());
                    ReadingRule[] readingRules = collect
                            .toArray(new ReadingRule[0]);

                    return new ReadingRules(readingRules);
                } else {
                    return new ReadingRules(new ReadingRule[]{new ReadingRule(rule, cursorAt)});
                }
            }
        }

        ReadingRule[] toArray() {
            return readingRules.toArray(ReadingRule[]::new);
        }

        Stream<ReadingRule> stream() {
            return readingRules.stream();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReadingRules that = (ReadingRules) o;
            return readingRules.equals(that.readingRules);
        }

        @Override
        public int hashCode() {
            return readingRules.hashCode();
        }

        @Override
        public String toString() {
            return "ReadingRules{" +
                    "readingRules=" + readingRules.toString() +
                    '}';
        }

        public Symbol[] currentSymbols() {
            return readingRules.stream()
                    .filter(r -> !r.isFinalized())
                    .map(ReadingRule::currentSymbol)
                    .collect(Collectors.toUnmodifiableSet())
                    .toArray(new Symbol[0]);
        }

        public ReadingRules read(Symbol symbol) {
            ReadingRule[] readingRules = this.readingRules.stream()
                    .filter(r -> !r.isFinalized())
                    .filter(r -> r.currentSymbol().equals(symbol))
                    .map(ReadingRule::next)
                    .map(ReadingRules::new)
                    .map(ReadingRules::expand)
                    .map(ReadingRules::toArray)
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toUnmodifiableSet())
                    .toArray(new ReadingRule[0]);

            return new ReadingRules(readingRules);
        }
    }
}

class ReadingRule implements Comparable<ReadingRule> {
    private final Rule rule;
    private final int cursorAt;

    ReadingRule(Rule rule, int cursorAt) {
        this.rule = rule;
        this.cursorAt = cursorAt;
    }

    ReadingRule(Rule rule) {
        this.rule = rule;
        this.cursorAt = 0;
    }

    int cursorAt() {
        return cursorAt;
    }

    Rule rule() {
        return rule;
    }

    ReadingRule next() {
        return new ReadingRule(rule, cursorAt + 1);
    }

    Symbol currentSymbol() {
        return rule.right[cursorAt];
    }

    boolean isFinalized() {
        return cursorAt >= rule.right.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingRule that = (ReadingRule) o;
        return cursorAt == that.cursorAt && Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, cursorAt);
    }

    @Override
    public String toString() {
        return "ReadingRule{" +
                "rule=" + rule +
                ", cursorAt=" + cursorAt +
                '}';
    }

    @Override
    public int compareTo(ReadingRule o) {
        int cursorAt = Integer.compare(this.cursorAt, o.cursorAt);

        if(cursorAt == 0) {
            return this.rule.compareTo(o.rule);
        }

        return cursorAt;
    }
}

class State implements Comparable<State> {
    private final Set<ReadingRule> rules;

    State(ReadingRule[] rules) {
        this.rules = Arrays.stream(rules).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(rules, state.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rules);
    }

    @Override
    public String toString() {
        return "State{" +
                "rules=" + rules +
                '}';
    }

    public ReadingRule[] rules() {
        return rules.toArray(new ReadingRule[0]);
    }

    @Override
    public int compareTo(State o) {
        return Arrays.compare(this.rules.toArray(new ReadingRule[0]), o.rules.toArray(new ReadingRule[0]));
    }
}
