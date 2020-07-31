package zaibacu.graphql.dto;

import java.io.Serializable;

public class QueryDTO implements Serializable {
    private String query;

    public QueryDTO(){

    }

    public QueryDTO(String query){
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
