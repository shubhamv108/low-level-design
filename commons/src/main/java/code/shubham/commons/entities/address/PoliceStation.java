package code.shubham.commons.entities.address;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Address;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "police_stations")
public class PoliceStation extends BaseAbstractAuditableEntity {

    @NaturalId
    @NotBlank
    @Column(name = "police_station_name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
