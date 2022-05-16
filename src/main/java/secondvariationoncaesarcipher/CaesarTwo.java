package secondvariationoncaesarcipher;

import java.util.List;
import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/55084d3898b323f0aa000546/train/java">Second Variation on Caesar Cipher</a>
 */
public class CaesarTwo {

    public static List<String> encodeStr(String string, int shift) {
        return Cleartext.of(string)
                .encryptBy(shift)
                .toChunks()
                .toList();
    }

    public static String decode(List<String> strings) {
        return Chunks.of(strings)
                .join()
                .decrypted()
                .payload();
    }
}

class Cleartext {
    private final String cleartext;

    private Cleartext(String cleartext) {
        this.cleartext = cleartext;
    }

    public String payload() {
        return cleartext.substring(Prefix.PREFIX_LENGTH);
    }

    public static Cleartext of(String text) {
        return new Cleartext(text);
    }

    public Ciphertext encryptBy(int shift) {
        Prefix prefix = Prefix.forEncryption(cleartext, shift);

        String encrypted = Caesars.withOffset(prefix.shift())
                .encrypt(cleartext);

        return Ciphertext.from(prefix.format() + encrypted);
    }
}

class Ciphertext {
    private static final int CHUNKS_COUNT = 5;

    private final String ciphertext;

    private Ciphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public Chunks toChunks() {
        int size = (int) Math.ceil((float) (ciphertext.length()) / CHUNKS_COUNT);

        return Chunks.ofSize(size)
                .from(ciphertext);
    }

    public static Ciphertext from(String ciphertext) {
        return new Ciphertext(ciphertext);
    }

    public Cleartext decrypted() {
        Prefix prefix = Prefix.forDecryption(ciphertext);
        String cleartext = Caesars.withOffset(prefix.shift())
                .decrypt(ciphertext);

        return Cleartext.of(cleartext);
    }
}

class Chunks {
    private final int size;
    private final List<String> chunks;

    private Chunks(int size, List<String> chunks) {
        this.size = size;
        this.chunks = chunks;
    }

    public Chunks from(String text) {
        List<String> list = createChunks(text, List.of());
        return new Chunks(size, list);
    }

    public Ciphertext join() {
        String ciphertext = String.join("", chunks);
        return Ciphertext.from(ciphertext);
    }

    public List<String> toList() {
        return chunks;
    }

    private List<String> createChunks(String text, List<String> chunks) {
        if (text.isEmpty()) {
            return chunks;
        }

        int chunkSize = Math.min(size, text.length());
        String chunk = text.substring(0, chunkSize);

        List<String> newChunks = Stream.concat(
                chunks.stream(),
                Stream.of(chunk)
        ).toList();

        return createChunks(text.substring(chunkSize), newChunks);
    }

    public static Chunks ofSize(int size) {
        return new Chunks(size, List.of());
    }

    public static Chunks of(List<String> chunks) {
        return new Chunks(chunks.size(), List.copyOf(chunks));
    }
}

class Prefix {
    public static final int PREFIX_LENGTH = 2;
    private final int shift;
    private final String raw;

    private Prefix(int shift, String raw) {
        this.shift = shift;
        this.raw = raw;
    }

    public int shift() {
        return shift;
    }

    public String format() {
        return raw;
    }

    public static Prefix forEncryption(String input, int shift) {
        char first = Character.toLowerCase(input.charAt(0));
        char second = Caesars.withOffset(shift).encrypt(first);
        char[] chars = new char[]{first, second};
        String raw = new String(chars);

        return new Prefix(shift, raw);
    }

    public static Prefix forDecryption(String input) {
        char first = input.charAt(0);
        char second = input.charAt(1);

        return new Prefix((second - first) % 26, input.substring(0, 2));
    }
}

class Caesars {
    private final int offset;

    private Caesars(int offset) {
        this.offset = offset;
    }

    public String encrypt(String cleartext) {
        return rotateBy(offset, cleartext);
    }

    public char encrypt(char letter) {
        return (char) rotateBy(offset, letter);
    }

    public String decrypt(String cipher) {
        return rotateBy(-offset, cipher);
    }

    private static String rotateBy(int offset, String cipher) {
        return cipher.chars().map(i -> rotateBy(offset, i)).collect(
                StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append
        ).toString();
    }

    private static int rotateBy(int offset, int value) {
        if (value >= 'a' && value <= 'z') {
            return (value - 'a' + offset) % 26 + 'a';
        }
        if (value >= 'A' && value <= 'Z') {
            return (value - 'A' + offset) % 26 + 'A';
        }

        return value;
    }

    public static Caesars withOffset(int offset) {
        return new Caesars(offset % 26);
    }
}
