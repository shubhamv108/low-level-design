package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinemas")
public class Cinema extends BaseAbstractAuditableEntity {

	private String name;

	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@ManyToMany
	@JoinTable(name = "cinemas__coupons__mapping", joinColumns = @JoinColumn(name = "cinema_id"),
			inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private Set<Coupon> coupons;

}
