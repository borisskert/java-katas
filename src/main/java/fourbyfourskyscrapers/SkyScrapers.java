package fourbyfourskyscrapers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/5671d975d81d6c1c87000022
 */
public class SkyScrapers {
    public static final int SIZE = 4;

    static int[][] solvePuzzle(int[] cluesAsArray) {
        Clues clues = Clues.of(cluesAsArray);

        return Fields.empty()
                .solve(clues)
                .toArray();
    }
}

class Clues {
    private final List<Clue> top;
    private final List<Clue> bottom;
    private final List<Clue> left;
    private final List<Clue> right;

    private Clues(
            List<Clue> top,
            List<Clue> bottom,
            List<Clue> left,
            List<Clue> right
    ) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    Clue top(int index) {
        return top.stream()
                .filter(c -> c.hasIndex(index))
                .findFirst()
                .orElseThrow();
    }

    Clue right(int index) {
        return right.stream()
                .filter(c -> c.hasIndex(index))
                .findFirst()
                .orElseThrow();
    }

    Clue left(int index) {
        return left.stream()
                .filter(c -> c.hasIndex(index))
                .findFirst()
                .orElseThrow();
    }

    Clue bottom(int index) {
        return bottom.stream()
                .filter(c -> c.hasIndex(index))
                .findFirst()
                .orElseThrow();
    }

    static Clues of(int[] clues) {
        List<Clue> top = extractTopClues(clues);
        List<Clue> right = extractRightClues(clues);
        List<Clue> bottom = extractBottomClues(clues);
        List<Clue> left = extractLeftClues(clues);

        return new Clues(top, bottom, left, right);
    }

    private static List<Clue> extractTopClues(int[] clues) {
        List<Clue> top = new ArrayList<>(SkyScrapers.SIZE);
        for (int i = 0; i < SkyScrapers.SIZE; i++) {
            int value = clues[i];
            Clue clue = Clue.of(value, i);

            top.add(clue);
        }

        return top;
    }

    private static List<Clue> extractRightClues(int[] clues) {
        List<Clue> right = new ArrayList<>(SkyScrapers.SIZE);
        for (int i = SkyScrapers.SIZE; i < SkyScrapers.SIZE * 2; i++) {
            int value = clues[i];
            int index = i - SkyScrapers.SIZE;
            Clue clue = Clue.of(value, index);

            right.add(clue);
        }

        return right;
    }

    private static List<Clue> extractBottomClues(int[] clues) {
        List<Clue> bottom = new ArrayList<>(SkyScrapers.SIZE);
        for (int i = SkyScrapers.SIZE * 2; i < SkyScrapers.SIZE * 3; i++) {
            int value = clues[i];
            int index = SkyScrapers.SIZE - (i - SkyScrapers.SIZE * 2 + 1);
            Clue clue = Clue.of(value, index);

            bottom.add(clue);
        }

        return bottom;
    }

    private static List<Clue> extractLeftClues(int[] clues) {
        List<Clue> left = new ArrayList<>(SkyScrapers.SIZE);
        for (int i = SkyScrapers.SIZE * 3; i < SkyScrapers.SIZE * 4; i++) {
            int value = clues[i];
            int index = SkyScrapers.SIZE - (i - SkyScrapers.SIZE * 3 + 1);
            Clue clue = Clue.of(value, index);

            left.add(clue);
        }

        return left;
    }
}

class Clue {
    private final int value;
    private final int index;

    private Clue(Integer value, int index) {
        this.value = value;
        this.index = index;
    }

    boolean isEmpty() {
        return value == 0;
    }

    boolean is(int value) {
        return this.value == value;
    }

    boolean hasIndex(int index) {
        return this.index == index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return index == clue.index && Objects.equals(value, clue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, index);
    }

    @Override
    public String toString() {
        return "Clue{" +
                "value=" + value +
                ", index=" + index +
                '}';
    }

    static Clue of(int value, int index) {
        return new Clue(value, index);
    }
}

class Fields {
    private final List<Field> fields;

    private Fields(List<Field> fields) {
        this.fields = fields;
    }

