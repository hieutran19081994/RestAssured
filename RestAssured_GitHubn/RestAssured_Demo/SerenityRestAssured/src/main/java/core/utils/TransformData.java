package core.utils;

import core.configs.Configuration;
import net.serenitybdd.core.Serenity;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformData {
    public static String getDataAsString(String data) {
        return replaceVariablesInString(data);
    }

    public static String replaceVariablesInString(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        Pattern p1 = Pattern.compile("\\$\\{.*?}");
        Matcher m1 = p1.matcher(source);
        while (m1.find()) {
            String foundVar = m1.group();
            Object varObj = getDataAsObject(foundVar);
            if (varObj != null) {
                source = source.replace(foundVar, varObj.toString());
            }
        }
        return source;
    }

    public static Object getDataAsObject(String data) {
        Object result = null;
        data = data.trim();
        if (data.startsWith("${") && data.endsWith("}")) {
            String dataValue = data.substring(2, data.length() - 1).trim();
            if (System.getProperty(dataValue) != null) {
                return System.getProperty(dataValue);
            }
            result = Serenity.sessionVariableCalled(data);
            if (result == null) {
                result = Configuration.instance().getDataValue(dataValue);
            }
        } else {
            result = data;
        }
        return result;
    }

    public static String getValue(String strData) {
        return getDataAsString(strData);
    }

    public static List<Map<String, String>> getValue(List<Map<String, String>> dataLMap) {
        List<Map<String, String>> newDataLMap = new ArrayList<>();
        for (Map<String, String> map : dataLMap) {
            Map<String, String> newMap = getValue(map);
            newDataLMap.add(newMap);
        }
        return newDataLMap;
    }

    public static Map<String, String> getValue(Map<String, String> dataMap) {
        Map<String, String> newDataMap = new HashMap<>();
        for (String key : dataMap.keySet()) {
            String newValue = getValue(dataMap.get(key));
            if (System.getProperty(key) != null) {
                newValue = System.getProperty(key);
            }
            newDataMap.put(key, newValue);
        }
        return newDataMap;
    }
}
