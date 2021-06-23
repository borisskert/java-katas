package conwaysgameoflife;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * https://www.codewars.com/kata/52423db9add6f6fc39000354
 */
public class ConwayLife {

    public static int[][] getGeneration(int[][] cellsAsArray, int generations) {
        Cells cells = Cells.of(cellsAsArray);

        for (int counter = 0; counter < generations; counter++) {
            cells = cells.nextGeneration();
        }

        return cells.toArray();
    }

}

class Cells {
    private final Map<Position, Cell> cells;

    private Cells(Map<Position, Cell> cells) {
        this.cells = cells;
    }

    Cells nextGeneration() {
        Stream<Cell> bornCells = cells.values().stream()
                .filter(Cell::isAlive)
                .flatMap(c -> c.neighbors(this::at).stream())
                .filter(Cell::isDead)
                .map(c -> c.nextGeneration(this::at))
                .filter(Cell::isAlive);

        Stream<Cell> livingCells = cells.values()
                .stream()
                .map(cell -> cell.nextGeneration(this::at))
                .filter(Cell::isAlive);

        Map<Position, Cell> nextGeneration = Stream.concat(
                livingCells,
                bornCells
        )
                .distinct()
                .collect(
                        toMap(
                                Cell::position,
                                Function.identity()
                        )
                );

        return new Cells(nextGeneration);
    }

    int[][] toArray() {
        CellsArray cellsArray = new CellsArray();
        cellsArray.addAll(cells.values());

        return cellsArray.toArray();
    }

    private Cell at(Position position) {
        return Optional.ofNullable(cells.get(position))
                .orElseGet(() -> Cell.dead(position));
    }

    static Cells of(int[][] cells) {
        return new Cells(toCellsMap(cells));
    }

    private static Map<Position, Cell> toCellsMap(int[][] cellsArray) {
        Map<Position, Cell> cells = new HashMap<>();

        for (int x = 0; x < cellsArray.length; x++) {
            for (int y = 0; y < cellsArray[x].length; y++) {
                Position position = Position.of(x, y);

                if (cellsArray[x][y] == 1) {
                    cells.put(position, Cell.living(position));
                }
            }
        }

        return cells;
    }
}

class Cell {
    private final Position position;
    private final boolean isAlive;
    private final Set<Position> neighbors;

    private Cell(Position position, boolean isAlive, Set<Position> neighbors) {
        this.position = position;
        this.isAlive = isAlive;
        this.neighbors = neighbors;
    }

    Set<Cell> neighbors(Function<Position, Cell> retrieveCell) {
        return neighbors.stream()
                .map(retrieveCell)
                .collect(Collectors.toUnmodifiableSet());
    }

    Cell nextGeneration(Function<Position, Cell> retrieveCell) {
        Set<Cell> neighbors = neighbors(retrieveCell);

        long livingNeighborsCount = neighbors.stream().filter(n -> n.isAlive).count();

        if (isAlive) {
            if (livingNeighborsCount < 2 || livingNeighborsCount > 3) {
                return die();
            }
        } else if (livingNeighborsCount == 3) {
            return spawn();
        }

        return this;
    }

    boolean isAlive() {
        return isAlive;
    }

    boolean isDead() {
        return !isAlive;
    }

    Position position() {
        return position;
    }

    int intValue() {
        if (isAlive) {
            return 1;
        }

        return 0;
    }

    private Cell die() {
        return new Cell(position, false, neighbors);
    }

    private Cell spawn() {
        return new Cell(position, true, neighbors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(position, cell.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    static Cell living(Position position) {
        return new Cell(position, true, position.neighbors());
    }

    static Cell dead(Position position) {
        return new Cell(position, false, position.neighbors());
    }
}

class Position {
    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Set<Position> neighbors() {
        Set<Position> neighbors = new HashSet<>();

        for (int x = this.x - 1; x <= this.x + 1; x++) {
            for (int y = this.y - 1; y <= this.y + 1; y++) {
                Position neighbor = Position.of(x, y);

                if (neighbor.equals(this)) {
                    continue;
                }

                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position that = (Position) o;
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

    static Position of(int x, int y) {
        return new Position(x, y);
    }
}

class CellsArray {
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    private final Set<Cell> cells = new HashSet<>();

    void add(Cell cell) {
        Position position = cell.position();
        int x = position.x();
        if (minX > x) {
            minX = x;
        }
        if (maxX < x) {
            maxX = x;
        }

        int y = position.y();
        if (minY > y) {
            minY = y;
        }
        if (maxY < y) {
            maxY = y;
        }

        cells.add(cell);
    }

    void addAll(Collection<Cell> cells) {
        for (Cell cell : cells) {
            add(cell);
        }
    }

    int[][] toArray() {
        int width = maxX - minX;
        int height = maxY - minY;

        int[][] cellsArray = new int[width + 1][height + 1];

        for (Cell cell : cells) {
            Position position = cell.position();
            cellsArray[position.x() - minX][position.y() - minY] = cell.intValue();
        }

        return cellsArray;
    }
}
