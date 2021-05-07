package mostfrequentlyusedwords;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TopWords {

    private static final Comparator<Map.Entry<String, Integer>> descendingOrder = (e1, e2) -> e2.getValue().compareTo(e1.getValue());
    private static final Pattern wordsPattern = Pattern.compile("([A-Za-z]+('([A-Za-z])*)*)", Pattern.CASE_INSENSITIVE);

    public static List<String> top3(String s) {
        Map<String, Integer> collectedWords = countWords(s);

        return collectedWords.entrySet()
                .stream()
                .sorted(descendingOrder)
                .map(Map.Entry::getKey)
                .limit(3)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Map<String, Integer> countWords(String text) {
        Map<String, Integer> collectedWords = new HashMap<>();
        Matcher matcher = wordsPattern.matcher(text);

        while (matcher.find()) {
            if (matcher.groupCount() > 0) {
                String word = matcher.group(1).toLowerCase();

                if (collectedWords.containsKey(word)) {
                    collectedWords.put(word, collectedWords.get(word) + 1);
                } else {
                    collectedWords.put(word, 1);
                }
            }
        }

        return collectedWords;
    }
}
