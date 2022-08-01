package Test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLearningTest {
    @Test
    public void testHowGroupCountWork() throws Exception {
        String hayStack = "The needle shop sells needles";
        String regex = "(needle)";
        Matcher matcher = Pattern.compile(regex).matcher(hayStack);

        int matchFoundCount = 0;
        while (matcher.find()) {
            matchFoundCount++;
        }
        assertEquals(2,matchFoundCount);
    }

    @Test
    public void testFirstStartAndEnd() throws Exception {
        String hayStack = "The needle shop sells needles";
        String regex = "(needle)";

        Matcher matcher = Pattern.compile(regex).matcher(hayStack);

        assertTrue(matcher.find());
        assertEquals("Wrong start index of 1st match", 4, matcher.start());
        assertEquals("Wrong end index of 1st match", 10, matcher.end());

        assertTrue(matcher.find());
        assertEquals("Wrong start index of 2nd match", 22, matcher.start());
        assertEquals("Wrong end index of 2nd match", 28,matcher.end());

        assertFalse(matcher.find());

    }


}
