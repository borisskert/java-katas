package befungeinterpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * https://www.codewars.com/kata/526c7b931666d07889000a3c/train/java
 */
public class BefungeInterpreter {
    private final Instructions instructions = new Instructions(this);

    private final SimpleStack stack = new SimpleStack();
    private final StringBuilder output = new StringBuilder();
    private final Position position = new Position(0, 0);

    private Direction direction = Direction.RIGHT;
    private boolean isRunning = true;
    private Program program;

    public String interpret(String code) {
        this.program = new Program(code);

        while (isRunning) {
            char token = token();
            instructions.getBy(token).operate(token);

            move();
        }

        return output.toString();
    }

    char token() {
        return this.program.get(position.getX(), position.getY());
    }

    void move() {
        position.move(direction);
    }

    void direction(Direction direction) {
        this.direction = direction;
    }

    int pop() {
        return stack.pop();
    }

    void push(int value) {
        stack.push(value);
    }

    void outputAscii(char value) {
        output.append(value);
    }

    void outputInteger(int value) {
        output.append(value);
    }

    char get(int x, int y) {
        return this.program.get(x, y);
    }

    void put(int x, int y, char value) {
        this.program.put(x, y, value);
    }

    void terminate() {
        this.isRunning = false;
    }
}

interface Instruction {
    void operate(char token);
}

class Program {
    private static final Map<String, char[][]> cache = new HashMap<>();
    private final char[][] program;

    Program(String code) {
        this.program = getCachedProgram(code);
    }

    private static char[][] getCachedProgram(String code) {
        if (cache.containsKey(code)) {
            return cache.get(code);
        }

        char[][] program = toProgram(code);
        cache.put(code, program);

        return program;
    }

    private static char[][] toProgram(String code) {
        final char[][] program = new char[25][80];

        int x = 0;
        int y = 0;

        for (int index = 0; index < code.length(); index++) {
            char codeChar = code.charAt(index);

            if (codeChar != '\n') {
                program[y][x] = codeChar;
                x++;
            } else {
                x = 0;
                y++;
            }
        }

        return program;
    }

    public char get(int x, int y) {
        return program[y][x];
    }

    public void put(int x, int y, char value) {
        program[y][x] = value;
    }
}

enum Direction {
    RIGHT, LEFT, UP, DOWN
}

class Position {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void move(Direction direction) {
        if (direction == Direction.RIGHT) {
            right();
        }
        if (direction == Direction.LEFT) {
            left();
        }
        if (direction == Direction.UP) {
            up();
        }
        if (direction == Direction.DOWN) {
            down();
        }
    }

    void right() {
        x++;
    }

    void left() {
        x--;
    }

    void up() {
        y--;
    }

    void down() {
        y++;
    }
}

class Instructions {
    public static final Instruction NO_OP = NoOp.INSTANCE;
    private final Instruction pushNumberOnStack = new PushNumberOnStack();
    private final Instruction addition = new Addition();
    private final Instruction subtraction = new Subtraction();
    private final Instruction multiplication = new Multiplication();
    private final Instruction not = new Not();
    private final Instruction greaterThan = new GreaterThan();
    private final Instruction moveRight = new MoveRight();
    private final Instruction moveDown = new MoveDown();
    private final Instruction moveLeft = new MoveLeft();
    private final Instruction moveUp = new MoveUp();
    private final Instruction moveRandom = new MoveRandom();
    private final Instruction duplicateValueOnStack = new DuplicateValueOnStack();
    private final Instruction swap = new Swap();
    private final Instruction popAndDiscard = new PopAndDiscard();
    private final Instruction printAsInteger = new PrintAsInteger();
    private final Instruction popAndOutputAsAscii = new PopAndOutputAsAscii();
    private final Instruction skip = new Skip();
    private final Instruction popValueAndMoveLeftOrRight = new PopValueAndMoveLeftOrRight();
    private final Instruction popValueAndMoveUpOrDown = new PopValueAndMoveUpOrDown();
    private final Instruction stringMode = new StringMode();
    private final Instruction put = new Put();
    private final Instruction get = new Get();
    private final Instruction terminate = new Terminate();

    private final BefungeInterpreter befunge;

    Instructions(BefungeInterpreter befunge) {
        this.befunge = befunge;
    }

    Instruction getBy(char token) {
        if (token == ' ') return NO_OP;
        if (token >= '0' && token <= '9') return pushNumberOnStack;
        if (token == '+') return addition;
        if (token == '-') return subtraction;
        if (token == '*') return multiplication;
        if (token == '!') return not;
        if (token == '`') return greaterThan;
        if (token == '>') return moveRight;
        if (token == 'v') return moveDown;
        if (token == '<') return moveLeft;
        if (token == '^') return moveUp;
        if (token == '?') return moveRandom;
        if (token == ':') return duplicateValueOnStack;
        if (token == '\\') return swap;
        if (token == '$') return popAndDiscard;
        if (token == '.') return printAsInteger;
        if (token == ',') return popAndOutputAsAscii;
        if (token == '#') return skip;
        if (token == '_') return popValueAndMoveLeftOrRight;
        if (token == '|') return popValueAndMoveUpOrDown;
        if (token == '"') return stringMode;
        if (token == 'p') return put;
        if (token == 'g') return get;
        if (token == '@') return terminate;

        throw new RuntimeException("No instruction found for token '" + token + "'");
    }

    private class PushNumberOnStack implements Instruction {
        private static final int ASCII_OFFSET = 48;

