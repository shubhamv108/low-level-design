package code.shubham.authentication.service;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.authentication.dao.entities.AuthenticationAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.dao.service.CryptoService;
import code.shubham.authentication.utils.JWTUtils;
import code.shubham.common.exceptions.InvalidRequestException;
import code.shubham.common.utils.StringUtils;
import code.shubham.models.authentication.CreateAccount;
import code.shubham.models.authentication.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    private final JWTUtils jwtUtils;

    @Autowired
    public AuthenticationService(final AccountService accountService,
                                 final CryptoService cryptoService,
                                 final PasswordEncoder passwordEncoder,
                                 final SecurityProperties securityProperties,
                                 final JWTUtils jwtUtils) {
        this.accountService = accountService;
        this.cryptoService = cryptoService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
        this.jwtUtils = jwtUtils;
    }

    public String getEncodedPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    public Login.Response login(Login.Request request) {
        String decryptedAuthValue = cryptoService.decryptUsingPrivateKey(request.getPassword(), AuthenticationConstants.AUTH_TYPE_PASSWORD);
        String authValueToMatch = cryptoService.parseAndGetAuthValue(decryptedAuthValue, AuthenticationConstants.AUTH_TYPE_PASSWORD);
        String encodedPassword = this.getEncodedPassword(authValueToMatch);

        Optional<AuthenticationAccount> accountOptional = this.accountService.fndByUsername(request.getUsername());
        if (accountOptional.isEmpty())
            throw new InvalidRequestException("username", "Invalid username. Not found.");

        AuthenticationAccount account = accountOptional.get();
        if (!StringUtils.isEquals(encodedPassword, account.getPassword()))
            throw new BadCredentialsException(this.securityProperties.getBadCredentialMessage());

        return this.prepareLoginResponse(account.getName());
    }

    private Login.Response prepareLoginResponse(String accountName) {
        int generatedRandomNumber = this.jwtUtils.generateRandomNumber();
        String generatedUTID      = this.jwtUtils.prepareUTID(generatedRandomNumber);
        String jti                = this.jwtUtils.generateJti();
        Calendar calendar         = Calendar.getInstance();
        Date dateExp              = this.jwtUtils.generateExpirationDate(calendar);
        log.info("Expiration Date for the auth token {}", dateExp.toString());
        String secret             = this.securityProperties.getSecret().get(generatedRandomNumber);
//        Role role = user.getRoles().stream().findFirst().get();
        String accessToken        = this.jwtUtils.generateAccessToken(
                accountName, null, generatedUTID, jti, dateExp, secret, null);
        return Login.Response.builder().
                username(accountName).
                accessToken(accessToken).
                jti(jti).
                utid(generatedUTID).
                roles(new ArrayList<>()).
                build();
    }
}
