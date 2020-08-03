package com.github.zaibacu.graphql.dto;

import java.util.HashMap;
import java.util.Map;

public class MutationDTO extends QueryDTO{
    private Map<String, Object> variables = new HashMap<>();

    public MutationDTO(){

    }

    public MutationDTO(String query){
        super(query);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
