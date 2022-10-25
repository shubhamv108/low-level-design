package code.shubham.queue.models;

import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;
import java.util.Date;

@Getter
public class Task {
    private Integer id;
    private String queueName;
    private String command;
    private TaskPriority priority = TaskPriority.LOW;
    private Integer maxRetries = 0;
    private Long expiryAt;
}
