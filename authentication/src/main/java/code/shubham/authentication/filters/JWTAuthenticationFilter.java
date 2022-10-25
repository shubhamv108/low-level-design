package code.shubham.authentication.filters;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.dao.entities.AuthenticationAccount;
import code.shubham.authentication.utils.JWTUtils;
import code.shubham.common.utils.StringUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@Order(5)
public class JWTAuthenticationFilter implements Filter {

    private final JWTUtils jwtUtils;
    private final SecurityProperties securityProperties;

    @Autowired
    public JWTAuthenticationFilter(final JWTUtils jwtUtils,
                                   final SecurityProperties securityProperties) {
        this.jwtUtils = jwtUtils;
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        this.setResponseHeaders(response);
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        String utid = request.getHeader(this.securityProperties.getUtid());
        if (StringUtils.isEmpty(utid)) {
            setErrorResponse(401, this.securityProperties.getFailureMessage(), response);
            return;
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
            setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
        }

        String secret = this.securityProperties.getSecret().get(secret_Key_id);

        String accessToken = this.accessToken(request, response);
        if (StringUtils.isEmpty(accessToken))
            return;

        Claims claims = this.jwtUtils.getClaimsFromAccessToken(accessToken, secret);
        if (claims == null) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return;
        }

        if (!this.jwtUtils.validateExpirationDate(claims)) {
            this.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return;
        }

        String role          = (String) claims.get("role");
        String remoteAddress = (String) claims.get("remoteAddress");
        if (StringUtils.isNotEmpty(remoteAddress)) {
            if (!StringUtils.isEquals(remoteAddress, request.getRemoteAddr())) {
                this.setErrorResponse(HttpStatus.FORBIDDEN.value(), this.securityProperties.getFailureMessage(), response);
                return;
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        authorities.add(authority);

        AuthenticationAccount authenticationUser = AuthenticationAccount.builder().
                name(claims.getSubject()).
                authorities(authorities).
                build();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationUser, null, authenticationUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(servletRequest, servletResponse);
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
        response.setHeader("Acce" +
                "ss-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
    }

    private void setErrorResponse(int code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"status\":\"" + code + "\",\"message\":\" %s \"}", msg));
        response.setStatus(code);
    }
}
