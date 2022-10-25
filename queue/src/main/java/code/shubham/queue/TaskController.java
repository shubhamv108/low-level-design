package code.shubham.queue;

import code.shubham.authorization.client.AuthorizationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(Constants.ControllerContexts.TASKS_CONTEXT)
public class TaskController {

    private final TaskManager manager;
    private final QueueManager queueManager;
    private final AuthorizationClient authorizationClient;

    @Value("${host.queue:127.0.0.1:8088}")
    private String queueHost;

    @Value("${service.queue.context-path:/queue}")
    private String contextPath;

    private String taskURI;

    @PostConstruct
    private void postConstruct() {
        this.taskURI = String.format("%s%s%s",
                this.queueHost, this.contextPath, Constants.ControllerContexts.TASKS_CONTEXT).intern();
    }

    @Autowired
    public TaskController(final TaskManager manager,
                          final QueueManager queueManager,
                          final AuthorizationClient authorizationClient) {
        this.manager = manager;
        this.queueManager = queueManager;
        this.authorizationClient = authorizationClient;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("userId") final Integer userId,
                                    @RequestBody final Collection<code.shubham.queue.models.Task> taskDTOs)
            throws URISyntaxException, IOException, InterruptedException {
        log.info("Received tasks bulk create");
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (!this.authorizationClient.isIndividualUser(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Collection<Task> tasks = taskDTOs.stream().map(taskDTO -> {
            Queue queue = this.queueManager.get(taskDTO.getQueueName()).
                    orElseThrow(() -> new NoSuchQueueExistException(taskDTO.getQueueName()));
            return Task.builder().
                    queue(queue).
                    command(taskDTO.getCommand()).
                    expiryAt(new Date(taskDTO.getExpiryAt())).
                    priority(taskDTO.getPriority().getPriority()).
                    maxRetries(taskDTO.getMaxRetries()).
                    build();
        }).collect(Collectors.toList());
        Collection<Task> createdTasks = this.manager.createAll(tasks);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(createdTasks);
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestHeader("userId") final Integer userId,
                                    @RequestBody final Collection<code.shubham.queue.models.Task> taskDTOs)
            throws URISyntaxException, IOException, InterruptedException {
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (!this.authorizationClient.isAdmin(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Collection<Task> tasks = taskDTOs.stream().
                map(taskDTO ->
                        Task.builder().
                            id(taskDTO.getId()).
                            priority(taskDTO.getPriority().getPriority()).
                            maxRetries(taskDTO.getMaxRetries()).
                            queue(Queue.builder().name(taskDTO.getQueueName()).build()).
                            build()).
                collect(Collectors.toList());

        Collection<Task> updatedTasks = this.manager.updateAll(tasks);
        return ResponseEntity.ok().body(updatedTasks);
    }
}
