package sudokusolutionvalidator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/529bf0e9bdf7657179000008/train/java
 */
public class SudokuValidator {
    private static final int GRID_SIZE = 3;

    private static final Predicate<Fields> HAS_DUPLICATES = Fields::hasDuplicates;
    private static final Predicate<Fields> IS_NOT_FILLED = Fields::isNotFilled;

    public static boolean check(int[][] sudoku) {
        return concat(
                toRows(sudoku).stream(),
                toColumns(sudoku).stream(),
                toSubGrids(sudoku).stream()
        ).noneMatch(HAS_DUPLICATES.or(IS_NOT_FILLED));
    }

    @SafeVarargs
    private static <T> Stream<T> concat(Stream<T>... streams) {
        Stream<T> concatenated = Stream.empty();

        for (Stream<T> stream : streams) {
            concatenated = Stream.concat(concatenated, stream);
        }

        return concatenated;
    }

    private static List<Fields> toRows(int[][] sudoku) {
        List<Fields> rows = new ArrayList<>();

        for (int[] row : sudoku) {
            rows.add(Fields.of(row));
        }

        return rows;
    }

    private static List<Fields> toColumns(int[][] sudoku) {
        List<Fields> columns = new ArrayList<>();

        for (int columnIndex = 0; columnIndex < GRID_SIZE * GRID_SIZE; columnIndex++) {
            Fields column = Fields.empty();

            for (int[] row : sudoku) {
                column.add(row[columnIndex]);
            }

            columns.add(column);
        }

        return columns;
    }

    private static List<Fields> toSubGrids(int[][] sudoku) {
        List<Fields> subGrids = new ArrayList<>();

        for (int subGridRowIndex = 0; subGridRowIndex < GRID_SIZE; subGridRowIndex++) {
            for (int subGridColumnIndex = 0; subGridColumnIndex < GRID_SIZE; subGridColumnIndex++) {
                Fields subGrid = Fields.empty();

                for (int rowIndex = 0; rowIndex < GRID_SIZE; rowIndex++) {
                    for (int columnIndex = 0; columnIndex < GRID_SIZE; columnIndex++) {
                        subGrid.add(sudoku[subGridRowIndex * GRID_SIZE + rowIndex][subGridColumnIndex * GRID_SIZE + columnIndex]);
                    }
                }

                subGrids.add(subGrid);
            }
        }

        return subGrids;
    }

    private static class Fields {
        private final List<Integer> values;

        private Fields(List<Integer> values) {
            this.values = values;
        }

        Fields add(int value) {
            values.add(value);
            return this;
        }

        boolean hasDuplicates() {
            return values.stream()
                    .distinct()
                    .count() != values.size();
        }

        boolean isNotFilled() {
            return values.stream()
                    .anyMatch(i -> i == 0);
        }

        static Fields of(int[] values) {
            List<Integer> list = toList(values);

            return new Fields(list);
        }

        static Fields empty() {
            return new Fields(new ArrayList<>());
        }

        private static List<Integer> toList(int[] values) {
            List<Integer> list = new ArrayList<>(values.length);

            for (int i : values) {
                list.add(i);
            }

            return list;
        }
    }
}
