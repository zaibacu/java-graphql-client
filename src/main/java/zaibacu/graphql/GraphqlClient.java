package zaibacu.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import zaibacu.graphql.providers.MutationProvider;
import zaibacu.graphql.providers.QueryProvider;
import zaibacu.graphql.services.ApacheHttpService;
import zaibacu.graphql.services.HttpService;

import java.util.HashMap;
import java.util.Map;

public class GraphqlClient {
    private HttpService httpService;

    private GraphqlClient(String url, Map<String, String> headers){
        this.httpService = new ApacheHttpService(url, headers);
    }

    public QueryProvider query(String name){
        QueryProvider builder = new QueryProvider()
                .withName(name)
                .withHttpService(httpService);

        return builder;
    }

    public MutationProvider mutate(String name){
        MutationProvider builder = new MutationProvider()
                .withName(name)
                .withHttpService(httpService);

        return builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String url;
        private Map<String, String> headers = new HashMap<>();

        public Builder withUrl(String url){
            this.url = url;
            return this;
        }

        public Builder withHeader(String key, String value){
            this.headers.put(key, value);
            return this;
        }

        public Builder withAuthToken(String value){
            this.headers.put("Authorization", "Token " + value);
            return this;
        }

        public GraphqlClient build(){
            GraphqlClient client = new GraphqlClient(url, headers);
            return client;
        }
    }
}