    Fields with(Coordinates coordinates, int height) {
        List<Field> newFields = fields.stream().map(field -> {
            if (field.hasCoordinates(coordinates)) {
                return field.withHeight(height);
            } else if (field.isOnSameRow(coordinates) || field.isOnSameColumn(coordinates)) {
                if (field.isUnsolved()) {
                    return field.withoutCandidate(height);
                }
            }

            return field;
        }).collect(Collectors.toUnmodifiableList());

        return new Fields(newFields);
    }

    int[][] toArray() {
        int[][] array = new int[SkyScrapers.SIZE][SkyScrapers.SIZE];

        fields.forEach(field -> array[field.y()][field.x()] = field.height());

        return array;
    }

    Fields solve(Clues clues) {
        Fields originFields;
        Fields solvedFields = this;

        Collection<SolveStrategy> solveStrategies = SolveStrategy.all();

        do {
            originFields = solvedFields;

            for (SolveStrategy solveStrategy : solveStrategies) {
                Collection<RowOrColumnAndClue> rowOrColumnAndClues = solvedFields.rowOrColumns(clues);

                for (RowOrColumnAndClue rowOrColumnAndClue : rowOrColumnAndClues) {
                    if (solveStrategy.isApplicable(rowOrColumnAndClue)) {
                        solvedFields = solvedFields.with(solveStrategy.solve(rowOrColumnAndClue).fields());
                    }
                }
            }
        } while (!originFields.equals(solvedFields));

        return solvedFields;
    }

    Collection<RowOrColumnAndClue> rowOrColumns(Clues clues) {
        Fields fields = this;
        Collection<RowOrColumnAndClue> rowOrColumns = new ArrayList<>();

        for (int index = 0; index < SkyScrapers.SIZE; index++) {
            rowOrColumns.add(RowOrColumn.rowFromRight(index, fields).with(clues.right(index)));
            rowOrColumns.add(RowOrColumn.rowFromLeft(index, fields).with(clues.left(index)));
            rowOrColumns.add(RowOrColumn.columnFromTop(index, fields).with(clues.top(index)));
            rowOrColumns.add(RowOrColumn.columnFromBottom(index, fields).with(clues.bottom(index)));
        }

        return rowOrColumns;
    }

    Stream<Field> stream() {
        return fields.stream();
    }

    private Fields with(Collection<Field> fields) {
        Fields newFields = this;

        for (Field field : fields) {
            if (field.isSolved()) {
                newFields = newFields.with(field);
            }
        }

        return newFields;
    }

    private Fields with(Field resolved) {
        return with(resolved.coordinates(), resolved.height());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fields fields1 = (Fields) o;
        return Objects.equals(fields, fields1.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }

    static Fields empty() {
        List<Field> newFields = Coordinates.all()
                .stream()
                .map(Field::empty)
                .collect(Collectors.toUnmodifiableList());

        return new Fields(newFields);
    }
}

class Field {
    private final Coordinates coordinates;
    private final Integer height;
    private final Set<Integer> candidates;

    private Field(Coordinates coordinates, Integer height, Set<Integer> candidates) {
        this.coordinates = coordinates;
        this.height = height;
        this.candidates = candidates;
    }

    Field withHeight(int height) {
        if(this.height != null) {
            if (this.height != height) {
                throw new RuntimeException("This field has a height already");
            }
        } else {
            if (!this.candidates.contains(height)) {
                throw new RuntimeException("This field has no candidate " + height);
            }
        }

        return new Field(coordinates, height, Set.of());
    }

    boolean hasCoordinates(Coordinates coordinates) {
        return this.coordinates.equals(coordinates);
    }

    boolean isOnSameRow(Coordinates coordinates) {
        return this.coordinates.isOnSameRow(coordinates);
    }

    boolean isOnSameColumn(Coordinates coordinates) {
        return this.coordinates.isOnSameColumn(coordinates);
    }

    Field withoutCandidate(int candidate) {
        Set<Integer> newCandidates = candidates.stream()
                .filter(c -> !c.equals(candidate))
                .collect(Collectors.toUnmodifiableSet());

        return new Field(coordinates, height, newCandidates);
    }

