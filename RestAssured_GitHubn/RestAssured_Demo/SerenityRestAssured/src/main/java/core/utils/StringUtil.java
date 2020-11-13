package core.utils;

import java.util.Map;

/**
 *
 */
public class StringUtil {
    static final String PARAM_TEMPLATE = "\\{\\{%s\\}\\}";
    static final String PARAMS = "{{PARAM%d}}";

    /**
     * @param templateString
     * @param params
     * @return
     */
    public static String getStringWithParams(String templateString, String... params) {
        if (templateString == null || templateString.isEmpty() || params == null || params.length == 0) {
            return templateString;
        }

        for (int i = 0; i < params.length; i++) {
            String p = params[i];
            if (p != null && !p.isEmpty()) {
                templateString = templateString.replace(String.format(PARAMS, i + 1), p);
            }
        }
        return templateString;
    }

    /**
     * @param source
     * @param map
     * @return
     */
    public static String replaceWithMap(String source, Map<String, String> map) {
        if (source == null || source.isEmpty() || map == null || map.size() == 0) {
            return source;
        }

        //Replace variables
        final String[] inputCopy = {source};

        map.forEach((key, value) -> {
            if (value != null) {
                inputCopy[0] = inputCopy[0].replaceAll(String.format(PARAM_TEMPLATE, key), value);
            }
        });

        return inputCopy[0];
    }
}
