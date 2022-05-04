package thepoetandthependulum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * https://www.codewars.com/kata/5bd776533a7e2720c40000e5/train/java
 */
public class Poet {
    public static int[] pendulum(final int[] values) {
        return Elements.from(values)
                .sorted()
                .toArray();
    }
}

class Elements {
    private final List<Element> elements;

    private Elements(List<Element> elements) {
        this.elements = elements;
    }

    public Elements sorted() {
        List<Element> sortedElements = elements.stream()
                .sorted()
                .toList();

        return new Elements(sortedElements);
    }

    public static Elements from(final int[] values) {
        int[] sortedValues = Arrays.stream(values)
                .sorted()
                .toArray();

        List<Element> elements = IntStream
                .range(0, values.length)
                .mapToObj(index -> new Element(sortedValues[index], index))
                .toList();

        return new Elements(elements);
    }

    public int[] toArray() {
        return elements.stream()
                .map(Element::value)
                .toList()
                .stream()
                .mapToInt(value -> value)
                .toArray();
    }
}

class Element implements Comparable<Element> {
    private final int value;
    private final int index;


    Element(int value, int index) {
        this.value = value;
        this.index = index;
    }

    public int value() {
        return value;
    }

    public boolean evenIndex() {
        return index % 2 == 0;
    }

    @Override
    public int compareTo(Element other) {
        boolean evenIndex = this.evenIndex();
        boolean otherEvenIndex = other.evenIndex();

        if (evenIndex != other.evenIndex()) {
            return Boolean.compare(otherEvenIndex, evenIndex);
        }

        if (evenIndex) {
            return Integer.compare(other.index, this.index);
        }

        return Integer.compare(this.index, other.index);
    }
}
