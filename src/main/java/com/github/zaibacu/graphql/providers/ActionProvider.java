package com.github.zaibacu.graphql.providers;

import com.github.zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ActionProvider {
    ActionProvider withHttpService(HttpService service);
    ActionProvider withParameter(String name, Object param);
    ActionProvider withName(String name);
    ActionProvider withResultPrefix(String prefix);
    <T extends Serializable> Optional<List<T>> execute(String resultPath, Class<T> resultClass);
}
