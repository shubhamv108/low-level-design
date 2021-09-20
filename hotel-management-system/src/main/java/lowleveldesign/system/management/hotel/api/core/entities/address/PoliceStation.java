package lowleveldesign.system.management.hotel.api.core.entities.address;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.system.management.hotel.api.core.entities.base.BaseAbstractAuditableEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "police_stations")
public class PoliceStation extends BaseAbstractAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
//    @NotBlank
    @Column(name = "police_station_name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

}