    int height() {
        return height != null ? height : 0;
    }

    int x() {
        return coordinates.x();
    }

    int y() {
        return coordinates.y();
    }

    boolean hasLastCandidate() {
        return candidates.size() == 1;
    }

    Coordinates coordinates() {
        return coordinates;
    }

    boolean hasCandidate(int candidate) {
        return candidates.contains(candidate);
    }

    boolean hasHeight(int height) {
        return this.height != null && this.height == height;
    }

    boolean isSolved() {
        return height != null;
    }

    boolean isUnsolved() {
        return height == null;
    }

    Collection<Integer> candidates() {
        return candidates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(coordinates, field.coordinates) && Objects.equals(height, field.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, height);
    }

    @Override
    public String toString() {
        return coordinates +
                " = " + height;
    }

    boolean isOnRow(int index) {
        return coordinates.isOnRow(index);
    }

    boolean isOnColumn(int index) {
        return coordinates.isOnColumn(index);
    }

    boolean hasNoCandidate(int candidate) {
        return !candidates.contains(candidate);
    }

    int lastCandidate() {
        if (candidates.size() == 1) {
            return candidates.stream().findFirst().orElseThrow();
        }

        throw new RuntimeException("Candidates not unique");
    }

    static Field empty(Coordinates coordinates) {
        return new Field(coordinates, null, Set.of(1, 2, 3, 4));
    }

    static Comparator<Field> compareRow() {
        return (o1, o2) -> Coordinates.compareRow()
                .compare(o1.coordinates(), o2.coordinates());
    }

    static Comparator<Field> compareColumn() {
        return (o1, o2) -> Coordinates.compareColumn()
                .compare(o1.coordinates(), o2.coordinates());
    }
}

class Coordinates {
    private final int x;
    private final int y;

    private Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean isOnSameRow(Coordinates other) {
        return y == other.y;
    }

    boolean isOnSameColumn(Coordinates other) {
        return x == other.x;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }

    boolean isOnRow(int index) {
        return y == index;
    }

    boolean isOnColumn(int index) {
        return x == index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x +
                "/" + y +
                ')';
    }

    static Set<Coordinates> all() {
        return IntStream.range(0, SkyScrapers.SIZE)
                .mapToObj(x ->
                        IntStream.range(0, SkyScrapers.SIZE).mapToObj(y -> Coordinates.of(x, y))
                                .collect(Collectors.toUnmodifiableSet()))
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    static Coordinates of(int x, int y) {
        return new Coordinates(x, y);
    }

    static Comparator<Coordinates> compareRow() {
        return Comparator.comparingInt(o -> o.y);
    }

    static Comparator<Coordinates> compareColumn() {
        return Comparator.comparingInt(o -> o.x);
    }
}

class RowOrColumn {
    private final List<Field> fields;

    private RowOrColumn(Stream<Field> fields) {
        this.fields = fields.collect(Collectors.toUnmodifiableList());
    }

    Field at(int index) {
        return fields.get(index);
    }

    Field last() {
        return fields.get(SkyScrapers.SIZE - 1);
    }

    RowOrColumn with(Field field) {
        return new RowOrColumn(fields.stream().map(f -> {
            if (f.hasCoordinates(field.coordinates())) {
                return field;
            }

            return f;
        }));
    }

    RowOrColumnAndClue with(Clue clue) {
        return RowOrColumnAndClue.of(this, clue);
    }

    RowOrColumn withHeightAt(int index, int height) {
        Field field = at(index);
        return this.with(field.withHeight(height));
    }

