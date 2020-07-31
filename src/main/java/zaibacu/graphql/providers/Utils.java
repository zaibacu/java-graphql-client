package zaibacu.graphql.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import zaibacu.graphql.exceptions.InvalidResultPath;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private static ObjectMapper objectMapper = new ObjectMapper();

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

    public static <T extends Serializable> List<T> parseJson(String json, String resultPath, Class<T> klass) throws JsonProcessingException, IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        String[] pathBuffer = resultPath.split("\\.");
        if(pathBuffer.length == 0){
            pathBuffer = new String[]{resultPath};
        }

        JsonNode current = rootNode;
        for(String path : pathBuffer){
            current = current.get(path);
            if(current == null){
                throw new InvalidResultPath();
            }
        }

        List<T> results = new ArrayList<>();
        if(current.isContainerNode()){
            for(final JsonNode node : current){
                results.add(objectMapper.treeToValue(node, klass));
            }
        }
        else{
            results.add(objectMapper.treeToValue(current, klass));
        }

        return results;
    }

    public static <T extends Serializable> String toString(T obj) throws JsonProcessingException{
        return objectMapper.writeValueAsString(obj);
    }

    public static String nestResult(String resultPath, String result){
        String[] pathBuff = resultPath.split("\\.");
        if(pathBuff.length == 0){
            pathBuff = new String[]{resultPath};
        }

        StringBuilder sb = new StringBuilder();
        for(String path : pathBuff){
            sb.append(path);
            sb.append("{");
        }

        sb.append(result);

        for(String path : pathBuff){
            sb.append("}");
        }

        return sb.toString();
    }
}
