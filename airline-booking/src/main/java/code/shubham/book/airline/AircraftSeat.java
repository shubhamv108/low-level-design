package code.shubham.book.airline;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft_seats",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "aircraft", "seatNumber" }) })
public class AircraftSeat extends BaseAbstractAuditableEntity {

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    private boolean canStretchBack;

    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private Aircraft aircraft;

}
