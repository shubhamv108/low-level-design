package code.shubham.book.airline;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.DayOfWeek;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_weekly_schedules",
        uniqueConstraints = { @UniqueConstraint(columnNames = {  }) })
public class WeeklySchedule extends BaseAbstractAuditableEntity {

    private Date departureTime;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private Integer flightId;

}
