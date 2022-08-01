package Test;

import org.junit.Test;
import src.VariableArguments;

import static org.junit.Assert.*;

public class VariableArgumentsTest {

    @Test
    public void noArgumentSize() throws Exception {
        VariableArguments variableArguments = new VariableArguments();
        assertEquals(0,variableArguments.numberOfArgument());
    }

    @Test
    public void multipleArguments() throws Exception {
        VariableArguments variableArguments = new VariableArguments();
        assertEquals(2,variableArguments.numberOfArgument("", ""));
    }
}
