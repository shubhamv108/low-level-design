package code.shubham.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(nativeQuery = true, value = Constants.Queries.FETCH_MULTIPLE_TASK_AND_LOCK_BY_QUEUE_ID)
    Collection<Task> fetchAndLockByQueueIdHavingHighestPriority(Integer queueId, int limit);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = Constants.Queries.UPDATE_EXECUTING_TASK_STATUS)
    int updateTaskStatusForExecutingTask(String status, Integer id, Integer updatedBy, Integer resourceVersion);
}
