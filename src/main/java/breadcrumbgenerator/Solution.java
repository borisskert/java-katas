package breadcrumbgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.codewars.com/kata/563fbac924106b8bf7000046/train/java
 */
public class Solution {
    public static String generate_bc(String url, String separator) {
        TemplateStrategy strategy = Stream.of(
                new HomeOnlyIgnoreIndex(),
                new HomeOnly(),
                new OneForAllIgnoringIndex(),
                new OneForAll()
        )
                .filter(s -> s.test(url))
                .findFirst()
                .orElseThrow();

        BreadCrumb breadCrumb = BreadCrumb.builder()
                .separator(separator)
                .parents(strategy.parents());

        strategy.active().ifPresent(breadCrumb::active);

        return breadCrumb.build();
    }
}

interface TemplateStrategy extends Predicate<String> {
    Parents parents();

    Optional<Active> active();
}

class OneForAllIgnoringIndex implements TemplateStrategy {
    private static final Pattern URL_REGEXP = Pattern.compile("^(http(s)?://)?([a-z.\\-_]+)/(?<parents>[a-zA-Z\\-_]+(/[a-zA-Z\\-_]+)*/)?(?<active>[a-zA-Z\\-_]+)/index(.(html|htm|php|asp))?(/)?(#([a-zA-Z]+))?(\\?([a-zA-Z_]+)=([a-zA-Z_]+)(&[a-zA-Z_]+=[a-zA-Z_]+)*)?$");
    private Matcher matcher;

    @Override
    public Parents parents() {
        if (matcher == null) {
            throw new IllegalStateException();
        }

        String parsedParents = matcher.group("parents");

        return Optional.ofNullable(parsedParents)
                .map(Parents::parse)
                .orElse(Parents.empty());
    }

    @Override
    public Optional<Active> active() {
        if (matcher == null) {
            throw new IllegalStateException();
        }

        Active active = Active.of(matcher.group("active"));
        return Optional.of(active);
    }

    @Override
    public boolean test(String s) {
        matcher = URL_REGEXP.matcher(s);
        return matcher.matches();
    }
}

class OneForAll implements TemplateStrategy {
    private static final Pattern URL_REGEXP = Pattern.compile("^(http(s)?://)?([a-z.\\-_]+)/(?<parents>[a-zA-Z\\-_]+(/[a-zA-Z\\-_]+)*/)?(?<active>[a-zA-Z\\-_]+)(.(html|htm|php|asp))?(/)?(#([a-zA-Z]+))?(\\?([a-zA-Z_]+)=([a-zA-Z_]+)(&[a-zA-Z_]+=[a-zA-Z_]+)*)?$");
    private Matcher matcher;

    @Override
    public Parents parents() {
        if (matcher == null) {
            throw new IllegalStateException();
        }

        String parsedParents = matcher.group("parents");

        return Optional.ofNullable(parsedParents)
                .map(Parents::parse)
                .orElse(Parents.empty());
    }

    @Override
    public Optional<Active> active() {
        if (matcher == null) {
            throw new IllegalStateException();
        }

        Active active = Active.of(matcher.group("active"));
        return Optional.of(active);
    }

    @Override
    public boolean test(String s) {
        matcher = URL_REGEXP.matcher(s);
        return matcher.matches();
    }
}

class HomeOnlyIgnoreIndex implements TemplateStrategy {
    private static final Pattern HOME_ONLY = Pattern.compile("^(http(s)?://)?([a-z.\\-_]+)/index(\\.(html|htm|php|asp))?$");

    @Override
    public Parents parents() {
        return Parents.empty();
    }

    @Override
    public Optional<Active> active() {
        return Optional.empty();
    }

    @Override
    public boolean test(String s) {
        return HOME_ONLY.matcher(s).matches();
    }
}

class HomeOnly implements TemplateStrategy {
    private static final Pattern HOME_ONLY = Pattern.compile("^(http(s)?://)?([a-z.\\-_]+)/?$");

    @Override
    public Parents parents() {
        return Parents.empty();
    }

    @Override
    public Optional<Active> active() {
        return Optional.empty();
    }

    @Override
    public boolean test(String s) {
        return HOME_ONLY.matcher(s).matches();
    }
}

