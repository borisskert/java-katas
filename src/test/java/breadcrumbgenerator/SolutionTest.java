package breadcrumbgenerator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class SolutionTest {
    @Test
    public void examplesTests() {
        String[] urls = new String[]{
                "mysite.com/pictures/holidays.html",
                "www.codewars.com/users/GiacomoSorbi?ref=CodeWars",
                "www.microsoft.com/docs/index.htm#top",
                "mysite.com/very-long-url-to-make-a-silly-yet-meaningful-example/example.asp",
                "www.very-long-site_name-to-make-a-silly-yet-meaningful-example.com/users/giacomo-sorbi"
        };

        String[] seps = new String[]{" : ", " / ", " * ", " > ", " + "};

        String[] anss = new String[]{
                "<a href=\"/\">HOME</a> : <a href=\"/pictures/\">PICTURES</a> : <span class=\"active\">HOLIDAYS</span>",
                "<a href=\"/\">HOME</a> / <a href=\"/users/\">USERS</a> / <span class=\"active\">GIACOMOSORBI</span>",
                "<a href=\"/\">HOME</a> * <span class=\"active\">DOCS</span>",
                "<a href=\"/\">HOME</a> > <a href=\"/very-long-url-to-make-a-silly-yet-meaningful-example/\">VLUMSYME</a> > <span class=\"active\">EXAMPLE</span>",
                "<a href=\"/\">HOME</a> + <a href=\"/users/\">USERS</a> + <span class=\"active\">GIACOMO SORBI</span>"
        };

        for (int i = 0; i < 5; i++) {
            System.out.println(" \nTest with : " + urls[i]);
            String actual = Solution.generate_bc(urls[i], seps[i]);
            if (!actual.equals(anss[i])) {
                System.out.println(String.format("Expected : %s", reformat(anss[i])));
                System.out.println(String.format("Actual :   %s", reformat(actual)));
            }
            assertEquals(anss[i], actual);
        }
    }

    String reformat(String s) {
        return s.replace("<", "<");
    }

    @Test
    public void shouldAcceptOneUrlParameter() throws Exception {
        String url = "www.codewars.com/users/GiacomoSorbi?ref=CodeWars";
        assertThat(Solution.generate_bc(url, "* ")).isEqualTo("<a href=\"/\">HOME</a> * <a href=\"/users/\">USERS</a> * <span class=\"active\">GIACOMOSORBI</span>");
    }

    @Test
    public void linkedInTest() throws Exception {
        String url = "https://www.linkedin.com/in/giacomosorbi";
        assertThat(Solution.generate_bc(url, "* ")).isEqualTo("<a href=\"/\">HOME</a> * <a href=\"/in/\">IN</a> * <span class=\"active\">GIACOMOSORBI</span>");
    }

    @Test
    public void shouldAcceptLinkedInIssuesUrl() throws Exception {
        String url = "linkedin.it/issues/of-immunity-of-transmutation-insider/";
        assertThat(Solution.generate_bc(url, "  :")).isEqualTo("<a href=\"/\">HOME</a> : <a href=\"/issues/\">ISSUES</a> : <span class=\"active\">ITI</span>");
    }

    @Test
    public void shouldAcceptMoreUrlParameters() throws Exception {
        String url = "twitter.de/app/a-meningitis-or-in-for-of-cauterization-by/?rank=recent_first&hide=sold";
        assertThat(Solution.generate_bc(url, "  *")).isEqualTo("<a href=\"/\">HOME</a> * <a href=\"/app/\">APP</a> * <span class=\"active\">MC</span>");
    }


    @Test
    public void shouldAcceptTwoParents() throws Exception {
        String url = "http://linkedin.it/games/kamehameha-transmutation-meningitis-transmutation/secret-page.htm";
        assertThat(Solution.generate_bc(url, "  *")).isEqualTo("<a href=\"/\">HOME</a> * <a href=\"/games/\">GAMES</a> * <a href=\"/games/kamehameha-transmutation-meningitis-transmutation/\">KTMT</a> * <span class=\"active\">SECRET PAGE</span>");
    }

    @Test
    public void shouldAcceptUrlWithAnchorAndUrlParamsMixed() throws Exception {
        String url = "https://www.linkedin.it/most-viewed/in-with-bed-bioengineering/#info?source=utm_pippi";
        assertThat(Solution.generate_bc(url, ">>> ")).isEqualTo("<a href=\"/\">HOME</a> >>> <a href=\"/most-viewed/\">MOST VIEWED</a> >>> <span class=\"active\">IN WITH BED BIOENGINEERING</span>");
    }

    @Test
    public void shouldShortcutAction() throws Exception {
        String url = "codewars.com/issues/eurasian-of-transmutation-research/";
        assertThat(Solution.generate_bc(url, " / ")).isEqualTo("<a href=\"/\">HOME</a> / <a href=\"/issues/\">ISSUES</a> / <span class=\"active\">ETR</span>");
    }

    @Test
    public void shouldAcceptActiveWithoutParents() throws Exception {
        String url = "github.com/kamehameha-bed-by-kamehameha-with/";
        assertThat(Solution.generate_bc(url, " / ")).isEqualTo("<a href=\"/\">HOME</a> / <span class=\"active\">KBK</span>");
    }

    @Test
    public void shouldAcceptMultipleParents() throws Exception {
        String url = "agcpartners.co.uk/images/biotechnology-immunity-kamehameha-eurasian/funny.asp#images?order=desc&filter=adult";
        assertThat(Solution.generate_bc(url, "; ")).isEqualTo("<a href=\"/\">HOME</a> ; <a href=\"/images/\">IMAGES</a> ; <a href=\"/images/biotechnology-immunity-kamehameha-eurasian/\">BIKE</a> ; <span class=\"active\">FUNNY</span>");
    }

    @Test
    public void shouldAcceptWithoutParents() throws Exception {
        String url = "agcpartners.co.uk/at-or-a-biotechnology-diplomatic/?sortBy=year";
        assertThat(Solution.generate_bc(url, " ; ")).isEqualTo("<a href=\"/\">HOME</a> ; <span class=\"active\">BD</span>");
    }

    @Test
    public void shouldAcceptHtmlPageWithTwoParents() throws Exception {
        String url = "codewars.com/most-downloaded/kamehameha/secret-page.html";
        assertThat(Solution.generate_bc(url, " # ")).isEqualTo("<a href=\"/\">HOME</a> # <a href=\"/most-downloaded/\">MOST DOWNLOADED</a> # <a href=\"/most-downloaded/kamehameha/\">KAMEHAMEHA</a> # <span class=\"active\">SECRET PAGE</span>");
    }

    @Test
    public void shouldAcceptActiveToBeFormatted() throws Exception {
        String url = "pippi.pi/issues/a-from-pippi-by-insider-the-of-bioengineering/";
        assertThat(Solution.generate_bc(url, " + ")).isEqualTo("<a href=\"/\">HOME</a> + <a href=\"/issues/\">ISSUES</a> + <span class=\"active\">PIB</span>");
    }

    @Test
    public void shouldAcceptHomeOnly() throws Exception {
        String url = "www.agcpartners.co.uk/";
        assertThat(Solution.generate_bc(url, " * ")).isEqualTo("<span class=\"active\">HOME</span>");
    }

    @Test
    public void shouldAcceptHomeOnlyWithIndex() throws Exception {
        String url = "https://www.agcpartners.co.uk/index.html";
        assertThat(Solution.generate_bc(url, " >>> ")).isEqualTo("<span class=\"active\">HOME</span>");
    }
}
