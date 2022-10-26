package code.shubham.authentication.dao.repositories;

import code.shubham.authentication.dao.entities.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Integer> {
    BlacklistToken findByUsernameAndToken(String username, String token);
}
