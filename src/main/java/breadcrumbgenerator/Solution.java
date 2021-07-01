package breadcrumbgenerator;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * https://www.codewars.com/kata/563fbac924106b8bf7000046/train/java
 */
public class Solution {
    private static final String TEMPLATE = "<a href=\"/\">HOME</a> {{separator}} <a href=\"/pictures/\">PICTURES</a> {{separator}} <span class=\"active\">HOLIDAYS</span>";

    public static String generate_bc(String url, String separator) {
        return TEMPLATE.replaceAll("\\{\\{separator}}", separator.trim());
    }
}

interface TemplateStrategy extends Predicate<String> {
    String template(String url, String separator);
}

class SimpleHtmlPage implements TemplateStrategy {
    private static final Pattern REGEXP = Pattern.compile("^([a-z.]+)/(?<parent>[a-z]+)/(?<active>[a-z]+).html$");
    private static final String TEMPLATE = "<a href=\"/\">HOME</a> {{separator}} <a href=\"/{{parent_href}}/\">{{parent_title}}</a> {{separator}} <span class=\"active\">{{active_title}}</span>";

    @Override
    public String template(String url, String separator) {
        return null;
    }

    @Override
    public boolean test(String s) {
        return false;
    }
}