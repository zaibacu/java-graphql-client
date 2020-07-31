package zaibacu.graphql.providers;

import zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.Optional;

public class MutationProvider implements ActionProvider{
    private HttpService service;
    private String name;

    @Override
    public MutationProvider withHttpService(HttpService service){
        this.service = service;
        return this;
    }

    @Override
    public MutationProvider withParameter(String name, Object param) {
        return this;
    }

    @Override
    public MutationProvider withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public <T extends Serializable> Optional<T> execute(String resultPath, Class<T> resultClass) {
        return Optional.empty();
    }
}
