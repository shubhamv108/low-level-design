package code.shubham.book.airline;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_seat_instances",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "flightInstanceId", "aircraftSeatId" }) })
public class FlightSeatInstance extends BaseAbstractAuditableEntity {

    private BigDecimal seatFare;

    private Integer aircraftSeatId;

    private Integer flightInstanceId;

}
