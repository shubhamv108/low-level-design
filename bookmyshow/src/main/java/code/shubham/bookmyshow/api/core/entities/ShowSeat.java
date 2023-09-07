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
@Table(name = "show_seats")
public class ShowSeat extends code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity {

	private Double price;

	@ManyToOne
	@JoinColumn(name = "show_seat_status_id", nullable = false)
	private ShowSeatStatus showSeatStatus;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@ManyToOne
	@JoinColumn(name = "movie_show", nullable = false)
	private MovieShow movieShow;

	@ManyToOne
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;

}
