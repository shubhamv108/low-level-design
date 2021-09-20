package lowleveldesign.system.management.hotel.api.core.entities;

import lowleveldesign.system.management.hotel.api.core.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking extends BaseAbstractAuditableEntity {

    @OneToOne
    private Room room;

}
