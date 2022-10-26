package code.shubham.authentication.service.strategies;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.dao.service.BlacklistTokenService;
import code.shubham.authentication.utils.AccessTokenUtil;
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
import java.util.Optional;

@Component("JWTAccessTokenStrategy")
public class JWTAccessTokenStrategy implements AccessTokenStrategy {

    private final JWTUtils jwtUtils;
    private final SecurityProperties securityProperties;

    private final AccessTokenUtil accessTokenUtil;

    private final BlacklistTokenService blacklistTokenService;

    private final AccountService accountService;

    @Autowired
    public JWTAccessTokenStrategy(final JWTUtils jwtUtils,
                                  final SecurityProperties securityProperties,
                                  final AccessTokenUtil accessTokenUtil,
                                  final BlacklistTokenService blacklistTokenService,
                                  final AccountService accountService) {
        this.jwtUtils = jwtUtils;
        this.securityProperties = securityProperties;
        this.accessTokenUtil = accessTokenUtil;
        this.blacklistTokenService = blacklistTokenService;
        this.accountService = accountService;
    }

    @Override
    public Login.Response prepareLoginResponse(UserAccount userAccount, Date expirationDate) {
        int generatedRandomNumber = this.jwtUtils.generateRandomNumber();
        String generatedUTID      = this.jwtUtils.prepareUTID(generatedRandomNumber);
        String jti                = this.jwtUtils.generateJti();
        String secret             = this.securityProperties.getSecret().get(generatedRandomNumber);
//        Role role = user.getRoles().stream().findFirst().get();
        String accessToken        = this.jwtUtils.generateAccessToken(
                userAccount.getUsername(), null, generatedUTID, jti, expirationDate, secret, null);
        return Login.Response.builder().
                username(userAccount.getUsername()).
                accessToken(accessToken).
                jti(jti).
                utid(generatedUTID).
                roles(new ArrayList<>()).
                build();
    }

    @Override
    public Integer validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.setResponseHeaders(response);
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return null;
        }

        String utid = request.getHeader(this.securityProperties.getUtid());
        if (StringUtils.isEmpty(utid)) {
            this.accessTokenUtil.setErrorResponse(401, this.securityProperties.getFailureMessage(), response);
            return null;
        }

        String utidData = this.jwtUtils.getUTID(utid);
        if (Objects.isNull(utidData)) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
        }

        int secret_Key_id = -1;
        try {
            secret_Key_id = Integer.parseInt(utidData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (secret_Key_id == -1 || secret_Key_id > this.securityProperties.getSecret().size()) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
        }

        String secret = this.securityProperties.getSecret().get(secret_Key_id);

        String accessToken = this.accessTokenUtil.accessToken(request, response);
        if (StringUtils.isEmpty(accessToken))
            return null;

        Claims claims = this.jwtUtils.getClaimsFromAccessToken(accessToken, secret);
        if (claims == null) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }

        String username = claims.getSubject();
        /** Remove this check */
        if (this.blacklistTokenService.contains(username, accessToken)) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }

        if (!this.jwtUtils.validateExpirationDate(claims)) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }

//        String role          = (String) claims.get("role");
        String remoteAddress = (String) claims.get("remoteAddress");
        if (StringUtils.isNotEmpty(remoteAddress)) {
            if (!StringUtils.isEquals(remoteAddress, request.getRemoteAddr())) {
                this.accessTokenUtil.setErrorResponse(HttpStatus.FORBIDDEN.value(), this.securityProperties.getFailureMessage(), response);
                return null;
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
//        authorities.add(authority);

        UserAccount userAccount = UserAccount.builder().
                username(username).
                authorities(authorities).
                build();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userAccount, null, userAccount.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        Optional<UserAccount> userAccountOptional = this.accountService.findByUsername(username);
        if (userAccountOptional.isEmpty()) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }
        return userAccountOptional.get().getId();
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
    }

    @Override
    public boolean logout(UserAccount user, String token) {
        return this.blacklistTokenService.add(user.getUsername(), token) != null;
    }
}
