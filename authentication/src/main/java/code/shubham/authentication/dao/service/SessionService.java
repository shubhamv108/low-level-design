package code.shubham.authentication.dao.service;

import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.authentication.dao.entities.Session;
import code.shubham.authentication.dao.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SessionService {

    private final SessionRepository repository;
    private final CryptoService cryptoService;

    @Autowired
    public SessionService(final SessionRepository repository,
                          final CryptoService cryptoService) {
        this.repository = repository;
        this.cryptoService = cryptoService;
    }

    public Session create(Integer userId, String token, Date expirationAt) {
        Session session = Session.builder().
                createdOn(new Date()).
                token(token).
                userId(userId).
                expiryAt(expirationAt).
                build();
        return this.repository.save(session);
    }

    public Session findOneByToken(String token) {
        return this.repository.findOneByToken(token);
    }
}
