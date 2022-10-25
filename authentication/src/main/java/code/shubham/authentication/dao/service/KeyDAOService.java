package code.shubham.authentication.dao.service;

import code.shubham.authentication.dao.entities.Key;
import code.shubham.authentication.dao.repositories.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyDAOService {

    private final KeyRepository repository;

    @Autowired
    public KeyDAOService(final KeyRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets the public key.
     *
     * @param keyType the key type
     * @return the encryption key
     */
    public byte[] getPublicKey(String keyType) {
        Key key = this.repository.findByPurpose(keyType);
        return key.getPublicKey().getEncoded();
    }

    /**
     * Gets the private key.
     *
     * @param keyType the key type
     * @return the private key
     */
    public byte[] getPrivateKey(String keyType) {
        Key key = this.repository.findByPurpose(keyType);
        return key.getPrivateKey().getEncoded();
    }
}

