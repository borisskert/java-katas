package rankingpokerhands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/5739174624fc28e188000465/train/java
 */
public class PokerHand {
    public enum Result {TIE, WIN, LOSS}

    private final Hand hand;

    PokerHand(String hand) {
        this.hand = Hand.parse(hand);
    }

    public Result compareWith(PokerHand otherHand) {
        long thisHandValue = this.longValue();
        long otherHandValue = otherHand.longValue();

        if (thisHandValue > otherHandValue) {
            return Result.WIN;
        } else if (otherHandValue > thisHandValue) {
            return Result.LOSS;
        }

        return Result.TIE;
    }

    long longValue() {
        return HandValue.all()
                .stream()
                .filter(hv -> hv.test(this.hand))
                .findFirst()
                .map(hv -> hv.value(this.hand))
                .orElseThrow();
    }
}

class Hand {
    private static final Pattern HAND_PATTERN = Pattern.compile("([A-Z0-9]{2})");
    private final Cards cards;

    private Hand(Cards cards) {
        this.cards = cards;
    }

    Cards cards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    @Override
    public String toString() {
        return "{" +
                cards +
                '}';
    }

    static Hand parse(String hand) {
        Matcher matcher = HAND_PATTERN.matcher(hand);

        Cards parsedCards = new Cards();

        while (matcher.find()) {
            String card = matcher.group(1);
            parsedCards.add(Card.parse(card));
        }

        return new Hand(parsedCards);
    }

    static Hand of(Card... cards) {
        Cards createdCards = new Cards();
        Arrays.stream(cards).forEach(createdCards::add);

        return new Hand(createdCards);
    }
}

interface HandValue extends Predicate<Hand> {
    long value(Hand hand);

    static Collection<HandValue> all() {
        return List.of(
                new RoyalFlush(),
                new StraightFlush(),
                new FourOfaKind(),
                new FullHouse(),
                new Flush(),
                new Straight(),
                new ThreeOfAKind(),
                new TwoPairs(),
                new Pair(),
                new HighCard()
        );
    }
}

class HighCard implements HandValue {
    @Override
    public long value(Hand hand) {
        return hand.cards().longValue();
    }

    @Override
    public boolean test(Hand hand) {
        return true;
    }
}

class Pair implements HandValue {

    @Override
    public long value(Hand hand) {
        Cards pair = hand.cards().findPairs()
                .stream()
                .findFirst()
                .orElseThrow();

        Cards otherCards = hand.cards().except(pair);

        return (1L << 32) + (pair.longValue() << 14) + otherCards.longValue();
    }

    @Override
    public boolean test(Hand hand) {
        return hand.cards()
                .findPairs()
                .size() == 1;
    }
}

class TwoPairs implements HandValue {

    @Override
    public long value(Hand hand) {
        Cards handCards = hand.cards();
        Collection<Cards> pairs = handCards.findPairs();

        Cards pairCards = pairs.stream()
                .flatMap(Cards::stream)
                .collect(Cards.collectTo());

        Cards otherCards = handCards.except(pairCards);

        return (1L << 34) + (pairCards.longValue() << 14) + otherCards.longValue();
    }

    @Override
    public boolean test(Hand hand) {
        return hand.cards()
                .findPairs()
                .size() == 2;
    }
}

class ThreeOfAKind implements HandValue {

    @Override
    public long value(Hand hand) {
        Cards handCards = hand.cards();
        Cards threeOfaKind = handCards.findThreeOfaKind().orElseThrow();
        Cards otherCards = handCards.except(threeOfaKind);

        return (1L << 36) + (threeOfaKind.longValue() << 14) + otherCards.longValue();
    }

    @Override
    public boolean test(Hand hand) {
        return hand.cards()
                .findThreeOfaKind()
                .isPresent();
    }
}

class Straight implements HandValue {

    @Override
    public long value(Hand hand) {
        long straightValue = hand.cards()
                .stream()
                .map(Card::value)
                .map(Value::longValue)
                .reduce(Long::sum)
                .orElse(0L);

        return (1L << 38) + straightValue;
    }

    @Override
    public boolean test(Hand hand) {
        return isStraight(hand.cards());
    }

