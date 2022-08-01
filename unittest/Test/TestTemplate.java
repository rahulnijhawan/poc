package Test;

/**
 * Triangulation
 */

import src.Template;
import src.MissingValueException;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestTemplate {

    private Template template;

    @Before
    public void setUp() throws Exception {
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }


    @Test
    public void multipleVariables() throws Exception {
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void unkownVariablesAreIgnored() throws Exception {

        template.set("unknownVariable", "value");
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void missingValueRaisesException() throws Exception {
        try {
            new Template("${foo}, ${abc}").evaluate();

            fail("evaluate() should throw an exception if "
                    + "a variable was left without a value!");
        } catch (MissingValueException expected) {
            assertEquals("No value for ${foo}, ${abc}", expected.getMessage());
        }
    }

    @Test(expected=MissingValueException.class)
    public void testMissingValueRaisesException() throws Exception {
        new Template("${foo}").evaluate();
    }


    private  void assertTemplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
    }

//    @Test
//    public void variablesGetProcessedJustOnce() throws Exception {
//        template.set("one", "${one}");
//        template.set("two", "${three}");
//        template.set("three", "${two}");
//        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
//    }

}
