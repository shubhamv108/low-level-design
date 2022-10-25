package code.shubham.queue;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.queue.models.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
public class Task extends BaseAbstractAuditableEntity {
    @Lob
    private String command;

    @Builder.Default
    @Column(nullable = false)
    private Double priority = TaskPriority.LOW.getPriority();

    @Builder.Default
    private Integer maxRetries = 0;

    @Builder.Default
    private Integer retryCount = -1;

    @Temporal(TemporalType.DATE)
    private Date expiryAt;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.QUEUED;

    private Date lockExpiryAt;

    private Integer lockedBy;

    @Builder.Default
    private Integer resourceVersion = 1;

    @ManyToOne
    @JoinColumn(name = "queue_id", referencedColumnName = "id", nullable = false)
    private Queue queue;
}
