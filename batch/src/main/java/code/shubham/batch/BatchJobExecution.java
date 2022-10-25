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
@Table(name = "batch_job_executions")
public class BatchJobExecution extends BaseAbstractAuditableEntity {

    @Column(nullable = false)
    private Integer bathJobId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRunStartTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRunEndTimestamp;


    @Enumerated(EnumType.STRING)
    private Status lastRunStatus;

    @Column(name="reason_for_failure")
    private String reasonForFailure;
}