    static boolean isStraight(Cards cards) {
        List<Value> sortedValues = cards
                .stream()
                .sorted(Card.comparingByValue())
                .map(Card::value)
                .collect(Collectors.toUnmodifiableList());

        Long lastValue = null;
        for (Value value : sortedValues) {
            long longValue = value.longValue();

            if (lastValue == null) {
                lastValue = longValue;
            } else if (longValue == lastValue * 2L) {
                lastValue = longValue;
            } else {
                return false;
            }
        }

        return true;
    }
}

class Flush implements HandValue {

    @Override
    public long value(Hand hand) {
        long flushValue = hand.cards()
                .stream()
                .map(Card::value)
                .map(Value::longValue)
                .reduce(Long::sum)
                .orElse(0L);

        return (1L << 40) + flushValue;
    }

    @Override
    public boolean test(Hand hand) {
        return isFlush(hand.cards());
    }

    static boolean isFlush(Cards cards) {
        return cards
                .colors()
                .size() == 1;
    }
}

class FullHouse implements HandValue {

    @Override
    public long value(Hand hand) {
        Cards handCards = hand.cards();

        long threeOfaKindValue = handCards.findThreeOfaKind()
                .orElseThrow()
                .stream()
                .map(Card::value)
                .map(Value::longValue)
                .reduce(Long::sum)
                .orElse(0L) << 14;

        long cardValues = hand.cards()
                .findPairs()
                .stream()
                .flatMap(Cards::stream)
                .map(Card::value)
                .map(Value::longValue)
                .reduce(Long::sum)
                .orElse(0L);

        return (1L << 42) + threeOfaKindValue + cardValues;
    }

    @Override
    public boolean test(Hand hand) {
        Cards handCards = hand.cards();

        return handCards.findThreeOfaKind().isPresent()
                && handCards.findPairs().size() == 1;
    }
}

class FourOfaKind implements HandValue {

    @Override
    public long value(Hand hand) {
        Cards handCards = hand.cards();
        Cards fourOfaKind = handCards
                .findFourOfaKind()
                .orElseThrow();
        Cards otherCards = handCards.except(fourOfaKind);

        return (1L << 44) + (fourOfaKind.longValue() << 14) + otherCards.longValue();
    }

    @Override
    public boolean test(Hand hand) {
        return hand.cards()
                .findFourOfaKind()
                .isPresent();
    }
}

class StraightFlush implements HandValue {

    @Override
    public long value(Hand hand) {
        long straightFlushValue = hand.cards().longValue();
        return (1L << 46) + (straightFlushValue << 14);
    }

    @Override
    public boolean test(Hand hand) {
        Cards handCards = hand.cards();

        return Flush.isFlush(handCards)
                && Straight.isStraight(handCards);
    }
}

class RoyalFlush implements HandValue {

    @Override
    public long value(Hand hand) {
        long royalFlushValue = hand.cards().longValue();
        return (1L << 48) + royalFlushValue;
    }

    @Override
    public boolean test(Hand hand) {
        Cards handCards = hand.cards();

        return Flush.isFlush(handCards)
                && Straight.isStraight(handCards)
                && handCards.contains(Value.ACE)
                ;
    }
}

class Card {
    private static final Pattern CARD_PATTERN = Pattern.compile("^(?<value>.)(?<color>.)$");
    private final Value value;
    private final Color color;

    private Card(Value value, Color color) {
        this.value = value;
        this.color = color;
    }

    Value value() {
        return value;
    }

    Color color() {
        return color;
    }

    static Card of(Value value, Color color) {
        return new Card(value, color);
    }

