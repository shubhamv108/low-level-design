package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity {

	private String latitude;

	private String longitude;

	private String apartmentNumber;

	private String buildingNumber;

	private String buildingName;

	private String streetName;

	private String areaName;

	private String city;

	@ManyToOne
	@JoinColumn(name = "pin_code_id", nullable = false)
	private code.shubham.bookmyshow.api.core.entities.address.PinCode pincode;

}
