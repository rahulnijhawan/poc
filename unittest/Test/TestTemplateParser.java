package Test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.TemplateParser;

public class TestTemplateParser {
    @Test
    public void emptyTemplateRendersAsEmptyString() throws Exception {

        List<String> segments = parse("");
        assertSegments(segments, "");
//        assertEquals("Number of segments", 1, segments.size());
//        assertEquals("", segments.get(0));

    }

    @Test
    public void templateWithOnlyPlainText()  throws Exception {
        List<String> segments = parse("plain text");
        assertSegments(segments, "plain text");
//        assertEquals("Number of segments", 1, segments.size());
//        assertEquals("plain text", segments.get(0));
    }

    private void assertSegments(List<? extends Object> actual,
                                Object... expected) {
        assertEquals("Number of segments doesn't match.",

                expected.length, actual.size());

        List a = Arrays.asList(expected);
//        for ( <> al : a) {
//            System.out.println(al.toString());
//        }

        assertEquals(Arrays.asList(expected), actual);
    }

    private List<String> parse(String template) {
        return new TemplateParser().parse(template);
    }


}
