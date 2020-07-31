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
    public ActionProvider withParameter(String name, String param) {
        return null;
    }

    @Override
    public ActionProvider withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public <T extends Serializable> Optional<T> execute(Class<T> resultClass) {
        return Optional.empty();
    }
}
