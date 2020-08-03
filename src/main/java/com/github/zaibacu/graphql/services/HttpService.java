package com.github.zaibacu.graphql.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface HttpService {
    <T extends Serializable> Optional<List<T>> post(String json, String resultPath, Class<T> klass);
}
