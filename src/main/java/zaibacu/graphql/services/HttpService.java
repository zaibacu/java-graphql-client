package zaibacu.graphql.services;

import java.io.Serializable;
import java.util.Optional;

public interface HttpService {
    <T extends Serializable> Optional<T> post(String json, Class<T> klass);
}
