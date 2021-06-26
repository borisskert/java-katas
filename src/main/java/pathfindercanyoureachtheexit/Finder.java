package pathfindercanyoureachtheexit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/5765870e190b1472ec0022a2/train/java
 */
public class Finder {

    static boolean pathFinder(String mazeAsString) {
        Game game = new Game(Maze.of(mazeAsString));

        while (game.isSearching()) {
            game.nextTurn();
        }

        return game.hasFound();
    }
}

class Game {
    private final Coordinate goal;
    private final PathFinder goalFinder;
    private final PathFinder startFinder;

    Game(Maze maze) {
        this.goal = maze.goal();
        this.goalFinder = PathFinder.goalFinder(maze);
        this.startFinder = PathFinder.startFinder(maze);
    }

    boolean isSearching() {
        return goalFinder.hasNext()
                && startFinder.hasNext()

                && !goalFinder.hasInPlanToVisit(Coordinate.start())
                && !startFinder.hasInPlanToVisit(goal)

                && !goalFinder.hasReached(Coordinate.start())
                && !startFinder.hasReached(goal)
                ;
    }

    boolean hasFound() {
        return goalFinder.hasInPlanToVisit(Coordinate.start())
                || startFinder.hasInPlanToVisit(goal)

                || goalFinder.hasReached(Coordinate.start())
                || startFinder.hasReached(goal)

                || goalFinder.hasOverlapsWith(startFinder)
                ;
    }

    public void nextTurn() {
        if (goalFinder.hasNext()) {
            goalFinder.next();
        }

        if (startFinder.hasNext()) {
            startFinder.next();
        }
    }
}

class Maze {
    private final Map<Coordinate, Boolean> walls;
    private final Coordinate goal;

    private Maze(Map<Coordinate, Boolean> walls, Coordinate goal) {
        this.walls = walls;
        this.goal = goal;
    }

    boolean isWall(Coordinate coordinate) {
        return walls.getOrDefault(coordinate, true);
    }

    boolean isFree(Coordinate coordinate) {
        return !isWall(coordinate);
    }

    static Maze of(String maze) {
        String[] linesArray = maze.split("\n");

        Map<Coordinate, Boolean> walls = new HashMap<>();

        for (int y = 0; y < linesArray.length; y++) {
            char[] fields = linesArray[y].toCharArray();

            for (int x = 0; x < fields.length; x++) {
                Coordinate coordinate = Coordinate.of(x, y);
                walls.put(coordinate, fields[x] == 'W');
            }
        }

        return new Maze(walls, Coordinate.of(linesArray.length - 1));
    }

    public Coordinate goal() {
        return goal;
    }
}

class PathFinder implements Iterator<Void> {
    private final Set<Coordinate> visitedPositions = new HashSet<>();
    private final Queue<Coordinate> plannedPositions;

    private final Maze maze;

    PathFinder(Maze maze, Coordinate start, Comparator<Coordinate> priority) {
        this.maze = maze;
        this.plannedPositions = new PriorityQueue<>(priority);
        this.plannedPositions.add(start);
    }

    @Override
    public boolean hasNext() {
        return !plannedPositions.isEmpty();
    }

    boolean hasInPlanToVisit(Coordinate coordinate) {
        return this.plannedPositions.contains(coordinate);
    }

    @Override
    public Void next() {
        Coordinate currentPosition = plannedPositions.remove();

        Set<Coordinate> neighbors = currentPosition.neighbors()
                .stream()
                .filter(maze::isFree)
                .filter(neighbor -> !visitedPositions.contains(neighbor))
                .collect(Collectors.toUnmodifiableSet());

        plannedPositions.addAll(neighbors);
        visitedPositions.add(currentPosition);

        return null;
    }

    boolean hasReached(Coordinate coordinate) {
        return visitedPositions.contains(coordinate);
    }

    boolean hasOverlapsWith(PathFinder other) {
        return other.plannedPositions.stream().anyMatch(visitedPositions::contains)
                || other.visitedPositions.stream().anyMatch(plannedPositions::contains)

                || other.visitedPositions.stream().anyMatch(visitedPositions::contains)
                || other.plannedPositions.stream().anyMatch(plannedPositions::contains)
                ;
    }

    static PathFinder goalFinder(Maze maze) {
        return new PathFinder(maze, maze.goal(), Coordinate::compareTo);
    }

    static PathFinder startFinder(Maze maze) {
        return new PathFinder(maze, Coordinate.start(), Reverse.of(Coordinate::compareTo));
    }
}

class Coordinate implements Comparable<Coordinate> {
    private static final Coordinate START = new Coordinate(0, 0);

    private final int x;
    private final int y;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate start() {
        return START;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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

    static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    static Coordinate of(int xAndY) {
        return new Coordinate(xAndY, xAndY);
    }

    public Set<Coordinate> neighbors() {
        return Set.of(
                Coordinate.of(x - 1, y),
                Coordinate.of(x, y - 1),
                Coordinate.of(x, y + 1),
                Coordinate.of(x + 1, y)
        );
    }

    @Override
    public int compareTo(Coordinate o) {
        return Integer.compare(x + y, o.x + o.y);
    }
}

class Reverse<T> implements Comparator<T> {
    private final Comparator<T> comparator;

    private Reverse(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        return comparator.compare(o1, o2) * -1;
    }

    static <T> Comparator<T> of(Comparator<T> comparator) {
        return new Reverse<>(comparator);
    }
}
