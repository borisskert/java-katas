package connectfour;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/56882731514ec3ec3d000009/train/java
 */
public class ConnectFour {
    public static String whoIsWinner(List<String> piecesPositionList) {
        Moves moves = Moves.of(piecesPositionList);
        Grid grid = new Grid();

        for (Move move : moves) {
            grid.add(move);

            Optional<Color> winner = grid.winner();

            if (winner.isPresent()) {
                return winner.map(Color::name)
                        .orElseThrow();
            }
        }

        return "Draw";
    }
}

class Grid {
    private static final int MAX_HEIGHT = 5;

    private final List<GridColumn> grid = List.of(
            new GridColumn(),
            new GridColumn(),
            new GridColumn(),
            new GridColumn(),
            new GridColumn(),
            new GridColumn(),
            new GridColumn()
    );

    void add(Move move) {
        int index = move.getColumn().index();
        grid.get(index).push(move.getColor());
    }

    Optional<Color> winner() {
        return verticalWinner()
                .or(this::horizontalWinner)
                .or(this::diagonalWinner);
    }

    private Optional<Color> get(Coordinate coordinate) {
        return grid.get(coordinate.x()).get(coordinate.y());
    }

    private Optional<Color> verticalWinner() {
        return grid
                .stream()
                .map(GridColumn::verticalWinner)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<Color> horizontalWinner() {
        for (int index = 0; index <= MAX_HEIGHT; index++) {
            Optional<Color> color = horizontalWinner(index);

            if (color.isPresent()) {
                return color;
            }
        }

        return Optional.empty();
    }

    private Optional<Color> horizontalWinner(int index) {
        Optional<Color> color = Optional.empty();
        int counter = 0;

        for (GridColumn gridColumn : grid) {
            Optional<Color> currentColor = gridColumn.get(index);

            if (currentColor.isPresent()) {
                if (color.isEmpty()) {
                    color = currentColor;
                    counter++;
                } else if (color.get() == currentColor.get()) {
                    counter++;
                } else {
                    counter = 1;
                    color = currentColor;
                }
            } else {
                color = Optional.empty();
                counter = 0;
            }

            if (counter == 4) {
                return color;
            }
        }

        return Optional.empty();
    }

    private Optional<Color> diagonalWinner() {
        return diagonalWinner(Coordinate.of(Column.A.index(), 0), Coordinate::diagonalRightUp)
                .or(() -> diagonalWinner(Coordinate.of(Column.A.index(), 1), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.A.index(), 2), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.A.index(), 3), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.A.index(), 4), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.A.index(), 5), Coordinate::diagonalRightDown))

                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 0), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 1), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 2), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 3), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 4), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.B.index(), 5), Coordinate::diagonalRightDown))

                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 0), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 1), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 2), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 3), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 4), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.C.index(), 5), Coordinate::diagonalRightDown))

                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 0), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 1), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 2), Coordinate::diagonalRightUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 3), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 4), Coordinate::diagonalRightDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 5), Coordinate::diagonalRightDown))

                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 0), Coordinate::diagonalLeftUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 1), Coordinate::diagonalLeftUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 2), Coordinate::diagonalLeftUp))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 3), Coordinate::diagonalLeftDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 4), Coordinate::diagonalLeftDown))
                .or(() -> diagonalWinner(Coordinate.of(Column.D.index(), 5), Coordinate::diagonalLeftDown))
                ;
    }

    private Optional<Color> diagonalWinner(Coordinate starting, Function<Coordinate, Coordinate> next) {
        Coordinate coordinate = starting;
        Optional<Color> winningColor = this.get(coordinate);

        if (winningColor.isEmpty()) {
            return Optional.empty();
        }

        for (int counter = 1; counter < 4; counter++) {
            coordinate = next.apply(coordinate);

            Optional<Color> color = this.get(coordinate);

            if (color.isEmpty()) {
                return Optional.empty();
            }

            if (color.get() != winningColor.get()) {
                return Optional.empty();
            }
        }

        return winningColor;
    }
}

class GridColumn {
    private final Stack<Color> stack = new Stack<>();

    void push(Color color) {
        stack.push(color);
    }

    Optional<Color> get(int index) {
        if (index < stack.size()) {
            return Optional.of(stack.get(index));
        }

        return Optional.empty();
    }

    Optional<Color> verticalWinner() {
        Color color = null;
        int counter = 0;

        for (Color currentColor : stack) {
            if (color == null) {
                color = currentColor;
                counter++;
            } else if (color == currentColor) {
                counter++;
            } else {
                counter = 1;
                color = currentColor;
            }

            if (counter == 4) {
                return Optional.of(color);
            }
        }

        return Optional.empty();
    }
}

class Moves implements Iterable<Move> {
    private final List<Move> moves;

    private Moves(List<Move> moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        return "{" + moves +
                '}';
    }

    @Override
    public Iterator<Move> iterator() {
        return moves.iterator();
    }

    static Moves of(List<String> piecesPositionList) {
        List<Move> parsedMoves = piecesPositionList.stream().map(Move::parse).collect(Collectors.toUnmodifiableList());
        return new Moves(parsedMoves);
    }
}

class Move {
    private static final Pattern MOVE_REGEXP = Pattern.compile("^(?<column>[A-G])_(?<color>(Red|Yellow))$");

    private final Column column;
    private final Color color;

    private Move(Column column, Color color) {
        this.column = column;
        this.color = color;
    }

    Column getColumn() {
        return column;
    }

    Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "{" + column +
                ", " + color +
                '}';
    }

    static Move parse(String moveAsString) {
        Matcher matcher = MOVE_REGEXP.matcher(moveAsString);

        if (matcher.matches()) {
            String column = matcher.group("column");
            String color = matcher.group("color");

            return new Move(Column.valueOf(column), Color.valueOf(color));
        }

        throw new RuntimeException("Cannot parse move: " + moveAsString);
    }
}

enum Column {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6);

    private final int index;

    Column(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}

enum Color {
    Red, Yellow
}

class Coordinate {
    private final int x;
    private final int y;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }

    Coordinate diagonalRightUp() {
        return new Coordinate(x + 1, y + 1);
    }

    Coordinate diagonalRightDown() {
        return new Coordinate(x + 1, y - 1);
    }

    Coordinate diagonalLeftUp() {
        return new Coordinate(x - 1, y + 1);
    }

    Coordinate diagonalLeftDown() {
        return new Coordinate(x - 1, y - 1);
    }

    static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }
}
