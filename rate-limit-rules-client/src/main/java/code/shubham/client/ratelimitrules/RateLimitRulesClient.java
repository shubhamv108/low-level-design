package code.shubham.client.ratelimitrules;

import code.shubham.commonclient.HttpClientUtils;
import code.shubham.models.ratelimit.rules.GetCached;
import code.shubham.models.ratelimit.rules.GetRule;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.RateLimitRulesAPIVersion;
import code.shubham.models.ratelimit.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

@Component
public class RateLimitRulesClient {

    @Value("${host.ratelimitrules:127.0.0.1}")
    private String host = "127.0.0.1";

    @Value("${port.authorization: #{8098}}")
    private Integer port = 8098;

    @Value("${service.name:RateLimitRules}")
    private String serviceName = "RateLimitRules";
    private final String baseUrl;
    private static final String CONTEXT_PATH = "/ratelimitrules";

    private final HttpClientUtils httpClientUtils;

    @Autowired
    public RateLimitRulesClient(final HttpClientUtils httpClientUtils) {
        this.httpClientUtils = httpClientUtils;
        this.baseUrl = "http://" + this.host + ":" + this.port + CONTEXT_PATH;
    }

    public Rule getRule(GetRule.Request.QueryParams queryParams,
                        Integer userId) {
        return this.getRule(RateLimitRulesAPIVersion.V1, queryParams, userId);
    }

    public Rule getRule(RateLimitRulesAPIVersion apiVersion,
                        GetRule.Request.QueryParams queryParams,
                        Integer userId) {
        HttpRequest request = this.httpClientUtils.createRequest(
                "GET",
                this.baseUrl + apiVersion.getURI(),
                 queryParams.toString(),
                null);
        if (userId != null)
            request.headers().map().put("userId", Arrays.asList(String.valueOf(userId)));
        return this.httpClientUtils.sendRequest(request, Rule.class, this.serviceName);
    }

    public List<? extends IRule> getCached() {
        return this.getCached(RateLimitRulesAPIVersion.V1);
    }

    public List<? extends IRule> getCached(RateLimitRulesAPIVersion apiVersion) {
        HttpRequest request = this.httpClientUtils.createRequest(
                "GET",
                this.baseUrl + apiVersion.getURI() + "/cached",
                null,
                null);
        var responseBody = this.httpClientUtils.sendRequest(request, GetCached.Response.Body.class, this.serviceName);
        return responseBody.getRules();
    }
}
