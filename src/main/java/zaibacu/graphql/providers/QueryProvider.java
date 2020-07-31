package zaibacu.graphql.providers;

import zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class QueryProvider implements ActionProvider{
    private HttpService service;
    private Map<String, String> parameters = new HashMap<>();
    private String name;

    @Override
    public QueryProvider withHttpService(HttpService service){
        this.service = service;
        return this;
    }

    @Override
    public QueryProvider withParameter(String name, String param) {
        parameters.put(name, param);
        return this;
    }

    @Override
    public QueryProvider withName(String name) {
        this.name = name;
        return this;
    }

    private String parametersString(){
        if(parameters.isEmpty()){
            return "";
        }

        return "(" + parameters
                .entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(",")) + ")";
    }

    private<T> String resultString(Class<T> resultClass){
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

    protected <T extends Serializable> String toQuery(Class<T> resultClass){
        StringBuilder request = new StringBuilder(name);
        request.append(parametersString());
        request.append("{");
        request.append(resultString(resultClass));
        request.append("}");

        return request.toString();
    }

    @Override
    public <T extends Serializable> Optional<T> execute(Class<T> resultClass) {
        return service.post(this.toQuery(resultClass), resultClass);
    }
}