    Collection<Integer> candidates() {
        return fields.stream()
                .flatMap(f -> f.candidates().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    Field first() {
        return fields.get(0);
    }

    Field second() {
        return fields.get(1);
    }

    Field third() {
        return fields.get(2);
    }

    List<Field> fieldsWithCandidate(int candidate) {
        return fields.stream()
                .filter(f -> f.hasCandidate(candidate))
                .collect(Collectors.toUnmodifiableList());
    }

    Collection<Field> fields() {
        return fields;
    }

    Collection<Field> lastCandidates() {
        return fields.stream()
                .filter(Field::hasLastCandidate)
                .collect(Collectors.toUnmodifiableList());
    }

    boolean hasUniqueCandidates() {
        for (int height = 1; height < SkyScrapers.SIZE + 1; height++) {
            List<Field> fieldsWithCandidate = this.fieldsWithCandidate(height);

            if (fieldsWithCandidate.size() == 1) {
                return true;
            }
        }

        return false;
    }

    static RowOrColumn rowFromRight(int index, Fields fields) {
        return new RowOrColumn(fields.stream()
                .filter(field -> field.isOnRow(index))
                .sorted(Sort.reverse(Field.compareColumn())));
    }

    static RowOrColumn rowFromLeft(int index, Fields fields) {
        return new RowOrColumn(fields.stream()
                .filter(field -> field.isOnRow(index))
                .sorted(Field.compareColumn()));
    }

    static RowOrColumn columnFromTop(int index, Fields fields) {
        return new RowOrColumn(fields.stream()
                .filter(field -> field.isOnColumn(index))
                .sorted(Field.compareRow()));
    }

    static RowOrColumn columnFromBottom(int index, Fields fields) {
        return new RowOrColumn(fields.stream()
                .filter(field -> field.isOnColumn(index))
                .sorted(Sort.reverse(Field.compareRow())));
    }
}

class RowOrColumnAndClue {
    private final RowOrColumn rowOrColumn;
    private final Clue clue;

    private RowOrColumnAndClue(RowOrColumn rowOrColumn, Clue clue) {
        this.rowOrColumn = rowOrColumn;
        this.clue = clue;
    }

    Clue clue() {
        return clue;
    }

    RowOrColumn rowOrColumn() {
        return rowOrColumn;
    }

    static RowOrColumnAndClue of(RowOrColumn rowOrColumn, Clue clue) {
        return new RowOrColumnAndClue(rowOrColumn, clue);
    }
}

class Sort {
    static <T> Comparator<T> reverse(Comparator<T> comparator) {
        return (o1, o2) -> comparator.compare(o1, o2) * -1;
    }
}

interface SolveStrategy {
    RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue);

    boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue);

    static Collection<SolveStrategy> all() {
        return List.of(
                new SolveOrdered(),
                new SolveTallestAtBorder(),
                new SolveTwoScrapersWhenHighestIsLast(),
                new SolveThreeScrapersWhenHighestIsThird(),
                new SolveHighestScraperNotAsFirst(),
                new SolveTwoScrapersWhenHighestIsThird(),
                new SolveThreeScrapersWhenHighestIsLastAndLowestThird(),
                new ResolveLastCandidates(),
                new ResolveUniqueCandidates()
        );
    }
}

class SolveOrdered implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        for (int height = 1; height <= 4; height++) {
            int index = height - 1;
            rowOrColumn = rowOrColumn.withHeightAt(index, height);
        }


        return rowOrColumn;
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        return rowOrColumnAndClue.clue().is(4);
    }
}

class SolveTallestAtBorder implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        return rowOrColumn.with(rowOrColumn.first().withHeight(4));
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        return rowOrColumnAndClue.clue().is(1)
                && rowOrColumnAndClue.rowOrColumn().first().isUnsolved();
    }
}

class SolveTwoScrapersWhenHighestIsLast implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        return rowOrColumn.with(rowOrColumn.first().withHeight(3));
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        return rowOrColumnAndClue.clue().is(2)
                && rowOrColumn.last().hasHeight(4)
                && rowOrColumn.first().isUnsolved()
                ;
    }
}

/**
 *  3 > [ ? | ? | 4 | ? ]
 */
class SolveThreeScrapersWhenHighestIsThird implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        List<Integer> sortedCandidates = rowOrColumn.candidates()
                .stream()
                .sorted()
                .collect(Collectors.toUnmodifiableList());

        return rowOrColumn
                .with(rowOrColumn.first().withHeight(sortedCandidates.get(0)))
                .with(rowOrColumn.second().withHeight(sortedCandidates.get(1)))
                ;
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        return rowOrColumnAndClue.clue().is(3)
                && rowOrColumn.at(2).hasHeight(4)
                && rowOrColumn.candidates().size() == 2
                && rowOrColumn.first().isUnsolved()
                && rowOrColumn.second().isUnsolved()
                ;
    }
}

