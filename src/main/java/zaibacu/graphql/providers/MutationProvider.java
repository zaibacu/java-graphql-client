package zaibacu.graphql.providers;

import zaibacu.graphql.dto.MutationDTO;
import zaibacu.graphql.services.HttpService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MutationProvider implements ActionProvider{
    private HttpService service;
    private String name;
    private Map<String, Object> parameters = new HashMap<>();
    private String prefix = "data";

    @Override
    public MutationProvider withResultPrefix(String prefix){
        this.prefix = prefix;
        return this;
    }

    @Override
    public MutationProvider withHttpService(HttpService service){
        this.service = service;
        return this;
    }

    @Override
    public MutationProvider withParameter(String name, Object param) {
        parameters.put(name, param);
        return this;
    }

    @Override
    public MutationProvider withName(String name) {
        this.name = name;
        return this;
    }

    protected <T extends Serializable> String toMutation(String resultPath, Class<T> resultClass){
        StringBuilder request = new StringBuilder();
        request.append("mutation{");

        request.append(name);
        request.append(Utils.parametersString(parameters));
        request.append("{");
        request.append(Utils.nestResult(resultPath, Utils.resultString(resultClass)));
        request.append("}");

        request.append("}");

        return request.toString();
    }

    @Override
    public <T extends Serializable> Optional<List<T>> execute(String resultPath, Class<T> resultClass) {
        MutationDTO dto = new MutationDTO(this.toMutation(resultPath, resultClass));
        try {
            return service.post(Utils.toString(dto), String.format("%s.%s.%s", prefix, name, resultPath), resultClass);
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }
}
