package code.shubham.bookmyshow.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
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
@Table(name = "auditoriums")
public class Auditorium extends code.shubham.bookmyshow.api.core.entities.base.BaseAbstractAuditableEntity {

	private String hallName;

	private Integer screenHeight;

	private Integer screenLength;

	@ManyToMany
	@JoinTable(name = "auditorium__auditorium_feature__mapping", joinColumns = @JoinColumn(name = "auditorium_id"),
			inverseJoinColumns = @JoinColumn(name = "auditorium_feature_id"))
	private Set<AuditoriumFeature> features;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	private Set<Seat> seats;

	@ManyToOne
	@JoinColumn(name = "cinema_id", nullable = false)
	private Cinema cinema;

	@OneToMany(mappedBy = "auditorium"/* , cascade = CascadeType.ALL */)
	private Set<MovieShow> movieShows;

}
