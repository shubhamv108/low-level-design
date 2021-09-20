package lowleveldesign.swiggy.restaurant.entities.fooditems;

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
@Table(name = "restaurant_outlet_food_item_status")
public class RestaurantOutletFoodItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false, length = 12)
    private String name; // AVAILABLE, UNAVAILABLE, DINE_ONLY

}
