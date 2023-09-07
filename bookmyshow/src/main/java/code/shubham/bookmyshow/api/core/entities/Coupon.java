package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon extends BaseAbstractAuditableEntity {

	@Column(name = "coupon_code", nullable = false)
	private String code;

	private Double discountAmount;

	private Double discountPercentage;

	@Builder.Default
	private int maxUsage = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expires_at", nullable = false)
	private Date expiresAt;

}
