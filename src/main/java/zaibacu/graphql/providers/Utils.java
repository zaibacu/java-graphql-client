package zaibacu.graphql.providers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static<T> String resultString(Class<T> resultClass){
        List<Field> fields = new ArrayList<>();
        Class klass = resultClass;
        while (klass != Object.class) {
            fields.addAll(Arrays.asList(klass.getDeclaredFields()));
            klass = klass.getSuperclass();
        }

        return fields
                .stream()
                .filter(f -> !f.isSynthetic())
                .map(f -> {
                    Class<?> type = f.getType();
                    if(String.class.isAssignableFrom(type)){
                        return f.getName();
                    }
                    if(Serializable.class.isAssignableFrom(type)){
                        return f.getName() + "{" +  resultString(type) + "}";
                    }
                    else {
                        return f.getName();
                    }
                })
                .collect(Collectors.joining(" "));
    }

    private static String renderParameterValue(Object param){
        if(param instanceof String){
            return String.format("\"%s\"", param);
        }
        else{
            return param.toString();
        }
    }

    public static String parametersString(Map<String, Object> parameters){
        if(parameters.isEmpty()){
            return "";
        }

        return "(" + parameters
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                .map(e -> e.getKey() + ": " + renderParameterValue(e.getValue()))
                .collect(Collectors.joining(", ")) + ")";
    }
}
