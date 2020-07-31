package zaibacu.graphql.providers;

import zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.Optional;

public interface ActionProvider {
    ActionProvider withHttpService(HttpService service);
    ActionProvider withParameter(String name, String param);
    ActionProvider withName(String name);
    <T extends Serializable> Optional<T> execute(Class<T> resultClass);
}
