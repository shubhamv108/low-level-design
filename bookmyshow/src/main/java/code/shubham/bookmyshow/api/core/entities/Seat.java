package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "seats")
public class Seat extends BaseAbstractAuditableEntity {

	private String rowNumber;

	private String seatNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "seat__seat_feature__mapping", joinColumns = @JoinColumn(name = "seat_id"),
			inverseJoinColumns = @JoinColumn(name = "seat_feature_id"))
	private Set<SeatFeature> seatFeatures;

	@Builder.Default
	private boolean isUsable = true;

	@ManyToOne
	@JoinColumn(name = "auditorium_id", referencedColumnName = "id", nullable = false)
	private Auditorium auditorium;

	@OneToMany
	private Set<ShowSeat> showSeats;

}
