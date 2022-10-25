package code.shubham.queue;

public interface Constants {
    public interface Queries {
        String FETCH_MULTIPLE_TASK_AND_LOCK_BY_QUEUE_ID =
                "select * from tasks where queue_id = ? and ((status = 'QUEUED') or (status = 'FAIL' and retry_count < max_retries) or (status = 'EXECUTING' and lock_expiry_at < NOW())) order by priority desc, created_at, updated_at limit ? for update";

        String UPDATE_MULTIPLE_TASK_AND_LOCK_BY_QUEUE_NAME =
                "update tasks set status = 'EXECUTING', locked_by = ?, lock_expiry_at = NOW() + ?, retry_count = retry_count + 1, resource_version = resource_version + 1 where where queue_id = ? and ((status = 'QUEUED') or (status = 'FAIL' and retry_count < max_retries) or (status = 'EXECUTING' and lock_expiry_at < NOW())) order by priority desc, created_at, updated_at limit ?";
        String UPDATE_EXECUTING_TASK_STATUS =
                "update tasks set status = ?, lock_expiry_at = null, locked_by = null, updated_at = NOW(), resource_version = resource_version + 1 where id = ? and status = 'EXECUTING' and locked_by = ? and resource_version = ?";
    }

    interface ControllerContexts {
        String TASKS_CONTEXT = "/tasks";
    }
}
