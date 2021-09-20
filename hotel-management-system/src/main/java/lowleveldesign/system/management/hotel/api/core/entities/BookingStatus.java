package lowleveldesign.system.management.hotel.api.core.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_status")
public class BookingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
//    @NotBlank
//    @Size(max = 12)
    @Column(unique = true, nullable = false, length = 12)
    private String status; // CANCELLED, COMPLETED, RESERVED, CONFIRMAITON_PENDING, ONGOING

}
