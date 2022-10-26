package code.shubham.authentication.service.strategies;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.utils.JWTUtils;
import code.shubham.common.utils.StringUtils;
import code.shubham.models.authentication.Login;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JWTAccessTokenStrategy implements AccessTokenStrategy {

    private final JWTUtils jwtUtils;
    private final SecurityProperties securityProperties;

    @Autowired
    public JWTAccessTokenStrategy(final JWTUtils jwtUtils,
                                  final SecurityProperties securityProperties) {
        this.jwtUtils = jwtUtils;
        this.securityProperties = securityProperties;
    }

    @Override
    public Login.Response prepareLoginResponse(String userName, Date expirationDate) {
        int generatedRandomNumber = this.jwtUtils.generateRandomNumber();
        String generatedUTID      = this.jwtUtils.prepareUTID(generatedRandomNumber);
        String jti                = this.jwtUtils.generateJti();
        String secret             = this.securityProperties.getSecret().get(generatedRandomNumber);
//        Role role = user.getRoles().stream().findFirst().get();
        String accessToken        = this.jwtUtils.generateAccessToken(
                userName, null, generatedUTID, jti, expirationDate, secret, null);

        return Login.Response.builder().
                username(userName).
                accessToken(accessToken).
                jti(jti).
                utid(generatedUTID).
                roles(new ArrayList<>()).
                build();
    }

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.setResponseHeaders(response);
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        String utid = request.getHeader(this.securityProperties.getUtid());
        if (StringUtils.isEmpty(utid)) {
            setErrorResponse(401, this.securityProperties.getFailureMessage(), response);
            return false;
        }

        String utidData = this.jwtUtils.getUTID(utid);
        if (Objects.isNull(utidData)) {
            setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
        }

        int secret_Key_id = -1;
        try {
            secret_Key_id = Integer.parseInt(utidData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (secret_Key_id == -1 || secret_Key_id > this.securityProperties.getSecret().size()) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
        }

        String secret = this.securityProperties.getSecret().get(secret_Key_id);

        String accessToken = this.accessToken(request, response);
        if (StringUtils.isEmpty(accessToken))
            return false;

        Claims claims = this.jwtUtils.getClaimsFromAccessToken(accessToken, secret);
        if (claims == null) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return false;
        }

        if (!this.jwtUtils.validateExpirationDate(claims)) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return false;
        }

//        String role          = (String) claims.get("role");
        String remoteAddress = (String) claims.get("remoteAddress");
        if (StringUtils.isNotEmpty(remoteAddress)) {
            if (!StringUtils.isEquals(remoteAddress, request.getRemoteAddr())) {
                this.setErrorResponse(HttpStatus.FORBIDDEN.value(), this.securityProperties.getFailureMessage(), response);
                return false;
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
//        authorities.add(authority);

        UserAccount userAccount = UserAccount.builder().
                username(claims.getSubject()).
                authorities(authorities).
                build();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userAccount, null, userAccount.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        return true;
    }

    private String accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
    }

    private void setErrorResponse(int code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"status\":\"" + code + "\",\"message\":\" %s \"}", msg));
        response.setStatus(code);
    }
}
