package code.shubham.authentication.service.strategies;

import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.models.authentication.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public interface AccessTokenStrategy {

    Login.Response prepareLoginResponse(UserAccount userAccount, Date expirationDate);

    Integer validate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    boolean logout(UserAccount user, String token);

}
