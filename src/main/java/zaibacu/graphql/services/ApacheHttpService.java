package zaibacu.graphql.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class ApacheHttpService implements HttpService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String url;
    private Map<String, String> headers;

    public ApacheHttpService(String url, Map<String, String> headers){
        this.url = url;
        this.headers = headers;
    }

    @Override
    public <T extends Serializable> Optional<T> post(String json, Class<T> klass) {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> entry: headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();

            return Optional.of(objectMapper.readValue(resultJson, klass));
        }
        catch(IOException ioException){
            return Optional.empty();
        }
    }
}