        @Override
        public void operate(char token) {
            befunge.push(token - ASCII_OFFSET);
        }
    }

    private class Addition implements Instruction {
        @Override
        public void operate(char token) {
            Integer a = befunge.pop();
            Integer b = befunge.pop();

            befunge.push(a + b);
        }
    }

    private class Subtraction implements Instruction {
        @Override
        public void operate(char token) {
            Integer a = befunge.pop();
            Integer b = befunge.pop();

            befunge.push(b - a);
        }
    }

    private class Multiplication implements Instruction {
        @Override
        public void operate(char token) {
            Integer a = befunge.pop();
            Integer b = befunge.pop();

            befunge.push(a * b);
        }
    }

    private static class NoOp implements Instruction {
        private static final Instruction INSTANCE = new NoOp();

        @Override
        public void operate(char token) {
            // Do nothing
        }
    }

    private class Not implements Instruction {
        @Override
        public void operate(char token) {
            int value = befunge.pop();

            if (value == 0) {
                befunge.push(1);
            } else {
                befunge.push(0);
            }
        }
    }

    private class GreaterThan implements Instruction {
        @Override
        public void operate(char token) {
            int a = befunge.pop();
            int b = befunge.pop();

            if (b > a) {
                befunge.push(1);
            } else {
                befunge.push(0);
            }
        }
    }

    private class MoveRight implements Instruction {
        @Override
        public void operate(char token) {
            befunge.direction(Direction.RIGHT);
        }
    }

    private class MoveLeft implements Instruction {
        @Override
        public void operate(char token) {
            befunge.direction(Direction.LEFT);
        }
    }

    private class MoveUp implements Instruction {
        @Override
        public void operate(char token) {
            befunge.direction(Direction.UP);
        }
    }

    private class MoveDown implements Instruction {
        @Override
        public void operate(char token) {
            befunge.direction(Direction.DOWN);
        }
    }

    private class MoveRandom implements Instruction {
        private final Random random = new Random();

        @Override
        public void operate(char token) {
            int randomNumber = random.nextInt() % 4;

            if (randomNumber == 0) {
                befunge.direction(Direction.RIGHT);
            } else if (randomNumber == 1) {
                befunge.direction(Direction.LEFT);
            } else if (randomNumber == 2) {
                befunge.direction(Direction.UP);
            } else if (randomNumber == 3) {
                befunge.direction(Direction.DOWN);
            }
        }
    }

    private class PopValueAndMoveLeftOrRight implements Instruction {
        @Override
        public void operate(char token) {
            Integer value = popValue();

            if (value.equals(0)) {
                befunge.direction(Direction.RIGHT);
            } else {
                befunge.direction(Direction.LEFT);
            }
        }

        private Integer popValue() {
            return befunge.pop();
        }
    }

    private class PopValueAndMoveUpOrDown implements Instruction {
        @Override
        public void operate(char token) {
            Integer value = befunge.pop();

            if (value.equals(0)) {
                befunge.direction(Direction.DOWN);
            } else {
                befunge.direction(Direction.UP);
            }
        }
    }

    private class StringMode implements Instruction {
        @Override
        public void operate(char token) {
            char nextToken = readNextToken();

            while (nextToken != '\"') {
                befunge.push(nextToken);
                nextToken = readNextToken();
            }
        }

        private char readNextToken() {
            befunge.move();
            return befunge.token();
        }
    }

    private class DuplicateValueOnStack implements Instruction {
        @Override
        public void operate(char token) {
            int value = befunge.pop();

            befunge.push(value);
            befunge.push(value);
        }
    }

    private class Swap implements Instruction {
        @Override
        public void operate(char token) {
            int a = befunge.pop();
            int b = befunge.pop();

            befunge.push(a);
            befunge.push(b);
        }
    }

    private class PopAndDiscard implements Instruction {
        @Override
        public void operate(char token) {
            befunge.pop();
        }
    }

    private class PrintAsInteger implements Instruction {
        @Override
        public void operate(char token) {
            int value = befunge.pop();
            befunge.outputInteger(value);
        }
    }

    private class PopAndOutputAsAscii implements Instruction {
        @Override
        public void operate(char token) {
            int value = befunge.pop();
            befunge.outputAscii((char) value);
        }
    }

    private class Skip implements Instruction {
        @Override
        public void operate(char token) {
            befunge.move();
        }
    }

    private class Put implements Instruction {
        @Override
        public void operate(char token) {
            int y = befunge.pop();
            int x = befunge.pop();
            int v = befunge.pop();

            befunge.put(x, y, (char) v);
        }
    }

    private class Get implements Instruction {
        @Override
        public void operate(char token) {
            int y = befunge.pop();
            int x = befunge.pop();

            char value = befunge.get(x, y);
            befunge.push(value);
        }
    }

    private class Terminate implements Instruction {
        @Override
        public void operate(char token) {
            befunge.terminate();
        }
    }
}

class SimpleStack {
    private int[] stack = new int[16];
    private int count = 0;

    int pop() {
        if (count == 0) {
            return 0;
        }

        int value = stack[count];

        count--;

        return value;
    }

    void push(int value) {
        if (stack.length <= ++count) {
            extendArray();
        }

        stack[count] = value;
    }

    private void extendArray() {
        int[] newStack = new int[count * 2];

        System.arraycopy(stack, 0, newStack, 0, stack.length);

        stack = newStack;
    }
}
