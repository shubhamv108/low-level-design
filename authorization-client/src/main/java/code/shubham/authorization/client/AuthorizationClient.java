package code.shubham.authorization.client;

import code.shubam.authorization.models.RoleName;
import code.shubam.authorization.models.UserRole;
import code.shubham.common.exceptions.InternalServerError;
import code.shubham.common.exceptions.AuthorizationClientException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Component
public class AuthorizationClient {

    @Value("${host.authorization:127.0.0.1}")
    private String host = "127.0.0.1";

    @Value("${port.authorization: #{8084}}")
    private Integer port = 8084;
    private final String baseUrl;
    private static final String CONTEXT_PATH = "/authorization";

    private AuthorizationClient() {
        this.baseUrl = "http://" + this.host + ":" + this.port + CONTEXT_PATH;
    }

    public boolean isIndividualUser(final Integer userId) throws URISyntaxException, IOException, InterruptedException {
        Collection<UserRole> userRoles = this.getUserRoles(userId, null, RoleName.INDIVIDUAL_USER.name());
        return userRoles.size() == 1;
    }

    public boolean isAdmin(final Integer userId) throws URISyntaxException, IOException, InterruptedException {
        Collection<UserRole> userRoles = this.getUserRoles(userId, null, RoleName.ADMIN.name());
        return userRoles.size() == 1;
    }

    public Collection<UserRole> getUserRoles(final Integer userId,
                                             final Integer roleId,
                                             final String roleName)
            throws URISyntaxException, IOException, InterruptedException {
        StringBuilder queryParamsBuilder = new StringBuilder();
        if (roleId != null)
            queryParamsBuilder.append("roleId").append("=").append(roleId);
        if (roleName != null && roleName != "")
            if (queryParamsBuilder.length() > 0)
                queryParamsBuilder.append("&");
            queryParamsBuilder.append("roleName").append("=").append(roleName);
        HttpRequest request = this.createRequest(
                "GET", this.baseUrl + "/",
                queryParamsBuilder.toString(),
                null,
                "userId", String.valueOf(userId).intern());
        return (Collection<UserRole>) this.sendRequest(request, Collection.class);
    }

    private <R> R sendRequest(HttpRequest request, Class<R> clazz) {
        this.logRequest(request);
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder().build().
                    send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException connectException) {
            throw new AuthorizationClientException("Error connecting to Authorization service");
        } catch (SocketException socketException) {
            throw new AuthorizationClientException("Error getting response from Authorization service");
        } catch (IOException ioException) {
            throw new AuthorizationClientException("Error reading response from Authorization service");
        } catch (InterruptedException interruptedException) {
            throw new AuthorizationClientException("Error invoking Authorization Service");
        }
        this.logResponse(response);

        if (response.statusCode() == 404)
            throw new InternalServerError();
        if (response.statusCode() == 500)
            throw new InternalServerError();
        return new Gson().fromJson(response.body(), clazz);
    }

    private HttpRequest createRequest(String method,
                                      String uri,
                                      String queryParams,
                                      String body,
                                      String... headers)
            throws URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder().
                timeout(Duration.of(10, SECONDS)).
                uri(new URI(uri + "?" + queryParams)).
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
}
