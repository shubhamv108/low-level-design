package code.shubham.queue;

import code.shubham.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
public class QueueController {

    private final QueueManager manager;

    @Autowired
    public QueueController(final QueueManager manager) {
        this.manager = manager;
    }

    @PutMapping
    public Queue put(@RequestHeader("userId") Integer userId,
                     @RequestBody final Queue queue) {
        log.info("Received request to put Queue: {}", queue.getName());
        Optional<Queue> existingQueue = this.manager.get(queue.getName());
        if (existingQueue.isPresent())
            return existingQueue.get();
        return this.manager.create(Queue.builder().name(queue.getName()).build());
    }

    @GetMapping("poll")
    public ResponseEntity<?> poll(@RequestHeader("userId") final Integer userId,
                                  @RequestParam("queueName") final String queueName,
                                  @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        Queue queue = this.manager.get(queueName).
                orElseThrow(() -> new NoSuchQueueExistException(queueName));

        if (StringUtils.isEmpty(queueName))
            return ResponseEntity.badRequest().build();
        Collection<Task> lockedTasksInQueue = this.manager.poll(userId, queue, limit);
        return ResponseEntity.ok(lockedTasksInQueue);
    }

    @PostMapping("acknowledge")
    public ResponseEntity<?> acknowledge(@RequestHeader("userId") final Integer userId,
                                         @RequestBody final Task task) {
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean acknowledged = this.manager.acknowledge(userId, task);
        if (!acknowledged)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Queue> getAll() {
        return this.manager.getAll();
    }
}
