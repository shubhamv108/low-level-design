package code.shubham.swiggy.commons.entities.base.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseAbstractAuditableEntity {

    private String line1;
    private String line2;
    private String geoCode;

    @ManyToOne
    @JoinColumn(name = "pin_code", referencedColumnName = "id", nullable = false)
    private PinCode pinCode;

}
