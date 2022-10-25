package code.shubham.scheduler.job;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Duration;
import java.time.Period;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseAbstractAuditableEntity {
    @Lob
    private String script;

    @Enumerated(EnumType.STRING)
    private Status status = Status.READY;

    private int retryCount = 0;

    private Duration minWaitBetweenSuccessRun;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastExecuteTimestamp;

    private Integer userId;
}
