package code.shubham.authentication.dao.repositories;

import code.shubham.authentication.dao.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    List<Session> findAllByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM session WHERE expiry_at < ?1", nativeQuery = true)
    void deleteAllByExpiry(Date slackDate);

    @Transactional
    @Modifying
    @Query(value = "UPDATE session SET expiry_at = ?2 WHERE token = ?1", nativeQuery = true)
    void updateExpiryByToken(String token, Date newExpiry);

    @Query(value = "select  * from session where user_id=?1 and 1=1", nativeQuery = true)
    Session findOneByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "delete from session where token = ?1 and 1=1", nativeQuery = true)
    void deleteByToken(String token);

    @Query(value = "select * from session where token = ?1 and 1=1", nativeQuery = true)
    Session findOneByToken(String token);
}
