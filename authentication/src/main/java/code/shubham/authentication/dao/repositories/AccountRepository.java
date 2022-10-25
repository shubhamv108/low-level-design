package code.shubham.authentication.dao.repositories;

import code.shubham.authentication.dao.entities.AuthenticationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AuthenticationAccount, Integer> {
    Optional<AuthenticationAccount> findByUsername(String username);
}
