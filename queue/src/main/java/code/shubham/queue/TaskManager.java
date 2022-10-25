package code.shubham.queue;

import code.shubham.commons.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskManager {

    private final QueueRepository queueRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskManager(final QueueRepository queueRepository,
                       final TaskRepository taskRepository) {
        this.queueRepository = queueRepository;
        this.taskRepository = taskRepository;
    }

    public Task create(final Task task) {
        return this.taskRepository.save(task);
    }

    public Collection<Task> createAll(final Collection<Task> task) {
        return this.taskRepository.saveAll(task);
    }


    public Task get(final Integer id) {
        return this.taskRepository.getById(id);
    }

    public Collection<Task> updateAll(final Collection<Task> tasks) {
        Collection<Task> setTasks = new ArrayList<>();
        InvalidRequestException requestException = new InvalidRequestException();
        Collection<String> notFoundTaskIds = new HashSet<>();
        boolean hasEmptyId = false;
        for (Task task : tasks) {
            if (task.getId() == null) {
                hasEmptyId = true;
                continue;
            }

            Task existingTask = this.taskRepository.getById(task.getId());
            if (existingTask == null) {
                notFoundTaskIds.add(task.getId().toString());
                continue;
            }

            if (!existingTask.getQueue().getName().equals(task.getQueue().getName())) {
                requestException.putErrorMessage(
                        "QueueName",
                        String.format("Provided queue name %s should be same as existing queue name %s for task %d",
                                task.getQueue().getName(),
                                existingTask.getQueue().getName(),
                                task.getId()));
            }

            if (task.getPriority() != null)
                existingTask.setPriority(task.getPriority());
            if (task.getMaxRetries() != null)
                existingTask.setMaxRetries(existingTask.getMaxRetries());
            setTasks.add(existingTask);

            requestException.next();
        }

        if (hasEmptyId)
            requestException.putErrorMessage("id", "id must not be null or empty");
        if (!notFoundTaskIds.isEmpty())
            requestException.put("NotFound-TaskId", notFoundTaskIds);
        requestException.tryThrow();

        return this.taskRepository.saveAll(setTasks);
    }

    @Transactional
    public Collection<Task> fetchAndLockByQueueIdHavingHighestPriority(final Integer userId, final Integer queueId, final int limit) {
        Collection<Task> tasks = this.taskRepository.fetchAndLockByQueueIdHavingHighestPriority(queueId, limit);
        tasks.forEach(task -> {
            task.setStatus(TaskStatus.EXECUTING);
            task.setLockedBy(userId);
            task.setLockExpiryAt(Date.from(new Date().toInstant().plus(99, ChronoUnit.DAYS)));
        });
        return this.taskRepository.saveAll(tasks);
    }

    public boolean acknowledge(final Task task) {
        int updatedRows = this.taskRepository.updateTaskStatusForExecutingTask(
                task.getStatus().name(), task.getId(), task.getUpdatedBy(), task.getResourceVersion());
        return updatedRows == 1;
    }

}
