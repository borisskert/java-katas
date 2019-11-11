package shortestknightpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.codewars.com/kata/shortest-knight-path/train/java
 */
public class Chess {

    private static Map<Field, FieldTree> fieldMap = new HashMap<>();

    public static int knight(String start, String finish) {
        Field startField = Field.valueOf(start);
        Field finishField = Field.valueOf(finish);

        FieldTree fieldTree;
        if (fieldMap.containsKey(startField)) {
            fieldTree = fieldMap.get(startField);
        } else {
            fieldTree = new FieldTree(startField);
            fieldMap.put(startField, fieldTree);
        }

        return fieldTree.search(startField, finishField);
    }

    private static class FieldTree {
        private final Map<Field, Integer> steps = new HashMap<>();
        private final Map<Integer, List<Field>> farNeighbors = new HashMap<>();

        private FieldTree(Field start) {
            steps.put(start, 0);
        }

        public Integer search(Field start, Field finish) {
            if (steps.containsKey(finish)) {
                return steps.get(finish);
            }

            return search(new Field[]{start}, finish);
        }

        private Integer search(Field[] startFields, Field finish) {
            int steps = 1;

            while (steps < 7) {
                for (Field start : startFields) {
                    Field[] neighbors = start.getNeighbors();

                    for (Field neighbor : neighbors) {
                        if (neighbor == finish) {
                            return steps;
                        } else {
                            this.put(neighbor, steps);
                        }
                    }
                }

                startFields = this.getNeighbors(steps);
                steps++;
            }

            return null;
        }

        private void put(Field field, Integer steps) {
            if (!this.steps.containsKey(field)) {
                this.steps.put(field, steps);
                putNeighbor(field, steps);
            }
        }

        private void putNeighbor(Field field, Integer steps) {
            List<Field> routes = this.farNeighbors.computeIfAbsent(steps, k -> new ArrayList<>());
            routes.add(field);
        }

        private Field[] getNeighbors(int steps) {
            List<Field> fields = this.farNeighbors.get(steps);
            return fields.toArray(new Field[0]);
        }
    }

    private enum Field {
        a1, a2, a3, a4, a5, a6, a7, a8,
        b1, b2, b3, b4, b5, b6, b7, b8,
        c1, c2, c3, c4, c5, c6, c7, c8,
        d1, d2, d3, d4, d5, d6, d7, d8,
        e1, e2, e3, e4, e5, e6, e7, e8,
        f1, f2, f3, f4, f5, f6, f7, f8,
        g1, g2, g3, g4, g5, g6, g7, g8,
        h1, h2, h3, h4, h5, h6, h7, h8;

