package lowleveldesign.swiggy.restaurant.entities.fooditems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.restaurant.entities.Restaurant;
import lowleveldesign.swiggy.restaurant.entities.RestaurantOutlet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_outlet_food_items")
public class RestaurantOutletFoodItem {

    private String name;
    private String description;
    private Long image_id;

    @ManyToOne
    @JoinColumn(name = "restaurant_outlet_food_item_status_id", referencedColumnName = "id", nullable = false)
    private RestaurantOutletFoodItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_food_item_id", referencedColumnName = "id", unique = true)
    private RestaurantFoodItem restaurantFoodItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id", referencedColumnName = "id", unique = true)
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "restaurant_outlet_id", referencedColumnName = "id", nullable = false)
    private RestaurantOutlet restaurantOutlet;

}
