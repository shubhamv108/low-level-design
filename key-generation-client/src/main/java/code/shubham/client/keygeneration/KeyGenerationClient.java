package code.shubham.client.keygeneration;

import code.shubham.commonclient.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

@Component
public class KeyGenerationClient {

    @Value("${host.keygeneration:127.0.0.1}")
    private String host = "127.0.0.1";

    @Value("${port.authorization: #{8091}}")
    private Integer port = 8084;
    private final String baseUrl;
    private static final String CONTEXT_PATH = "/keygeneration";

    private final HttpClientUtils httpClientUtils;

    @Autowired
    public KeyGenerationClient(final HttpClientUtils httpClientUtils) {
        this.httpClientUtils = httpClientUtils;
        this.baseUrl = "http://" + this.host + ":" + this.port + CONTEXT_PATH;
    }

    public String poll() {
        HttpRequest request = this.httpClientUtils.createRequest(
                "GET",
                this.baseUrl + "/",
                null,
                null);
        return this.httpClientUtils.sendRequest(request, String.class, "KeyGeneration");
    }
}
