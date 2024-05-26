package simpleassemblerinterpreter;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <a href="https://www.codewars.com/kata/58e24788e24ddee28e000053/train/java">Simple assembler interpreter</a>
 */
public class SimpleAssembler {
    public static Map<String, Integer> interpret(String[] program) {
        Machine machine = Machine.load(program);
        machine.execute();

        return machine.exposeRegisters();
    }
}

class Machine {
    private final Registers registers = Registers.empty();
    private final Program program;
    private int programCounter = 0;

    private Machine(Program program) {
        this.program = program;
    }

    public void jump(int i) {
        programCounter += i;
    }

    public void jump(String label) {
        int x = registers.read(label);
        jump(x);
    }

    public void increment(String x) {
        registers.increment(x);
    }

    public void decrement(String x) {
        registers.decrement(x);
    }

    public void move(String x, int y) {
        registers.move(x, y);
    }

    public void move(String x, String y) {
        registers.move(x, y);
    }

    public boolean isZero(String x) {
        return registers.isZero(x);
    }

    public void execute() {
        while (programCounter >= 0 && programCounter < program.size()) {
            Operation operation = program.get(programCounter);

            if (operation.execute(this)) {
                programCounter++;
            }
        }
    }

    public static Machine load(String[] program) {
        Program loadedProgram = Program.fromArray(program);
        return new Machine(loadedProgram);
    }

    public Map<String, Integer> exposeRegisters() {
        return registers.asMap();
    }
}

class Registers {
    private final Map<String, Integer> registers;

    private Registers() {
        this.registers = new HashMap<>();
    }

    public void increment(String x) {
        Integer value = registers.getOrDefault(x, 0);
        registers.put(x, value + 1);
    }

    public void decrement(String x) {
        Integer value = registers.getOrDefault(x, 0);
        registers.put(x, value - 1);
    }

    public void move(String x, int y) {
        registers.put(x, y);
    }

    public void move(String x, String y) {
        Integer value = registers.getOrDefault(y, 0);
        registers.put(x, value);
    }

    public boolean isZero(String x) {
        Integer value = registers.getOrDefault(x, 0);
        return value == 0;
    }

    public int read(String x) {
        return registers.getOrDefault(x, 0);
    }

    public Map<String, Integer> asMap() {
        return new HashMap<>(registers);
    }

    public static Registers empty() {
        return new Registers();
    }
}

interface Operation {
    boolean execute(Machine machine);
}

class Program {
    private final List<Operation> operations;

    private static final List<Function<String, Optional<Operation>>> PARSERS = Arrays.asList(
            MovR::tryParse,
            MovC::tryParse,
            Inc::tryParse,
            Dec::tryParse,
            JnzRC::tryParse,
            JnzCR::tryParse,
            JnzRR::tryParse,
            JnzCC::tryParse
    );

    private Program(List<Operation> operations) {
        this.operations = operations;
    }

    public Operation get(int i) {
        return operations.get(i);
    }

    public int size() {
        return operations.size();
    }

    @Override
    public String toString() {
        return operations.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static Program fromArray(String[] program) {
        List<Operation> operations = Arrays.stream(program)
                .map(Program::parseOperation)
                .toList();

        return new Program(operations);
    }

    private static Operation parseOperation(String operation) {
        return PARSERS.stream()
                .map(parser -> parser.apply(operation))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation: " + operation));
    }
}

/**
 * Represents the operation `mov x y` where `x` and `y` are registers.
 */
class MovR implements Operation {
    private static final Pattern MOV_FROM_REGISTER_PATTERN = Pattern.compile("mov ([a-zA-Z]+) ([a-zA-Z]+)");

    private final String x;
    private final String y;

    private MovR(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        machine.move(x, y);
        return true;
    }

    @Override
    public String toString() {
        return "\"mov " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = MOV_FROM_REGISTER_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);
            String y = matcher.group(2);

