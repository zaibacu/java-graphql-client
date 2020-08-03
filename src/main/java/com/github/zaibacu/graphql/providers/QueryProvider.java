package com.github.zaibacu.graphql.providers;

import com.github.zaibacu.graphql.dto.QueryDTO;
import com.github.zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.*;

public class QueryProvider implements ActionProvider{
    private HttpService service;
    private Map<String, Object> parameters = new HashMap<>();
    private String name;
    private String prefix = "data";

    @Override
    public QueryProvider withResultPrefix(String prefix){
        this.prefix = prefix;
        return this;
    }

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

    protected <T extends Serializable> String toQuery(String resultPath, Class<T> resultClass){
        StringBuilder request = new StringBuilder();
        if(name != null){
            request.append(name);
        }
        request.append(Utils.parametersString(parameters));
        request.append("{");
        request.append(Utils.nestResult(resultPath, Utils.resultString(resultClass)));
        request.append("}");

        return request.toString();
    }

    @Override
    public <T extends Serializable> Optional<List<T>> execute(String resultPath, Class<T> resultClass) {
        QueryDTO dto = new QueryDTO(this.toQuery(resultPath, resultClass));
        try {
            return service.post(Utils.toString(dto), String.format("%s.%s.%s", prefix, name, resultPath), resultClass);
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }
}
