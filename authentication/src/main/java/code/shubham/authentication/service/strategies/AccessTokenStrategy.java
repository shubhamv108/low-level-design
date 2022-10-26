package code.shubham.authentication.service.strategies;

import code.shubham.models.authentication.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public interface AccessTokenStrategy {

    Login.Response prepareLoginResponse(String userName, Date expirationDate);

    boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