            return Optional.of(new MovR(x, y));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `mov x y` where `x` is a register and `y` is a constant.
 */
class MovC implements Operation {
    private static final Pattern MOV_FROM_CONSTANT_PATTERN = Pattern.compile("mov ([a-zA-Z]+) (-?\\d+)");

    private final String x;
    private final int y;

    private MovC(String x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        machine.move(x, y);
        return true;
    }

    @Override
    public String toString() {
        return "\"mov " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = MOV_FROM_CONSTANT_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);
            int y = Integer.parseInt(matcher.group(2));

            return Optional.of(new MovC(x, y));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `inc x` where `x` is a register.
 */
class Inc implements Operation {
    private static final Pattern INC_PATTERN = Pattern.compile("inc ([a-zA-Z]+)");

    private final String x;

    private Inc(String x) {
        this.x = x;
    }

    @Override
    public boolean execute(Machine machine) {
        machine.increment(x);
        return true;
    }

    @Override
    public String toString() {
        return "\"inc " + x + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = INC_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);

            return Optional.of(new Inc(x));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `dec x` where `x` is a register.
 */
class Dec implements Operation {
    private static final Pattern DEC_PATTERN = Pattern.compile("dec (\\w+)");
    private final String x;

    private Dec(String x) {
        this.x = x;
    }

    @Override
    public boolean execute(Machine machine) {
        machine.decrement(x);
        return true;
    }

    @Override
    public String toString() {
        return "\"dec " + x + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = DEC_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);

            return Optional.of(new Dec(x));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `jnz x y` where `x` is a register and `y` is a constant.
 */
class JnzRC implements Operation {
    private static final Pattern JUMP_PATTERN = Pattern.compile("jnz ([a-zA-Z]+) (-?\\d+)");

    private final String x;
    private final int y;

    private JnzRC(String x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        if (machine.isZero(x)) {
            return true;
        }

        machine.jump(y);
        return false;
    }

    @Override
    public String toString() {
        return "\"jnz " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = JUMP_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);
            int y = Integer.parseInt(matcher.group(2));

            return Optional.of(new JnzRC(x, y));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `jnz x y` where `x` is a constant and `y` is a label.
 */
class JnzCR implements Operation {
    private static final Pattern JUMP_PATTERN = Pattern.compile("jnz (-?\\d+) ([a-zA-Z]+)");

    private final int x;
    private final String y;

    private JnzCR(int x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        if (x != 0) {
            machine.jump(y);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "\"jnz " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = JUMP_PATTERN.matcher(operation);

        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            String y = matcher.group(2);

            return Optional.of(new JnzCR(x, y));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `jnz x y` where `x` and `y` are registers.
 */
class JnzRR implements Operation {
    private static final Pattern JUMP_PATTERN = Pattern.compile("jnz ([a-zA-Z]+) ([a-zA-Z]+)");

    private final String x;
    private final String y;

    private JnzRR(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        if (machine.isZero(x)) {
            return true;
        }

        machine.jump(y);
        return false;
    }

    @Override
    public String toString() {
        return "\"jnz " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = JUMP_PATTERN.matcher(operation);

        if (matcher.matches()) {
            String x = matcher.group(1);
            String y = matcher.group(2);

            return Optional.of(new JnzRR(x, y));
        }

        return Optional.empty();
    }
}

/**
 * Represents the operation `jnz x y` where `x` is a constant and `y` is a constant.
 */
class JnzCC implements Operation {
    private static final Pattern JUMP_PATTERN = Pattern.compile("jnz (-?\\d+) (-?\\d+)");

    private final int x;
    private final int y;

    private JnzCC(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(Machine machine) {
        if (x != 0) {
            machine.jump(y);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "\"jnz " + x + " " + y + "\"";
    }

    public static Optional<Operation> tryParse(String operation) {
        Matcher matcher = JUMP_PATTERN.matcher(operation);

        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));

            return Optional.of(new JnzCC(x, y));
        }

        return Optional.empty();
    }
}
