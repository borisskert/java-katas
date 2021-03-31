package breakcamelcase;

/**
 * https://www.codewars.com/kata/5208f99aee097e6552000148/train/java
 */
class Solution {
    public static String camelCase(String input) {
        return input.replaceAll("[A-Z]", " $0");
    }
}
