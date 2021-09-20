package lowleveldesign.system.management.hotel.api.core.entities.address;

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
@Table(name = "area_type")
public class AreaType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
//    @NotBlank
    @Column(name = "area_type", nullable = false, unique = true)
    private String type; // CIRCLE, CONTINENT, COUNTRY, DISTRICT, DIVISION, PIN_CODE, POLICE_STATION, REGION, STATE, SUB_DISTRICT, TALUK, ZONE

}
