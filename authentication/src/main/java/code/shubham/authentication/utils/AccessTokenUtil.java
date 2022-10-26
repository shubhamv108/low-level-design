package code.shubham.authentication.utils;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessTokenUtil {

    private final SecurityProperties securityProperties;

    @Autowired
    public AccessTokenUtil(final SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public String accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String headerAuth = headerAuth(request, response);
        if (StringUtils.isEmpty(headerAuth) || headerAuth.length() < 7) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }
        return headerAuth.substring(7);
    }

    private String headerAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String headerAuth = request.getHeader(this.securityProperties.getAccessToken());

        if (StringUtils.isEmpty(headerAuth) || "null".equals(headerAuth)
                || !headerAuth.startsWith(this.securityProperties.getTokenType())) {
            this.setErrorResponse(401, this.securityProperties.getFailureMessage(), response);
            return null;
        }
        return headerAuth;
    }

    public String getHeader(String headerKey, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (StringUtils.isEmpty(headerKey) || "null".equals(headerKey)
                || !headerKey.startsWith(this.securityProperties.getTokenType())) {
            this.setErrorResponse(401, this.securityProperties.getFailureMessage(), response);
            return null;
        }
        return headerKey;
    }

    public void setErrorResponse(int code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"status\":\"" + code + "\",\"message\":\" %s \"}", msg));
        response.setStatus(code);
    }
}
