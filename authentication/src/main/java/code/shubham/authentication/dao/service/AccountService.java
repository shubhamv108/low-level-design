package code.shubham.authentication.dao.service;

import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.repositories.AccountRepository;
import code.shubham.models.authentication.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    public UserAccount create(CreateAccount.Request request, String encodedPassword) {
        UserAccount userAccount = UserAccount.builder().
                username(request.getUserName()).
                password(encodedPassword).
                build();
        return this.save(userAccount);
    }

    public UserAccount save(UserAccount userAccount) {
        return this.repository.save(userAccount);
    }

    public Optional<UserAccount> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public Optional<UserAccount> findById(Integer id) {
        return this.repository.findById(id);
    }
}
