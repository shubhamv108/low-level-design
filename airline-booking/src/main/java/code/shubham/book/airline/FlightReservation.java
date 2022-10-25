package code.shubham.book.airline;

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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_reservations")
public class FlightReservation extends BaseAbstractAuditableEntity {

    @Column(unique = true, nullable = false)
    private String pnr;

    private Integer flightInstanceId;

    @Enumerated(EnumType.STRING)
    private FlightReservationsStatus reservationsStatus;

    private Integer paymentOrderId;
}
