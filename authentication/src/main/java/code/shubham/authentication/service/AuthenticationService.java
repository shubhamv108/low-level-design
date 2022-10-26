package code.shubham.authentication.service;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.dao.service.CryptoService;
import code.shubham.authentication.dao.service.SessionService;
import code.shubham.authentication.service.strategies.AccessStrategy;
import code.shubham.common.exceptions.InvalidRequestException;
import code.shubham.common.utils.StringUtils;
import code.shubham.models.authentication.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    private final AccountService accountService;

    private final CryptoService cryptoService;

    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties securityProperties;

    private final SessionService sessionService;

    private final AccessStrategy accessStrategy;

    @Autowired
    public AuthenticationService(final AccountService accountService,
                                 final CryptoService cryptoService,
                                 final PasswordEncoder passwordEncoder,
                                 final SecurityProperties securityProperties,
                                 final SessionService sessionService,
                                 @Qualifier("JWTAccessStrategy")
                                     final AccessStrategy accessStrategy) {
        this.accountService = accountService;
        this.cryptoService = cryptoService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
        this.sessionService = sessionService;
        this.accessStrategy = accessStrategy;
    }

    public String getEncodedPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    public Login.Response login(Login.Request request) {
        String decryptedAuthValue = this.cryptoService.decryptUsingPrivateKey(
                request.getPassword(), AuthenticationConstants.AUTH_TYPE_PASSWORD);
        String authValueToMatch = this.cryptoService.parseAndGetAuthValue(
                decryptedAuthValue, AuthenticationConstants.AUTH_TYPE_PASSWORD);
        String encodedPassword = this.getEncodedPassword(authValueToMatch);

        Optional<UserAccount> accountOptional = this.accountService.findByUsername(request.getUsername());
        if (accountOptional.isEmpty())
            throw new InvalidRequestException("username", "Invalid username. Not found.");

        UserAccount account = accountOptional.get();
        if (!StringUtils.isEquals(encodedPassword, account.getPassword()))
            throw new BadCredentialsException(this.securityProperties.getBadCredentialMessage());

        Calendar calendar = Calendar.getInstance();
        Date expirationDate = this.securityProperties.generateExpirationDate(calendar);
        log.info("Expiration Date for the auth token {}", expirationDate.toString());
        return this.accessStrategy.prepareLoginResponse(account, expirationDate);
    }

    public Integer authenticate(HttpServletRequest request, HttpServletResponse response) {
        try {
            return this.accessStrategy.validate(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean logout(Integer userId, String token) {
        UserAccount account = this.accountService.findById(userId).get();
        return this.accessStrategy.logout(account, token);
    }
}
