package lowleveldesign.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.bookmyshow.api.core.entities.address.PinCode;
import lowleveldesign.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseAbstractAuditableEntity {

    private String latitude;
    private String longitude;
    private String apartmentNumber;
    private String buildingNumber;
    private String buildingName;
    private String streetName;
    private String areaName;
    private String city;
    private PinCode pincode;

}
