package code.shubham.lock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<Lock, Integer> {

    Optional<Lock> findByName(String name);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = Constants.Queries.LOCK)
    int lock(String owner, Date expiryAt, String name);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = Constants.Queries.UPDATE_EXPIRY)
    int updateLockLease(Date expiryAt, String name, String owner, int version);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = Constants.Queries.UNLOCK)
    int unlock(String name, String owner, int version);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = Constants.Queries.EXPIRE)
    int expire();
}
