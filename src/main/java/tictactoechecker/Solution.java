package tictactoechecker;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/525caa5c1bf619d28c000335/train/java">Tic-Tac-Toe Checker</a>
 */
public class Solution {

    private static final int DRAW = 0;
    private static final int NOT_FINISHED_YET = -1;

    public static int isSolved(int[][] board) {
        TikTakToe tikTakToe = TikTakToe.of(board);

        return tikTakToe.winner()
                .map(Player::value)
                .orElse(tikTakToe.isFinished() ? DRAW : NOT_FINISHED_YET);
    }
}

enum Player {
    ONE(1), TWO(2);

    private final int value;

    Player(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Optional<Player> of(int value) {
        return Arrays.stream(Player.values())
                .filter(p -> p.value == value)
                .findFirst();
    }
}

class TikTakToe {
    private final int[][] board;

    private TikTakToe(int[][] board) {
        this.board = board;
    }

    public Optional<Player> winner() {
        return Stream.of(
                        topRow(),
                        middleRow(),
                        bottomRow(),
                        leftColumn(),
                        centerColumn(),
                        rightColumn(),
                        leftDiagonal(),
                        rightDiagonal()
                )
                .map(RowOrColumnOrDiagonal::winner)
                .flatMap(Optional::stream)
                .findFirst();
    }

    public boolean isFinished() {
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .allMatch(x -> x != 0);
    }

    private RowOrColumnOrDiagonal topRow() {
        List<Player> fields = Arrays.stream(board[0], 0, 3)
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal middleRow() {
        List<Player> fields = Arrays.stream(board[1], 0, 3)
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal bottomRow() {
        List<Player> fields = Arrays.stream(board[2], 0, 3)
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal leftColumn() {
        List<Player> fields = IntStream.range(0, 3)
                .map(x -> board[x][0])
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal centerColumn() {
        List<Player> fields = IntStream.range(0, 3)
                .map(x -> board[x][1])
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal rightColumn() {
        List<Player> fields = IntStream.range(0, 3)
                .map(x -> board[x][2])
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal leftDiagonal() {
        List<Player> fields = IntStream.range(0, 3)
                .map(x -> board[x][x])
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    private RowOrColumnOrDiagonal rightDiagonal() {
        List<Player> fields = IntStream.range(0, 3)
                .map(x -> board[x][2 - x])
                .mapToObj(Player::of)
                .flatMap(Optional::stream)
                .toList();

        return RowOrColumnOrDiagonal.of(fields);
    }

    public static TikTakToe of(int[][] board) {
        return new TikTakToe(board);
    }
}

class RowOrColumnOrDiagonal {
    private final List<Player> players;

    private RowOrColumnOrDiagonal(List<Player> players) {
        this.players = players;
    }

    public boolean isFinished() {
        return players.size() == 3 && players.stream()
                .distinct()
                .count() == 1;
    }

    public Optional<Player> winner() {
        if (!isFinished()) {
            return Optional.empty();
        }

        return Optional.of(players.get(0));
    }

    public static RowOrColumnOrDiagonal of(List<Player> players) {
        return new RowOrColumnOrDiagonal(players);
    }
}
