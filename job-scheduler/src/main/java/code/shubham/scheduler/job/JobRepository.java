package code.shubham.scheduler.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Optional<Job> findByIdAndUserId(Integer id, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE jobs SET status = ? WHERE id = ? AND user_id = ?", nativeQuery = true)
    int setStatusByIdAndUserId(Status status, Integer id, Integer userId);
}