/**
 * E.g.
 * v   v  only these have 4 as candidate
 * [ ? | ? | ? | ? | ] < 2
 * ^  but this one can't be 4!
 */
class SolveHighestScraperNotAsFirst implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        List<Field> fieldsWithCandidate = rowOrColumn.fieldsWithCandidate(4);

        Field first = fieldsWithCandidate.get(0);
        Field second = fieldsWithCandidate.get(1);

        if (rowOrColumn.first().equals(first)) {
            return rowOrColumn.with(second.withHeight(4));
        } else if (rowOrColumn.first().equals(second)) {
            return rowOrColumn.with(first.withHeight(4));
        }

        throw new RuntimeException("Should not happen");
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        Clue clue = rowOrColumnAndClue.clue();
        if (clue.isEmpty() || clue.is(1)) {
            return false;
        }

        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        List<Field> fieldsWithCandidate = rowOrColumn.fieldsWithCandidate(4);
        if (fieldsWithCandidate.size() != 2) {
            return false;
        }

        return rowOrColumn.first().hasCandidate(4);
    }
}

/**
 * E.g.
 * v  when here is no candidate '3'
 * 2  >  [ ? | ? | 4 | ? ]
 * ^ then there have to be a height of '1'
 * ^  and have to be a height of '2'
 */
class SolveTwoScrapersWhenHighestIsThird implements SolveStrategy {
    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        return rowOrColumn
                .with(rowOrColumn.first().withHeight(2))
                .with(rowOrColumn.second().withHeight(1));
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        return rowOrColumnAndClue.clue().is(2)
                && rowOrColumn.third().hasHeight(4)
                && rowOrColumn.first().hasNoCandidate(3)
                && rowOrColumn.first().isUnsolved()
                && rowOrColumn.second().isUnsolved()
                ;
    }
}

/**
 * E.g.
 * <p>
 * 3  >  [ ? | ? | 1 | 4 ]
 * ^  there has to be '3'
 * ^  and there has to be '2'
 */
class SolveThreeScrapersWhenHighestIsLastAndLowestThird implements SolveStrategy {
    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        return rowOrColumn
                .with(rowOrColumn.first().withHeight(2))
                .with(rowOrColumn.second().withHeight(3))
                ;
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        return rowOrColumnAndClue.clue().is(3)
                && rowOrColumn.candidates().size() == 2
                && rowOrColumn.last().hasHeight(4)
                && rowOrColumn.third().hasHeight(1)
                ;
    }
}

class ResolveLastCandidates implements SolveStrategy {
    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();
        Collection<Field> fieldsWithLastCandidates = rowOrColumn.lastCandidates();

        for (Field field : fieldsWithLastCandidates) {
            rowOrColumn = rowOrColumn.with(field.withHeight(field.lastCandidate()));
        }

        return rowOrColumn;
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        return rowOrColumnAndClue.rowOrColumn().lastCandidates().size() > 0;
    }
}

class ResolveUniqueCandidates implements SolveStrategy {

    @Override
    public RowOrColumn solve(RowOrColumnAndClue rowOrColumnAndClue) {
        RowOrColumn rowOrColumn = rowOrColumnAndClue.rowOrColumn();

        for (int height = 1; height < SkyScrapers.SIZE + 1; height++) {
            List<Field> fieldsWithCandidate = rowOrColumn.fieldsWithCandidate(height);

            if (fieldsWithCandidate.size() == 1) {
                Field fieldWithCandidate = fieldsWithCandidate.get(0);
                rowOrColumn = rowOrColumn.with(fieldWithCandidate.withHeight(height));
            }
        }

        return rowOrColumn;
    }

    @Override
    public boolean isApplicable(RowOrColumnAndClue rowOrColumnAndClue) {
        return rowOrColumnAndClue.rowOrColumn().hasUniqueCandidates();
    }
}
