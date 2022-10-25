package code.shubham.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchJobRepository extends JpaRepository<BatchJob, Integer> {
    Optional<BatchJob> findByCode(String code);

    @Query(value = "UPDATE batch_jobs SET status = 'STARTED', last_run_start_timestamp = NOW(), last_run_end_timestamp = NULL WHERE id = ? AND status NOT IN ('STARTED', 'STOPPED')", nativeQuery = true)
    int updateStatusToStartedIfStatusNotStartedOrStoppedByJobId(Integer id);
}
