package code.shubham.batch;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batch_jobs")
public class BatchJob extends BaseAbstractAuditableEntity {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String cron;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRunStartTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRunEndTimestamp;

    @Enumerated(EnumType.STRING)
    private Status lastRunStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cronCreatedAt;
}
