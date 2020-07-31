package zaibacu.graphql.providers;

import zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.*;

public class QueryProvider implements ActionProvider{
    private HttpService service;
    private Map<String, Object> parameters = new HashMap<>();
    private String name;

    @Override
    public QueryProvider withHttpService(HttpService service){
        this.service = service;
        return this;
    }

    @Override
    public QueryProvider withParameter(String name, Object param) {
        parameters.put(name, param);
        return this;
    }

    @Override
    public QueryProvider withName(String name) {
        this.name = name;
        return this;
    }

    protected <T extends Serializable> String toQuery(Class<T> resultClass){
        StringBuilder request = new StringBuilder();
        if(name != null){
            request.append(name);
        }
        request.append(Utils.parametersString(parameters));
        request.append("{");
        request.append(Utils.resultString(resultClass));
        request.append("}");

        return request.toString();
    }

    @Override
    public <T extends Serializable> Optional<T> execute(Class<T> resultClass) {
        return service.post(this.toQuery(resultClass), resultClass);
    }
}
