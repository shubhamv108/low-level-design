package code.shubham.authentication.service.strategies;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.dao.entities.Session;
import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.dao.service.SessionService;
import code.shubham.authentication.utils.AccessTokenUtil;
import code.shubham.common.utils.StringUtils;
import code.shubham.models.authentication.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component("UUIDAccessTokenStrategy")
public class UUIDAccessTokenStrategy implements AccessTokenStrategy {

    private final SessionService sessionService;
    private final AccessTokenUtil accessTokenUtil;

    private final SecurityProperties securityProperties;

    private final AccountService accountService;

    public static final Integer UNIQUE_SESSION_KEY_GENERATION_ATTEMPTS = 3;

    @Autowired
    public UUIDAccessTokenStrategy(final SessionService sessionService,
                                   final AccessTokenUtil accessTokenUtil,
                                   final SecurityProperties securityProperties,
                                   final AccountService accountService) {
        this.sessionService = sessionService;
        this.accessTokenUtil = accessTokenUtil;
        this.securityProperties = securityProperties;
        this.accountService = accountService;
    }

    @Override
    public Login.Response prepareLoginResponse(UserAccount userAccount, Date expirationDate) {
        String token = this.getUniqueSessionToken();
        Session session = this.sessionService.create(userAccount.getId(), token, expirationDate);
        return Login.Response.builder().
                accessToken(session.getToken()).
                username(userAccount.getUsername()).
                build();
    }

    private String getUniqueSessionToken() {
        String token = null;
        String randomString;
        for(int attempt = 1; attempt <= UNIQUE_SESSION_KEY_GENERATION_ATTEMPTS; attempt++){
            randomString = UUID.randomUUID().toString();
            if(sessionService.findOneByToken(randomString) == null){
                token = randomString;
                break;
            }
        }
        return token;
    }

    @Override
    public Integer validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = this.accessTokenUtil.accessToken(request, response);
        if (StringUtils.isEmpty(accessToken)) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }

        String username = this.accessTokenUtil.getHeader("username", request, response);
        Optional<UserAccount> userAccountOptional = this.accountService.findByUsername(username);
        if (userAccountOptional.isEmpty()) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }

        Optional<Session> sessionOptional = this.sessionService.findByUserIdAndToken(userAccountOptional.get().getId(), accessToken);
        if (sessionOptional.isEmpty()) {
            this.accessTokenUtil.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.securityProperties.getFailureMessage(), response);
            return null;
        }
        return userAccountOptional.get().getId();
    }

    @Override
    public boolean logout(UserAccount user, String token) {
        this.sessionService.deleteByUserIdAndToken(user.getId(), token);
        return true;
    }
}
