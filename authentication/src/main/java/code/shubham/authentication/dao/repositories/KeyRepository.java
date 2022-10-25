package code.shubham.authentication.dao.repositories;

import code.shubham.authentication.dao.entities.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
    Key findByPurpose(String purpose);
}
