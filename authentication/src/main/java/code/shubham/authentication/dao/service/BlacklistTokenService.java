package code.shubham.authentication.dao.service;

import code.shubham.authentication.dao.entities.BlacklistToken;
import code.shubham.authentication.dao.repositories.BlacklistTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistTokenService {

    private final BlacklistTokenRepository repository;

    @Autowired
    public BlacklistTokenService(BlacklistTokenRepository repository) {
        this.repository = repository;
    }

    public BlacklistToken add(String username, String token) {
        return this.repository.save(BlacklistToken.builder().username(username).token(token).build());
    }

    public boolean contains(String username, String token) {
        return this.repository.findByUsernameAndToken(username, token) != null;
    }
}
