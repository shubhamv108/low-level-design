package code.shubham.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueueManager {

    private final QueueRepository repository;
    private final TaskManager taskManager;

    @Autowired
    public QueueManager(final QueueRepository repository, final TaskManager taskManager) {
        this.repository = repository;
        this.taskManager = taskManager;
    }

    public Queue create(final Queue queue) {
        return this.repository.save(queue);
    }

    public Optional<Queue> get(final String name) {
        return this.repository.findByName(name);
    }

    public Collection<Queue> getAll() {
        return this.repository.findAll();
    }

    public Collection<Task> poll(final Integer userId, final Queue queue, int limit) {
        return this.taskManager.fetchAndLockByQueueIdHavingHighestPriority(userId, queue.getId(), limit);
    }

    public boolean acknowledge(final Integer userId, final Task task) {
        task.setUpdatedBy(userId);
        return this.taskManager.acknowledge(task);
    }
}
