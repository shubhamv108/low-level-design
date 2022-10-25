package code.shubham.swiggy.restaurant.entities.fooditems;

import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.swiggy.restaurant.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_food_items")
public class RestaurantFoodItem extends BaseAbstractAuditableEntity {

    private String name;
    private String description;
    private Long image_id;

    @ManyToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "id", unique = true)
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

}
