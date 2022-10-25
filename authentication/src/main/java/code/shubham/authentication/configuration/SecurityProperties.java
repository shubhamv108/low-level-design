package code.shubham.authentication.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityProperties {

    private List<String> secret;
    private String authorizationUrl;
    private Integer sessionTimeout;
    private String sessionTimeoutType;
    private String accessToken;
    private String tokenType;
    private String utid;
    private String jti;
    private String failureMessage;
    private String badCredentialMessage;

}
