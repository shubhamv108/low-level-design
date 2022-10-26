package code.shubham.authentication.dao.repositories;

import code.shubham.authentication.dao.entities.KeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<KeyStore, Long> {
    KeyStore findByPurpose(String purpose);
}
