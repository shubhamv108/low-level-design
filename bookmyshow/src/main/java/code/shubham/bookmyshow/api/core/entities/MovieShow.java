package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_shows")
public class MovieShow extends code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false)
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date to;

	@ManyToOne
	@JoinColumn(name = "auditorium_id", nullable = false)
	private Auditorium auditorium;

	@OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL)
	private Set<Booking> bookings;

	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	@ManyToMany
	@JoinTable(name = "movie_shows__coupons__mapping", joinColumns = @JoinColumn(name = "movie_show_id"),
			inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private Set<Coupon> coupons;

}