    static Comparator<Card> comparingByValue() {
        return (o1, o2) -> Value.comparing().compare(o1.value, o2.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public String toString() {
        return "{" +
                value +
                ", " + color +
                '}';
    }

    static Card parse(String card) {
        Matcher matcher = CARD_PATTERN.matcher(card);

        if (matcher.matches()) {
            String color = matcher.group("color");
            String value = matcher.group("value");

            Color parsedColor = Color.parse(color);
            Value parsedValue = Value.parse(value);

            return new Card(parsedValue, parsedColor);
        }

        throw new RuntimeException("Cannot parse card: " + card);
    }
}

enum Color {
    SPADES("S"), HEARTS("H"), DIAMONDS("D"), CLUBS("C");

    private final String text;

    Color(String text) {
        this.text = text;
    }

    static Color parse(String text) {
        return Arrays.stream(Color.values())
                .filter(v -> v.text.equals(text))
                .findFirst()
                .orElseThrow();
    }
}

enum Value {
    TWO("2", 1L),
    THREE("3", 1L << 1),
    FOUR("4", 1L << 2),
    FIVE("5", 1L << 3),
    SIX("6", 1L << 4),
    SEVEN("7", 1L << 5),
    EIGHT("8", 1L << 6),
    NINE("9", 1L << 7),
    TEN("T", 1L << 8),
    JACK("J", 1L << 9),
    QUEEN("Q", 1L << 10),
    KING("K", 1L << 11),
    ACE("A", 1L << 12);

    private static final Comparator<Value> COMPARATOR = new ValueComparator();

    private final String text;
    private final long value;

    Value(String text, long value) {
        this.text = text;
        this.value = value;
    }

    long longValue() {
        return value;
    }

    static Comparator<Value> comparing() {
        return COMPARATOR;
    }

    static Value parse(String text) {
        return Arrays.stream(Value.values())
                .filter(v -> v.text.equals(text))
                .findFirst()
                .orElseThrow();
    }

    private static class ValueComparator implements Comparator<Value> {
        @Override
        public int compare(Value o1, Value o2) {
            return Long.compare(o1.value, o2.value);
        }
    }
}


class Cards {
    private final Set<Card> allCards = new HashSet<>();
    private final MultiMap<Value, Card> cardsByValue = new MultiMap<>();
    private final MultiMap<Color, Card> cardsByColor = new MultiMap<>();

    void add(Card card) {
        allCards.add(card);
        cardsByValue.put(card.value(), card);
        cardsByColor.put(card.color(), card);
    }

    void addAll(Collection<Card> cards) {
        allCards.addAll(cards);
        cards.forEach(card -> {
            cardsByValue.put(card.value(), card);
            cardsByColor.put(card.color(), card);
        });
    }

    Set<Color> colors() {
        return cardsByColor.keySet();
    }

    Collection<Cards> findPairs() {
        return cardsByValue.entrySet()
                .stream()
                .sorted((o1, o2) -> Value.comparing().compare(o1.getKey(), o2.getKey()))
                .filter(e -> e.getValue().size() == 2)
                .map(Map.Entry::getValue)
                .map(Cards::of)
                .collect(Collectors.toUnmodifiableList());
    }

    Optional<Cards> findThreeOfaKind() {
        return cardsByValue.entrySet()
                .stream()
                .filter(e -> e.getValue().size() == 3)
                .map(Map.Entry::getValue)
                .map(Cards::of)
                .findFirst();
    }

    Optional<Cards> findFourOfaKind() {
        return cardsByValue.entrySet()
                .stream()
                .filter(e -> e.getValue().size() == 4)
                .map(Map.Entry::getValue)
                .map(Cards::of)
                .findFirst();
    }

    Stream<Card> stream() {
        return allCards.stream().sorted(Card.comparingByValue());
    }

    Cards except(Cards others) {
        List<Card> cardsExceptOthers = stream()
                .filter(card -> !others.allCards.contains(card))
                .collect(Collectors.toUnmodifiableList());

        return Cards.of(cardsExceptOthers);
    }

    long longValue() {
        return stream()
                .map(Card::value)
                .map(Value::longValue)
                .reduce(Long::sum)
                .orElse(0L);
    }

    boolean contains(Value value) {
        return allCards.stream().map(Card::value).anyMatch(v -> v.equals(value));
    }

    static Cards of(Collection<Card> cards) {
        Cards newCards = new Cards();
        newCards.addAll(cards);

        return newCards;
    }

    static Collector<Card, ?, Cards> collectTo() {
        return new Collector<Card, List<Card>, Cards>() {

            @Override
            public Supplier<List<Card>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<Card>, Card> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<Card>> combiner() {
                return (list, other) -> {
                    list.addAll(other);
                    return list;
                };
            }

            @Override
            public Function<List<Card>, Cards> finisher() {
                return Cards::of;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }
}

class MultiMap<K, V> {
    private final Map<K, Collection<V>> entries = new HashMap<>();

    void put(K key, V value) {
        Collection<V> values = entries.getOrDefault(key, new ArrayList<>());
        values.add(value);

        entries.put(key, values);
    }

    Set<K> keySet() {
        return entries.keySet();
    }

    Set<Map.Entry<K, Collection<V>>> entrySet() {
        return entries.entrySet();
    }
}
