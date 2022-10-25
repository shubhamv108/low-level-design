package code.shubham.analytics.logs;

import code.shubham.analytics.logs.entities.QueryLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryLogRepository extends CrudRepository<QueryLog, String> {
}
