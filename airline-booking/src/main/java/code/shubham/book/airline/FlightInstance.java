package code.shubham.book.airline;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_instances")
public class FlightInstance extends BaseAbstractAuditableEntity {

    private String departureGate;
    private String arrivalGate;

    private Integer flightId;
}
