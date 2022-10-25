package code.shubham.book.airline;


import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight extends BaseAbstractAuditableEntity {

    @Column(unique = true, nullable = false)
    private String number;

    private Integer arrivalAirportId;
    private Integer departureAirportId;

    private Integer aircraftId;
}
