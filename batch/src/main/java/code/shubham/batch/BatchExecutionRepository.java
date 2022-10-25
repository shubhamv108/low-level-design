package code.shubham.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchExecutionRepository extends JpaRepository<BatchJobExecution, Integer> {
}
