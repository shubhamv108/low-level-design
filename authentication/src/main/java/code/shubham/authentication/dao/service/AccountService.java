package code.shubham.authentication.dao.service;

import code.shubham.authentication.dao.entities.AuthenticationAccount;
import code.shubham.authentication.dao.repositories.AccountRepository;
import code.shubham.common.exceptions.InvalidRequestException;
import code.shubham.models.authentication.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    public AuthenticationAccount create(CreateAccount.Request request, String encodedPassword, Integer userId) {
        AuthenticationAccount authenticationAccount = AuthenticationAccount.builder().
                name(request.getUserName()).
                password(encodedPassword).
                userId(userId).
                build();
        return this.save(authenticationAccount);
    }

    public AuthenticationAccount save(AuthenticationAccount authenticationAccount) {
        return this.repository.save(authenticationAccount);
    }

    public Optional<AuthenticationAccount> fndByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public AuthenticationAccount setUserId(String username, Integer userId) {
        Optional<AuthenticationAccount> accountOptional = this.repository.findByUsername(username);
        if (!accountOptional.isEmpty())
            throw new InvalidRequestException("username", "Invalid username. Not Found");

        AuthenticationAccount account = accountOptional.get();
        account.setUserId(userId);
        return this.repository.save(account);
    }
}