class BreadCrumb {
    private static final String HOME_ELEMENT = "<a href=\"/\">HOME</a>";
    private static final String HOME_ONLY_ELEMENT = "<span class=\"active\">HOME</span>";

    private Parents parents;
    private Active active;
    private String separator;

    private BreadCrumb() {
    }

    static BreadCrumb builder() {
        return new BreadCrumb();
    }

    BreadCrumb parents(Parents parents) {
        this.parents = parents;
        return this;
    }

    BreadCrumb active(Active active) {
        this.active = active;
        return this;
    }

    BreadCrumb separator(String separator) {
        this.separator = separator.trim();
        return this;
    }

    String build() {
        if (active == null && parents.isEmpty()) {
            return HOME_ONLY_ELEMENT;
        }

        StringJoiner separatorJoiner = new StringJoiner(" " + separator + " ");
        separatorJoiner.add(HOME_ELEMENT);

        parents.stream().map(Parent::toElement).forEach(separatorJoiner::add);

        if (active != null) {
            separatorJoiner.add(active.toElement());
        }

        return separatorJoiner.toString();
    }
}

class Active {
    private static final String ACTIVE_ELEMENT = "<span class=\"active\">{{active_title}}</span>";
    private static final String ACTIVE_TITLE_PLACEHOLDER = "\\{\\{active_title}}";

    private final FormattedTitle title;

    private Active(FormattedTitle title) {
        this.title = title;
    }

    String toElement() {
        return ACTIVE_ELEMENT.replaceAll(ACTIVE_TITLE_PLACEHOLDER, title.formatted());
    }

    static Active of(String title) {
        return new Active(FormattedTitle.of(title));
    }
}

class Parent {
    private static final String PARENT_ELEMENT = "<a href=\"/{{parent_href}}/\">{{parent_title}}</a>";
    private static final String PARENT_HREF_PLACEHOLDER = "\\{\\{parent_href}}";
    private static final String PARENT_TITLE_PLACEHOLDER = "\\{\\{parent_title}}";

    private final String href;
    private final FormattedTitle title;

    private Parent(String href, FormattedTitle title) {
        this.href = href;
        this.title = title;
    }

    String toElement() {
        return PARENT_ELEMENT
                .replaceAll(PARENT_HREF_PLACEHOLDER, href)
                .replaceAll(PARENT_TITLE_PLACEHOLDER, title.formatted());
    }

    static Parent of(String href, String title) {
        return new Parent(href, FormattedTitle.of(title));
    }
}

class Parents {
    private static final String SEPARATOR = "/";

    private final List<Parent> parents;

    private Parents(List<Parent> parents) {
        this.parents = parents;
    }

    static Parents parse(String parentsUrl) {
        String[] parts = parentsUrl.split("/");

        List<Parent> parents = new ArrayList<>();
        StringJoiner builder = new StringJoiner(SEPARATOR);

        for (String part : parts) {
            builder.add(part);
            parents.add(Parent.of(builder.toString(), part));
        }

        return new Parents(parents);
    }

    static Parents empty() {
        return new Parents(List.of());
    }

    public boolean isEmpty() {
        return parents.isEmpty();
    }

    public Stream<Parent> stream() {
        return parents.stream();
    }
}

class FormattedTitle {
    private static final Set<String> IGNORED_WORDS = Set.of("the", "of", "in", "from", "by", "with", "and", "or", "for", "to", "at", "a");
    private static final int MAX_LENGTH = 30;
    private static final String WORD_SEPARATOR = "-";

    private final String href;

    private FormattedTitle(String href) {
        this.href = href;
    }

    String formatted() {
        if (href.length() <= MAX_LENGTH) {
            return href.toUpperCase()
                    .replaceAll(WORD_SEPARATOR, " ");
        }

        return formatDashedHref();
    }

    private String formatDashedHref() {
        List<String> withoutIgnoredWords = withoutIgnoredWords();

        return withoutIgnoredWords.stream()
                .map(String::toUpperCase)
                .map(word -> word.charAt(0))
                .map(Object::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private List<String> withoutIgnoredWords() {
        String[] words = href.split(WORD_SEPARATOR);

        return Arrays.stream(words)
                .filter(word -> !IGNORED_WORDS.contains(word))
                .collect(Collectors.toUnmodifiableList());
    }

    static FormattedTitle of(String href) {
        return new FormattedTitle(href);
    }
}
