package code.shubham.authentication.service.strategies;

import code.shubham.authentication.dao.service.SessionService;
import code.shubham.models.authentication.Login;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

public class UUIDAccessTokenStrategy implements AccessTokenStrategy {

    private final SessionService sessionService;

    public static final Integer UNIQUE_SESSION_KEY_GENERATION_ATTEMPTS = 3;

    @Autowired
    public UUIDAccessTokenStrategy(final SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Login.Response prepareLoginResponse(String userName, Date expirationDate) {
        String token = this.getUniqueSessionToken();
        return Login.Response.builder().
                accessToken(token).
                username(userName).
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
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
