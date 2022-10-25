package code.shubham.commonclient;

import code.shubham.common.exceptions.InternalServerError;
import code.shubham.common.exceptions.InvalidRequestException;
import code.shubham.common.exceptions.ServiceInvocationClientException;
import code.shubham.common.models.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Component
public class HttpClientUtils {
    public <R> R sendRequest(HttpRequest request, Class<R> clazz, String serviceName) {
        this.logRequest(request);
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder().build().
                    send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException connectException) {
            throw new ServiceInvocationClientException(String.format("Error connecting to %s service", serviceName));
        } catch (SocketException socketException) {
            throw new ServiceInvocationClientException(String.format("Error getting response from %s service", serviceName));
        } catch (IOException ioException) {
            throw new ServiceInvocationClientException(String.format("Error reading response from %s service", serviceName));
        } catch (InterruptedException interruptedException) {
            throw new ServiceInvocationClientException(String.format("Error invoking %s Service", serviceName));
        }
        this.logResponse(response);

        if (response.statusCode() == 404)
            throw new InternalServerError();
        if (response.statusCode() == 500)
            throw new InternalServerError();
        ServiceResponse serviceResponse = JsonUtils.deserialize(response.body(), ServiceResponse.class);
        if (serviceResponse == null)
            throw new ServiceInvocationClientException("Noresponse");
        if (serviceResponse.getStatusCode() > 399 && serviceResponse.getStatusCode() < 500)
            new InvalidRequestException((Map<String, Collection<String>>) serviceResponse.getError());
        if (serviceResponse.getStatusCode() > 500)
            new InternalServerError(JsonUtils.convert(serviceResponse.getError(), Map.class));
        return JsonUtils.convert(serviceResponse.getData(), clazz);
    }

    public HttpRequest createRequest(String method,
                                      String uriPath,
                                      String queryParams,
                                      String body,
                                      String... headers) {

        if (queryParams != null && "".equals(queryParams))
            uriPath = uriPath + "?" + queryParams;
        URI uri = this.getURI(uriPath);
        HttpRequest.Builder builder = HttpRequest.newBuilder().
                timeout(Duration.of(10, SECONDS)).
                uri(uri).
                headers(headers);
        if (body != null)
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        return builder.build();
    }

    private void logRequest(HttpRequest request) {
        log.info("Invoking {Url: {}, Headers: {}, Body: {}}",
                request.uri().toString(),
                request.headers().toString(),
                request.bodyPublisher().map(HttpRequest.BodyPublisher::toString).orElse(null));
    }

    private void logResponse(HttpResponse response) {
        log.info("Response {Uri: {}, StatusCode: {}, Headers: {}, Body: {}}",
                response.uri(),
                response.statusCode(),
                response.headers(),
                response.body());
    }

    private URI getURI(String uriPath) {
        URI uri = null;
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            throw new ServiceInvocationClientException(String.format("Invalid uri: %s", uriPath));
        }
        return uri;
    }
}
