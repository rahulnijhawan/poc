package Test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import src.Template;


public class TestTemplatePerformance {

    private Template template;
    @Before
    public void setUp() throws Exception {
        template = new Template(" hello ${one} ${two} fdfd fdf ${three} ${four} ${five} ${six} ${seven} fdfd fdfdf ${eight} ${nine} ${nine1}${nine2}${nine3}${nine4}${nine5}${nine6}${nine7}${nine8}${nine9}${nine10}${nine11}");
        template.set("one", "1dfdfdfdfdfdfdfdfdfdfdfdfd");
        template.set("two", "2frfkfkfkjdjkfdkjfdjkkdkfdkfdfkd");
        template.set("three", "2fdfffiiiriierieiiirieirieireieireireirei");
        template.set("four", "2rerererererererererererererrerererrrrrere");
        template.set("five", "2kjfrjji888328488888484388483843848348384843");
        template.set("six", "2kdkjfjfjfjsfjkdkjfjkdfjkdfjkdkjfkjdfkjdkjfdkj");
        template.set("seven", "iijfidfjidfijdfjidijfdij4444effdfdfdiifidfidiif");
        template.set("eight", "2jfjjkfdjkfjkfjkdkjfkjdkjfkjdkjfkjdkjfjkdjfkdkjfk");
        template.set("nine", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine1", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine2", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine3", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine4", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine5", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine6", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine7", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine8", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine9", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine10", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
        template.set("nine11", "2fdskjfjkdfjkdkjfdjkfdjkfkjdkjfkjdkjfdkjfkjdfkjdkjfkjdfkjdk");
    }

    @Test
    public void templateWith100WordsAnd20Variables() throws Exception {
        long expected = 200L;
        long time = System.currentTimeMillis();
        template.evaluate();
        time = System.currentTimeMillis() - time;
        assertTrue("Rendering the template took " + time
                        + "ms while the target was " + expected + "ms",
                time <= expected);
    }
}
