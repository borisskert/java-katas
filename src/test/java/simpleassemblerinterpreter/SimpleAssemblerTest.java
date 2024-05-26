package simpleassemblerinterpreter;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SimpleAssemblerTest {
    @Test
    public void simpleOperation() {
        String[] program = new String[]{"mov a 5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 5);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void twoSimpleOperation() {
        String[] program = new String[]{"mov a 5", "mov b a"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 5);
        out.put("b", 5);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void fourSimpleOperation() {
        String[] program = new String[]{"mov a 5", "mov b a", "dec a", "inc b"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 4);
        out.put("b", 6);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void simpleJumpByRegisterOperation() {
        String[] program = new String[]{"mov a 0", "jnz a 2", "mov a 5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 5);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void jumpToEnd() {
        String[] program = new String[]{"mov a 1", "jnz a 2", "mov a 5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 1);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void jumpToLast() {
        String[] program = new String[]{"mov a 0", "jnz a 1", "mov a 5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 5);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void simpleJumpBackByRegisterOperation() {
        String[] program = new String[]{"mov a 0", "jnz a -1", "mov a 5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 5);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void findBug() {
        String[] program = new String[]{"mov a 5", "inc a", "dec a", "dec a"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 4);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void simple_1() {
        String[] program = new String[]{"mov a 5", "inc a", "dec a", "dec a", "jnz a -1", "inc a"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 1);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void simple_2() {
        String[] program = new String[]{"mov a -10", "mov b a", "inc a", "dec b", "jnz a -2"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 0);
        out.put("b", -20);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void debuggingFailingTest1() {
        String[] program = new String[]{
                "mov a 1", "mov b 1", "mov c 0", "mov d 26", "jnz c 2", "jnz 1 5"
        };
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 1);
        out.put("b", 1);
        out.put("c", 0);
        out.put("d", 26);
        assertEquals(out, SimpleAssembler.interpret(program));
    }

    @Test
    public void failingTest1() {
        String[] program = new String[]{
                "mov a 1", "mov b 1", "mov c 0", "mov d 26", "jnz c 2", "jnz 1 5", "mov c 7", "inc d", "dec c",
                "jnz c -2", "mov c a", "inc a", "dec b", "jnz b -2", "mov b c", "dec d", "jnz d -6", "mov c 18",
                "mov d 11", "inc a", "dec d", "jnz d -2", "dec c", "jnz c -5"};
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 318009);
        out.put("b", 196418);
        out.put("c", 0);
        out.put("d", 0);
        assertEquals(out, SimpleAssembler.interpret(program));
    }


    @Test
    public void failingTest2() {
        String[] program = new String[]{
                "mov d 100", "dec d", "mov b d", "jnz b -2", "inc d", "mov a d", "jnz 5 10", "mov c a"
        };
        Map<String, Integer> out = new HashMap<>();
        out.put("a", 1);
        out.put("b", 0);
        out.put("d", 1);
        assertEquals(out, SimpleAssembler.interpret(program));
    }
}
