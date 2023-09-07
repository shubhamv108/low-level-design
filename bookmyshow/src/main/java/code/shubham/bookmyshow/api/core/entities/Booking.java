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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking extends BaseAbstractAuditableEntity {

	@OneToMany(mappedBy = "booking")
	private Set<ShowSeat> showSeats;

	@Builder.Default
	private Integer totalPrice = 0;

	@ManyToOne
	@JoinColumn(name = "movie_show", nullable = false)
	private MovieShow movieShow;

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	@ManyToOne
	@JoinColumn(name = "booking_viewer", referencedColumnName = "id", nullable = false)
	private User bookingViewer;

	public int seatCount() {
		return this.showSeats.size();
	}

}
