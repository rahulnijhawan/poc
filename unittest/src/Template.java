package src;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Template {
    private Map<String, String> variables;
    private String templateText;

    public Template(String templateText) {

        this.templateText = templateText;
        this.variables = new HashMap<>();
    }
    public void set(String variable, String value) {
        this.variables.put(variable, value);
    }
    public String evaluate() {
        String result = replaceVariables();
        checkForMissingValues(result);
        return result;
    }

    private String replaceVariables() {
        String result = templateText;
        for (Map.Entry<String, String> entry:
                variables.entrySet()) {

            String regex = "\\$\\{"+entry.getKey()+"\\}";
            result = result.replaceAll(regex,entry.getValue());
        }

        return result;
    }

    private void checkForMissingValues(String result) {
        Matcher m = Pattern.compile(".*\\$\\{.+\\}.*").matcher(result);
        if (m.find()) {
            throw new MissingValueException("No value for "+m.group());
        }

    }
}