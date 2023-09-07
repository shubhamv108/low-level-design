package code.shubham.bookmyshow.api.core.entities.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pin_codes")
public class PinCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NaturalId
	@Column(name = "pin_code", nullable = false, unique = true)
	private Integer pinCode;

	@ManyToOne
	@JoinColumn(name = "taluk_id")
	private Taluk taluk;

	@ManyToOne
	@JoinColumn(name = "sub_district_id")
	private SubDistrict subDistrict;

}
