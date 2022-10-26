package code.shubham.authentication.configuration;

import code.shubham.authentication.enums.SessionTimeoutType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;
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

    public Date generateExpirationDate(Calendar currentTimeMillis) {
        switch(SessionTimeoutType.valueOf(sessionTimeoutType)) {
            case YEAR:
                currentTimeMillis.add(Calendar.YEAR, sessionTimeout);
                break;
            case MONTH:
                currentTimeMillis.add(Calendar.MONTH, sessionTimeout);
                break;
            case WEEK:
                currentTimeMillis.add(Calendar.WEEK_OF_MONTH, sessionTimeout);
                break;
            case DAY:
                currentTimeMillis.add(Calendar.DAY_OF_MONTH, sessionTimeout);
                break;
            case HOUR:
                currentTimeMillis.add(Calendar.HOUR, sessionTimeout);
                break;
            case MINUTE:
                currentTimeMillis.add(Calendar.MINUTE, sessionTimeout);
                break;
            case SECOND:
                currentTimeMillis.add(Calendar.SECOND, sessionTimeout);
                break;
        }
        return currentTimeMillis.getTime();
    }
}
