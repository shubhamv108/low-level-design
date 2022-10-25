package code.shubham.sequencegenerator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AcceptedUserRepository extends JpaRepository<AcceptedUser, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE accepted_users SET user_id = ? WHERE user_id IS NULL LIMIT 1")
    int acceptToFirstEmpty(Integer userId);
}