        static {
            a1.neighbors = new Field[]{b3, c2};
            a2.neighbors = new Field[]{b4, c3, c1};
            a3.neighbors = new Field[]{b5, c4, c2, b1};
            a4.neighbors = new Field[]{b6, c5, c3, b2};
            a5.neighbors = new Field[]{b7, c6, c4, b3};
            a6.neighbors = new Field[]{b8, c7, c5, b4};
            a7.neighbors = new Field[]{c8, c6, b5};
            a8.neighbors = new Field[]{c7, b6};

            b1.neighbors = new Field[]{a3, c3, d2};
            b2.neighbors = new Field[]{a4, c4, d3, d1};
            b3.neighbors = new Field[]{a5, c5, d4, d2, c1, a1};
            b4.neighbors = new Field[]{a6, c6, d5, d3, c2, a2};
            b5.neighbors = new Field[]{a7, c7, d6, d4, c3, a3};
            b6.neighbors = new Field[]{a8, c8, d7, d5, c4, a4};
            b7.neighbors = new Field[]{d8, d6, c5, a5};
            b8.neighbors = new Field[]{d7, c6, a6};

            c1.neighbors = new Field[]{a2, b3, d3, e2};
            c2.neighbors = new Field[]{a1, a3, b4, d4, e3, e1};
            c3.neighbors = new Field[]{b1, a2, a4, b5, d5, e4, e2, d1};
            c4.neighbors = new Field[]{b2, a3, a5, b6, d6, e5, e3, d2};
            c5.neighbors = new Field[]{b3, a4, a6, b7, d7, e6, e4, d3};
            c6.neighbors = new Field[]{b4, a5, a7, b8, d8, e7, e5, d4};
            c7.neighbors = new Field[]{b5, a6, a8, e8, e6, d5};
            c8.neighbors = new Field[]{b6, a7, e7, d6};

            d1.neighbors = new Field[]{b2, c3, e3, f2};
            d2.neighbors = new Field[]{b1, b3, c4, e4, f3, f1};
            d3.neighbors = new Field[]{c1, b2, b4, c5, e5, f4, f2, e1};
            d4.neighbors = new Field[]{c2, b3, b5, c6, e6, f5, f3, e2};
            d5.neighbors = new Field[]{c3, b4, b6, c7, e7, f6, f4, e3};
            d6.neighbors = new Field[]{c4, b5, b7, c8, e8, f7, f5, e4};
            d7.neighbors = new Field[]{c5, b6, b8, f8, f6, e5};
            d8.neighbors = new Field[]{c6, b7, f7, e6};

            e1.neighbors = new Field[]{c2, d3, f3, g2};
            e2.neighbors = new Field[]{c1, c3, d4, f4, g3, g1};
            e3.neighbors = new Field[]{d1, c2, c4, d5, f5, g4, g2, f1};
            e4.neighbors = new Field[]{d2, c3, c5, d6, f6, g5, g3, f2};
            e5.neighbors = new Field[]{d3, c4, c6, d7, f7, g6, g4, f3};
            e6.neighbors = new Field[]{d4, c5, c7, d8, f8, g7, g5, f4};
            e7.neighbors = new Field[]{d5, c6, c8, g8, g6, f5};
            e8.neighbors = new Field[]{d6, c7, g7, f6};

            f1.neighbors = new Field[]{d2, e3, g3, h2};
            f2.neighbors = new Field[]{d1, d3, e4, g4, h3, h1};
            f3.neighbors = new Field[]{e1, d2, d4, e5, g5, h4, h2, g1};
            f4.neighbors = new Field[]{e2, d3, d5, e6, g6, h5, h3, g2};
            f5.neighbors = new Field[]{e3, d4, d6, e7, g7, h6, h4, g3};
            f6.neighbors = new Field[]{e4, d5, d7, e8, g8, h7, h5, g4};
            f7.neighbors = new Field[]{e5, d6, d8, h8, h6, g5};
            f8.neighbors = new Field[]{e6, d7, h7, g6};

            g1.neighbors = new Field[]{e2, f3, h3};
            g2.neighbors = new Field[]{e1, e3, f4, h4};
            g3.neighbors = new Field[]{f1, e2, e4, f5, h5, h1};
            g4.neighbors = new Field[]{f2, e3, e5, f6, h6, h2};
            g5.neighbors = new Field[]{f3, e4, e6, f7, h7, h3};
            g6.neighbors = new Field[]{f4, e5, e7, f8, h8, h4};
            g7.neighbors = new Field[]{f5, e6, e8, h5};
            g8.neighbors = new Field[]{f6, e7, h6};

            h1.neighbors = new Field[]{f2, g3};
            h2.neighbors = new Field[]{f1, f3, g4};
            h3.neighbors = new Field[]{g1, f2, f4, g5};
            h4.neighbors = new Field[]{g2, f3, f5, g6};
            h5.neighbors = new Field[]{g3, f4, f6, g7};
            h6.neighbors = new Field[]{g4, f5, f7, g8};
            h7.neighbors = new Field[]{g5, f6, f8};
            h8.neighbors = new Field[]{g6, f7};
        }

        private Field[] neighbors;

        public Field[] getNeighbors() {
            return neighbors;
        }
    }
}
