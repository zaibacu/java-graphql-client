package zaibacu.graphql.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import zaibacu.graphql.providers.Utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApacheHttpService implements HttpService {
    private String url;
    private Map<String, String> headers;

    public ApacheHttpService(String url, Map<String, String> headers){
        this.url = url;
        this.headers = headers;
    }

    @Override
    public <T extends Serializable> Optional<List<T>> post(String json, String resultPath, Class<T> klass) {
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(json, "UTF-8"));
        }
        catch(Exception ex){
            return Optional.empty();
        }

        for (Map.Entry<String, String> entry: headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        httpPost.addHeader("Content-Type", "application/json");

        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
            return Optional.of(Utils.parseJson(resultJson, resultPath, klass));
        }
        catch(IOException ioException){
            return Optional.empty();
        }
    }
}
